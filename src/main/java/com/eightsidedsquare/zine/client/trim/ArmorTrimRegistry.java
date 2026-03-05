package com.eightsidedsquare.zine.client.trim;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;

import java.util.Arrays;
import java.util.Map;

/**
 * Offers a method of adding armor trim materials and patterns in a (theoretically) mod-compatible manner.
 * <p>These methods do not register {@link TrimMaterial armor trim materials} or {@link TrimPattern armor trim patterns}.
 * They must still be registered through JSON or {@link net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider data generation}.
 * <p>This API has trouble with the following cases:
 * <ul>
 *     <li>The trim item overlay may apply strangely to armor item models that do not match the form of vanilla's armor item models.
 *     Use {@link #excludeForItemModelModification(Item...)} or {@link #excludeForItemModelModification(Identifier...)}
 *     to exclude items.
 *     <li>The trim item overlay will not be applied to non-armor item models.
 *     <li>The trim item overlay will not be applied to armor item models that don't use
 *     {@link net.minecraft.client.renderer.item.SelectItemModel minecraft:select} as their model type.
 *     <li>Custom equipment renderer implementations that don't handle trims with {@code EquipmentRenderer$TrimSpriteKey}
 *     (private class) will use a missing texture for non-vanilla materials.
 * </ul>
 */
@Environment(EnvType.CLIENT)
public final class ArmorTrimRegistry {
    private ArmorTrimRegistry() {
    }

    /**
     * Registers a material.
     * @param key the registry key of the armor trim material
     * @param name the name of the material, used for texture permutations
     * @param colorPaletteTexture the resource location of the color palette texture.
     *                            Usually, this value is {@code <namespace>:trims/color_palettes/<name>}
     * @param equipmentItemModelIds a map that supplies the item texture given an equipment type
     */
    public static void registerMaterial(ResourceKey<TrimMaterial> key, String name, Identifier colorPaletteTexture, Map<ArmorType, Identifier> equipmentItemModelIds) {
        ArmorTrimRegistryImpl.registerMaterial(key, name, colorPaletteTexture, equipmentItemModelIds);
    }

    /**
     * Registers a material with standard naming schemes.
     * @param key the registry key of the armor trim material
     */
    public static void registerMaterial(ResourceKey<TrimMaterial> key) {
        ArmorTrimRegistryImpl.registerMaterial(key);
    }

    /**
     * Registers a pattern.
     * @param key the registry key of the armor trim pattern
     * @param equipmentTextures a map that supplies the equipment texture given an equipment type
     */
    public void registerPattern(ResourceKey<TrimPattern> key, Map<EquipmentClientInfo.LayerType, Identifier> equipmentTextures) {
        ArmorTrimRegistryImpl.registerPattern(key, equipmentTextures);
    }

    /**
     * Registers a pattern with standard naming schemes.
     * @param key the registry key of the armor trim pattern
     */
    public static void registerPattern(ResourceKey<TrimPattern> key) {
        ArmorTrimRegistryImpl.registerPattern(key);
    }

    /**
     * @param ids identifiers of item models that should be excluded for item model modification,
     *            in which the trim overlay is added
     */
    public static void excludeForItemModelModification(Identifier... ids) {
        ArmorTrimRegistryImpl.excludeForItemModelModification(ids);
    }

    /**
     * @param items items that should be excluded for item model modification,
     *            in which the trim overlay is added
     */
    public static void excludeForItemModelModification(Item... items) {
        ArmorTrimRegistryImpl.excludeForItemModelModification(Arrays.stream(items).map(BuiltInRegistries.ITEM::getKey).toArray(Identifier[]::new));
    }

}
