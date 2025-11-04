package com.eightsidedsquare.zine.client.trim;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.ModelSupplier;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.TrimMaterialProperty;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.client.texture.atlas.PalettedPermutationsAtlasSource;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public final class ArmorTrimRegistryImpl {
    private ArmorTrimRegistryImpl() {
    }

    private static final Identifier TRIM_PALETTE_KEY = Identifier.ofVanilla("trims/color_palettes/trim_palette");
    private static final Map<RegistryKey<ArmorTrimMaterial>, Material> MATERIALS = new Object2ObjectOpenHashMap<>();
    private static final Map<RegistryKey<ArmorTrimPattern>, Pattern> PATTERNS = new Object2ObjectOpenHashMap<>();
    private static final Set<Identifier> ITEM_MODEL_EXCLUDE = new ObjectOpenHashSet<>();

    private static void registerMaterial(RegistryKey<ArmorTrimMaterial> key, Material material) {
        MATERIALS.put(key, material);
    }

    static void registerMaterial(RegistryKey<ArmorTrimMaterial> key, String name, Identifier colorPaletteTexture, Map<EquipmentType, Identifier> equipmentItemModelIds) {
        registerMaterial(key, new Material(name, colorPaletteTexture, equipmentItemModelIds));
    }

    static void registerMaterial(RegistryKey<ArmorTrimMaterial> key) {
        registerMaterial(key, key.getValue().getPath(), key.getValue().withPrefixedPath("trims/color_palettes/"), createEquipmentModelIds(key));
    }

    private static void registerPattern(RegistryKey<ArmorTrimPattern> key, Pattern pattern) {
        PATTERNS.put(key, pattern);
    }

    static void registerPattern(RegistryKey<ArmorTrimPattern> key, Map<EquipmentModel.LayerType, Identifier> equipmentTextures) {
        registerPattern(key, new Pattern(equipmentTextures));
    }

    static void registerPattern(RegistryKey<ArmorTrimPattern> key) {
        Map<EquipmentModel.LayerType, Identifier> equipmentTextures = ImmutableMap.<EquipmentModel.LayerType, Identifier>builder()
                .put(EquipmentModel.LayerType.HUMANOID, key.getValue().withPrefixedPath("trims/entity/humanoid/"))
                .put(EquipmentModel.LayerType.HUMANOID_LEGGINGS, key.getValue().withPrefixedPath("trims/entity/humanoid_leggings/"))
                .build();
        registerPattern(key, equipmentTextures);
    }

    static void excludeForItemModelModification(Identifier... ids) {
        ITEM_MODEL_EXCLUDE.addAll(Arrays.asList(ids));
    }

    private static Map<EquipmentType, Identifier> createEquipmentModelIds(RegistryKey<ArmorTrimMaterial> key) {
        ImmutableMap.Builder<EquipmentType, Identifier> builder = ImmutableMap.builder();
        String name = key.getValue().getPath();
        for(EquipmentType type : ZineUtil.HUMANOID_EQUIPMENT_TYPES) {
            builder.put(type, key.getValue().withPath("item/" + type.asString() + "_" + name + "_trim"));
        }
        return builder.build();
    }

    public static boolean containsMaterial(RegistryKey<ArmorTrimMaterial> key) {
        return MATERIALS.containsKey(key);
    }

    public static void modifyItemsAtlas(List<AtlasSource> sources) {
        modifyPalettedSource(sources, ArmorTrimRegistryImpl::applyMaterials);
    }

    public static void modifyArmorTrimsAtlas(List<AtlasSource> sources) {
        modifyPalettedSource(sources, source -> {
            applyMaterials(source);
            applyPatterns(source);
        });
    }

    private static void modifyPalettedSource(List<AtlasSource> sources, Consumer<PalettedPermutationsAtlasSource> consumer) {
        for(AtlasSource source : sources) {
            if(source instanceof PalettedPermutationsAtlasSource palettedSource && palettedSource.zine$getPaletteKey().equals(TRIM_PALETTE_KEY)) {
                consumer.accept(palettedSource);
            }
        }
    }

    private static void applyMaterials(PalettedPermutationsAtlasSource source) {
        MATERIALS.values().forEach(entry -> source.zine$addNamespacedPermutation(entry.name, entry.colorPaletteTexture));
    }

    private static void applyPatterns(PalettedPermutationsAtlasSource source) {
        PATTERNS.values().forEach(entry -> entry.equipmentTextures.values().forEach(source::zine$addTexture));
    }

    public static ItemModel.Unbaked modifyItemModels(Identifier id, ItemModel.Unbaked unbaked) {
        if(ITEM_MODEL_EXCLUDE.contains(id)) {
            return unbaked;
        }
        Item item = Registries.ITEM.get(id);
        if(!item.zine$modelEquals(id)) {
            return unbaked;
        }
        EquipmentType equipmentType = getEquipmentType(item);
        if(equipmentType == null) {
            return unbaked;
        }else if(unbaked instanceof SelectItemModel.Unbaked selectModel) {
            return applyMaterials(selectModel, equipmentType);
        }
        return unbaked;
    }

    private static ItemModel.Unbaked applyMaterials(SelectItemModel.Unbaked selectModel, EquipmentType equipmentType) {
        if(selectModel.fallback().isEmpty()) {
            return selectModel;
        }
        ItemModel.Unbaked fallback = selectModel.fallback().get();
        selectModel.zine$addCases(TrimMaterialProperty.TYPE, MATERIALS.entrySet()
                .stream()
                .map(entry -> ItemModels.switchCase(
                        entry.getKey(),
                        ItemModels.composite(
                                fallback,
                                ItemModels.basic(entry.getValue().equipmentModelIds.get(equipmentType))
                        )
                ))
                .toList()
        );
        return selectModel;
    }


    @Nullable
    private static EquipmentType getEquipmentType(Item item) {
        EquippableComponent equippableComponent = item.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if(equippableComponent == null) {
            return null;
        }
        return switch (equippableComponent.slot()) {
            case HEAD -> EquipmentType.HELMET;
            case CHEST -> EquipmentType.CHESTPLATE;
            case LEGS -> EquipmentType.LEGGINGS;
            case FEET -> EquipmentType.BOOTS;
            default -> null;
        };
    }

    public static void addUnbakedModels(BiConsumer<Identifier, ModelSupplier> modelCollector) {
        for(Material entry : MATERIALS.values()) {
            for(EquipmentType type : ZineUtil.HUMANOID_EQUIPMENT_TYPES) {
                Models.GENERATED.upload(
                        entry.equipmentModelIds.get(type),
                        TextureMap.layer0(entry.colorPaletteTexture.withPath("trims/items/" + type.asString() + "_trim_" + entry.name)),
                        modelCollector
                );
            }
        }
    }

    private record Material(String name, Identifier colorPaletteTexture, Map<EquipmentType, Identifier> equipmentModelIds) {
    }

    private record Pattern(Map<EquipmentModel.LayerType, Identifier> equipmentTextures) {
    }

}
