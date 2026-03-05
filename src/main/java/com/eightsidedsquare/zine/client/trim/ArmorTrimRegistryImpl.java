package com.eightsidedsquare.zine.client.trim;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public final class ArmorTrimRegistryImpl {
    private ArmorTrimRegistryImpl() {
    }

    private static final Identifier TRIM_PALETTE_KEY = Identifier.withDefaultNamespace("trims/color_palettes/trim_palette");
    private static final Map<ResourceKey<TrimMaterial>, Material> MATERIALS = new Reference2ObjectOpenHashMap<>();
    private static final Map<ResourceKey<TrimPattern>, Pattern> PATTERNS = new Reference2ObjectOpenHashMap<>();
    private static final Set<Identifier> ITEM_MODEL_EXCLUDE = new ObjectOpenHashSet<>();
    private static final Map<Item, ArmorType> ARMOR_ITEMS = new Reference2ObjectOpenHashMap<>();

    private static void registerMaterial(ResourceKey<TrimMaterial> key, Material material) {
        MATERIALS.put(key, material);
    }

    static void registerMaterial(ResourceKey<TrimMaterial> key, String name, Identifier colorPaletteTexture, Map<ArmorType, Identifier> equipmentItemModelIds) {
        registerMaterial(key, new Material(name, colorPaletteTexture, equipmentItemModelIds));
    }

    static void registerMaterial(ResourceKey<TrimMaterial> key) {
        registerMaterial(key, key.identifier().getPath(), key.identifier().withPrefix("trims/color_palettes/"), createEquipmentModelIds(key));
    }

    private static void registerPattern(ResourceKey<TrimPattern> key, Pattern pattern) {
        PATTERNS.put(key, pattern);
    }

    static void registerPattern(ResourceKey<TrimPattern> key, Map<EquipmentClientInfo.LayerType, Identifier> equipmentTextures) {
        registerPattern(key, new Pattern(equipmentTextures));
    }

    static void registerPattern(ResourceKey<TrimPattern> key) {
        Map<EquipmentClientInfo.LayerType, Identifier> equipmentTextures = ImmutableMap.<EquipmentClientInfo.LayerType, Identifier>builder()
                .put(EquipmentClientInfo.LayerType.HUMANOID, key.identifier().withPrefix("trims/entity/humanoid/"))
                .put(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS, key.identifier().withPrefix("trims/entity/humanoid_leggings/"))
                .build();
        registerPattern(key, equipmentTextures);
    }

    static void excludeForItemModelModification(Identifier... ids) {
        ITEM_MODEL_EXCLUDE.addAll(Arrays.asList(ids));
    }

    private static Map<ArmorType, Identifier> createEquipmentModelIds(ResourceKey<TrimMaterial> key) {
        ImmutableMap.Builder<ArmorType, Identifier> builder = ImmutableMap.builder();
        String name = key.identifier().getPath();
        for(ArmorType type : ZineUtil.HUMANOID_EQUIPMENT_TYPES) {
            builder.put(type, key.identifier().withPath("item/" + type.getSerializedName() + "_" + name + "_trim"));
        }
        return builder.build();
    }

    public static boolean containsMaterial(ResourceKey<TrimMaterial> key) {
        return MATERIALS.containsKey(key);
    }

    public static void modifyItemsAtlas(List<SpriteSource> sources) {
        modifyPalettedSource(sources, ArmorTrimRegistryImpl::applyMaterials);
    }

    public static void modifyArmorTrimsAtlas(List<SpriteSource> sources) {
        modifyPalettedSource(sources, source -> {
            applyMaterials(source);
            applyPatterns(source);
        });
    }

    public static void addArmorItem(Item item, ArmorType armorType) {
        ARMOR_ITEMS.put(item, armorType);
    }

    private static void modifyPalettedSource(List<SpriteSource> sources, Consumer<PalettedPermutations> consumer) {
        for(SpriteSource source : sources) {
            if(source instanceof PalettedPermutations palettedSource && palettedSource.zine$getPaletteKey().equals(TRIM_PALETTE_KEY)) {
                consumer.accept(palettedSource);
            }
        }
    }

    private static void applyMaterials(PalettedPermutations source) {
        MATERIALS.values().forEach(entry -> source.zine$addNamespacedPermutation(entry.name, entry.colorPaletteTexture));
    }

    private static void applyPatterns(PalettedPermutations source) {
        PATTERNS.values().forEach(entry -> entry.equipmentTextures.values().forEach(source::zine$addTexture));
    }

    public static ItemModel.Unbaked modifyItemModels(Identifier id, ItemModel.Unbaked unbaked) {
        if(ITEM_MODEL_EXCLUDE.contains(id)) {
            return unbaked;
        }
        Optional<Holder.Reference<Item>> itemRef = BuiltInRegistries.ITEM.get(id);
        if(itemRef.isPresent()
                && unbaked instanceof SelectItemModel.Unbaked selectModel
                && selectModel.unbakedSwitch().property() instanceof TrimMaterialProperty) {
            Item item = itemRef.get().value();
            ArmorType armorType = getArmorType(item);
            return armorType == null ? unbaked : applyMaterials(selectModel, armorType);
        }
        return unbaked;
    }

    private static ItemModel.Unbaked applyMaterials(SelectItemModel.Unbaked selectModel, ArmorType armorType) {
        if(selectModel.fallback().isEmpty()) {
            return selectModel;
        }
        ItemModel.Unbaked fallback = selectModel.fallback().get();
        selectModel.zine$addCases(TrimMaterialProperty.TYPE, MATERIALS.entrySet()
                .stream()
                .map(entry -> ItemModelUtils.when(
                        entry.getKey(),
                        ItemModelUtils.composite(
                                fallback,
                                ItemModelUtils.plainModel(entry.getValue().equipmentModelIds.get(armorType))
                        )
                ))
                .toList()
        );
        return selectModel;
    }


    @Nullable
    private static ArmorType getArmorType(Item item) {
        return ARMOR_ITEMS.get(item);
    }

    public static void addUnbakedModels(BiConsumer<Identifier, ModelInstance> modelCollector) {
        for(Material entry : MATERIALS.values()) {
            for(ArmorType type : ZineUtil.HUMANOID_EQUIPMENT_TYPES) {
                ModelTemplates.FLAT_ITEM.create(
                        entry.equipmentModelIds.get(type),
                        TextureMapping.layer0(entry.colorPaletteTexture.withPath("trims/items/" + type.getSerializedName() + "_trim_" + entry.name)),
                        modelCollector
                );
            }
        }
    }

    private record Material(String name, Identifier colorPaletteTexture, Map<ArmorType, Identifier> equipmentModelIds) {
    }

    private record Pattern(Map<EquipmentClientInfo.LayerType, Identifier> equipmentTextures) {
    }

}
