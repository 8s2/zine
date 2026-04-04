package com.eightsidedsquare.zine.common.advancement;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

public final class VanillaAdvancementModifications {
    private VanillaAdvancementModifications() {
    }

    public static void registerAdventuringTimeBiome(ResourceKey<Biome> biomeKey) {
        VanillaAdvancementModificationsImpl.registerAdventuringTimeBiome(biomeKey);
    }

    public static void registerAllEffectsStatusEffect(Holder<MobEffect> statusEffect, boolean potion) {
        VanillaAdvancementModificationsImpl.registerAllEffectsStatusEffect(statusEffect, potion);
    }

    public static void registerBalancedDietFood(Item item) {
        VanillaAdvancementModificationsImpl.registerBalancedDietFood(item);
    }

    public static void registerBreedableAnimal(EntityType<?> entityType, boolean laysEgg) {
        VanillaAdvancementModificationsImpl.registerBreedableAnimal(entityType, laysEgg);
    }

    public static void registerCompleteCatalogueCatVariant(ResourceKey<CatVariant> catVariantKey) {
        VanillaAdvancementModificationsImpl.registerCompleteCatalogueCatVariant(catVariantKey);
    }

    public static void registerExploreNetherBiome(ResourceKey<Biome> biomeKey) {
        VanillaAdvancementModificationsImpl.registerExploreNetherBiome(biomeKey);
    }

    public static void registerFishyBusinessItem(Item item) {
        VanillaAdvancementModificationsImpl.registerFishyBusinessItem(item);
    }

    public static void registerFroglightsItem(Item item) {
        VanillaAdvancementModificationsImpl.registerFroglightsItem(item);
    }

    public static void registerKillableMobEntityType(EntityType<?> entityType) {
        VanillaAdvancementModificationsImpl.registerKillableMobEntityType(entityType);
    }

    public static void registerLeashAllFrogVariantsFrogVariant(ResourceKey<FrogVariant> frogVariantKey) {
        VanillaAdvancementModificationsImpl.registerLeashAllFrogVariantsFrogVariant(frogVariantKey);
    }

    public static void registerLightenUpBlock(Block block) {
        VanillaAdvancementModificationsImpl.registerLightenUpBlock(block);
    }

    public static void registerLootBastionLootTable(ResourceKey<LootTable> lootTableKey) {
        VanillaAdvancementModificationsImpl.registerLootBastionLootTable(lootTableKey);
    }

    public static void registerPlantSeedBlock(Block block, boolean fromSniffer) {
        VanillaAdvancementModificationsImpl.registerPlantSeedBlock(block, fromSniffer);
    }

    public static void registerSalvageSherdLootTable(ResourceKey<LootTable> lootTableKey) {
        VanillaAdvancementModificationsImpl.registerSalvageSherdLootTable(lootTableKey);
    }

    public static void registerScrapingAxeItem(Item item) {
        VanillaAdvancementModificationsImpl.registerScrapingAxeItem(item);
    }

    public static void registerTacticalFishingBucketItem(Item item) {
        VanillaAdvancementModificationsImpl.registerTacticalFishingBucketItem(item);
    }

    public static void registerTrimWithAnyArmorPatternRecipe(ResourceKey<Recipe<?>> recipeKey) {
        VanillaAdvancementModificationsImpl.registerTrimWithAnyArmorPatternRecipe(recipeKey);
    }

    public static void registerWaxOffBlock(Block block) {
        VanillaAdvancementModificationsImpl.registerWaxOffBlock(block);
    }

    public static void registerWaxOnBlock(Block block) {
        VanillaAdvancementModificationsImpl.registerWaxOnBlock(block);
    }

    public static void registerWholePackWolfVariant(ResourceKey<WolfVariant> wolfVariantKey) {
        VanillaAdvancementModificationsImpl.registerWholePackWolfVariant(wolfVariantKey);
    }
}
