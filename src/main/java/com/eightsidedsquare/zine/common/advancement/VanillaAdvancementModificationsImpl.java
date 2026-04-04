package com.eightsidedsquare.zine.common.advancement;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanOpenHashMap;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public final class VanillaAdvancementModificationsImpl {
    private VanillaAdvancementModificationsImpl() {
    }

    private static final Identifier ADVENTURING_TIME_ID = Identifier.withDefaultNamespace("adventure/adventuring_time");
    private static final Identifier ALL_EFFECTS_ID = Identifier.withDefaultNamespace("nether/all_effects");
    private static final Identifier ALL_POTIONS_ID = Identifier.withDefaultNamespace("nether/all_potions");
    private static final Identifier BALANCED_DIET_ID = Identifier.withDefaultNamespace("husbandry/balanced_diet");
    private static final Identifier BRED_ALL_ANIMALS_ID = Identifier.withDefaultNamespace("husbandry/bred_all_animals");
    private static final Identifier COMPLETE_CATALOGUE_ID = Identifier.withDefaultNamespace("husbandry/complete_catalogue");
    private static final Identifier EXPLORE_NETHER_ID = Identifier.withDefaultNamespace("nether/explore_nether");
    private static final Identifier FISHY_BUSINESS_ID = Identifier.withDefaultNamespace("husbandry/fishy_business");
    private static final Identifier FROGLIGHTS_ID = Identifier.withDefaultNamespace("husbandry/froglights");
    private static final Identifier KILL_A_MOB_ID = Identifier.withDefaultNamespace("adventure/kill_a_mob");
    private static final Identifier KILL_ALL_MOBS_ID = Identifier.withDefaultNamespace("adventure/kill_all_mobs");
    private static final Identifier LEASH_ALL_FROG_VARIANTS_ID = Identifier.withDefaultNamespace("husbandry/leash_all_frog_variants");
    private static final Identifier LIGHTEN_UP_ID = Identifier.withDefaultNamespace("adventure/lighten_up");
    private static final Identifier LOOT_BASTION_ID = Identifier.withDefaultNamespace("nether/loot_bastion");
    private static final Identifier PLANT_ANY_SNIFFER_SEED_ID = Identifier.withDefaultNamespace("husbandry/plant_any_sniffer_seed");
    private static final Identifier PLANT_SEED_ID = Identifier.withDefaultNamespace("husbandry/plant_seed");
    private static final Identifier SALVAGE_SHERD_ID = Identifier.withDefaultNamespace("adventure/salvage_sherd");
    private static final Identifier TACTICAL_FISHING_ID = Identifier.withDefaultNamespace("husbandry/tactical_fishing");
    private static final Identifier TRIM_WITH_ANY_ARMOR_PATTERN_ID = Identifier.withDefaultNamespace("adventure/trim_with_any_armor_pattern");
    private static final Identifier WAX_OFF_ID = Identifier.withDefaultNamespace("husbandry/wax_off");
    private static final Identifier WAX_ON_ID = Identifier.withDefaultNamespace("husbandry/wax_on");
    private static final Identifier WHOLE_PACK_ID = Identifier.withDefaultNamespace("husbandry/whole_pack");

    private static final TreeSet<ResourceKey<Biome>> ADVENTURING_TIME_BIOMES = registryKeySet();
    private static final Reference2BooleanMap<Holder<MobEffect>> ALL_EFFECTS_STATUS_EFFECTS = new Reference2BooleanOpenHashMap<>();
    private static final TreeSet<Item> BALANCED_DIET_ITEMS = set(BuiltInRegistries.ITEM);
    private static final Reference2BooleanMap<EntityType<?>> BREEDABLE_ANIMALS = new Reference2BooleanOpenHashMap<>();
    private static final TreeSet<ResourceKey<CatVariant>> COMPLETE_CATALOGUE_CAT_VARIANTS = registryKeySet();
    private static final TreeSet<ResourceKey<Biome>> EXPLORE_NETHER_BIOMES = registryKeySet();
    private static final TreeSet<Item> FISHY_BUSINESS_ITEMS = set(BuiltInRegistries.ITEM);
    private static final TreeSet<Item> FROGLIGHTS_ITEMS = set(BuiltInRegistries.ITEM);
    private static final TreeSet<EntityType<?>> KILLABLE_MOBS = set(BuiltInRegistries.ENTITY_TYPE);
    private static final TreeSet<ResourceKey<FrogVariant>> LEASH_ALL_FROG_VARIANTS_FROG_VARIANTS = registryKeySet();
    private static final TreeSet<Block> LIGHTEN_UP_BLOCKS = set(BuiltInRegistries.BLOCK);
    private static final TreeSet<ResourceKey<LootTable>> LOOT_BASTION_LOOT_TABLES = registryKeySet();
    private static final Reference2BooleanMap<Block> PLANT_SEED_BLOCKS = new Reference2BooleanOpenHashMap<>();
    private static final TreeSet<ResourceKey<LootTable>> SALVAGE_SHERD_LOOT_TABLES = registryKeySet();
    private static final TreeSet<Item> SCRAPING_AXE_ITEMS = set(BuiltInRegistries.ITEM);
    private static final TreeSet<Item> TACTICAL_FISHING_ITEMS = set(BuiltInRegistries.ITEM);
    private static final TreeSet<ResourceKey<Recipe<?>>> TRIM_WITH_ANY_ARMOR_PATTERN_RECIPES = registryKeySet();
    private static final TreeSet<Block> WAX_OFF_BLOCKS = set(BuiltInRegistries.BLOCK);
    private static final TreeSet<Block> WAX_ON_BLOCKS = set(BuiltInRegistries.BLOCK);
    private static final TreeSet<ResourceKey<WolfVariant>> WHOLE_PACK_WOLF_VARIANTS = registryKeySet();

    public static void registerEvents() {
        AdvancementEvents.modifyAdvancement(ADVENTURING_TIME_ID, VanillaAdvancementModificationsImpl::modifyAdventuringTime);
        AdvancementEvents.modifyAdvancement(ALL_EFFECTS_ID, VanillaAdvancementModificationsImpl::modifyAllEffects);
        AdvancementEvents.modifyAdvancement(ALL_POTIONS_ID, VanillaAdvancementModificationsImpl::modifyAllPotions);
        AdvancementEvents.modifyAdvancement(BALANCED_DIET_ID, VanillaAdvancementModificationsImpl::modifyBalancedDiet);
        AdvancementEvents.modifyAdvancement(BRED_ALL_ANIMALS_ID, VanillaAdvancementModificationsImpl::modifyBredAllAnimals);
        AdvancementEvents.modifyAdvancement(COMPLETE_CATALOGUE_ID, VanillaAdvancementModificationsImpl::modifyCompleteCatalogue);
        AdvancementEvents.modifyAdvancement(EXPLORE_NETHER_ID, VanillaAdvancementModificationsImpl::modifyExploreNether);
        AdvancementEvents.modifyAdvancement(FISHY_BUSINESS_ID, VanillaAdvancementModificationsImpl::modifyFishyBusiness);
        AdvancementEvents.modifyAdvancement(FROGLIGHTS_ID, VanillaAdvancementModificationsImpl::modifyFroglights);
        AdvancementEvents.modifyAdvancement(KILL_A_MOB_ID, VanillaAdvancementModificationsImpl::modifyKillAMob);
        AdvancementEvents.modifyAdvancement(KILL_ALL_MOBS_ID, VanillaAdvancementModificationsImpl::modifyKillAllMobs);
        AdvancementEvents.modifyAdvancement(LEASH_ALL_FROG_VARIANTS_ID, VanillaAdvancementModificationsImpl::modifyLeashAllFrogVariants);
        AdvancementEvents.modifyAdvancement(LIGHTEN_UP_ID, VanillaAdvancementModificationsImpl::modifyLightenUp);
        AdvancementEvents.modifyAdvancement(LOOT_BASTION_ID, VanillaAdvancementModificationsImpl::modifyLootBastion);
        AdvancementEvents.modifyAdvancement(PLANT_ANY_SNIFFER_SEED_ID, VanillaAdvancementModificationsImpl::modifyPlantAnySnifferSeed);
        AdvancementEvents.modifyAdvancement(PLANT_SEED_ID, VanillaAdvancementModificationsImpl::modifyPlantSeed);
        AdvancementEvents.modifyAdvancement(SALVAGE_SHERD_ID, VanillaAdvancementModificationsImpl::modifySalvageSherd);
        AdvancementEvents.modifyAdvancement(TACTICAL_FISHING_ID, VanillaAdvancementModificationsImpl::modifyTacticalFishing);
        AdvancementEvents.modifyAdvancement(TRIM_WITH_ANY_ARMOR_PATTERN_ID, VanillaAdvancementModificationsImpl::modifyTrimWithAnyArmorPattern);
        AdvancementEvents.modifyAdvancement(WAX_OFF_ID, VanillaAdvancementModificationsImpl::modifyWaxOff);
        AdvancementEvents.modifyAdvancement(WAX_ON_ID, VanillaAdvancementModificationsImpl::modifyWaxOn);
        AdvancementEvents.modifyAdvancement(WHOLE_PACK_ID, VanillaAdvancementModificationsImpl::modifyWholePack);
    }

    public static void registerAdventuringTimeBiome(ResourceKey<Biome> biomeKey) {
        ADVENTURING_TIME_BIOMES.add(biomeKey);
    }

    public static void registerAllEffectsStatusEffect(Holder<MobEffect> statusEffect, boolean potion) {
        ALL_EFFECTS_STATUS_EFFECTS.put(statusEffect, potion);
    }

    public static void registerBalancedDietFood(Item item) {
        BALANCED_DIET_ITEMS.add(item);
    }

    public static void registerBreedableAnimal(EntityType<?> entityType, boolean laysEgg) {
        BREEDABLE_ANIMALS.put(entityType, laysEgg);
    }

    public static void registerCompleteCatalogueCatVariant(ResourceKey<CatVariant> catVariantKey) {
        COMPLETE_CATALOGUE_CAT_VARIANTS.add(catVariantKey);
    }

    public static void registerExploreNetherBiome(ResourceKey<Biome> biomeKey) {
        EXPLORE_NETHER_BIOMES.add(biomeKey);
    }

    public static void registerFishyBusinessItem(Item item) {
        FISHY_BUSINESS_ITEMS.add(item);
    }

    public static void registerFroglightsItem(Item item) {
        FROGLIGHTS_ITEMS.add(item);
    }

    public static void registerKillableMobEntityType(EntityType<?> entityType) {
        KILLABLE_MOBS.add(entityType);
    }

    public static void registerLeashAllFrogVariantsFrogVariant(ResourceKey<FrogVariant> frogVariantKey) {
        LEASH_ALL_FROG_VARIANTS_FROG_VARIANTS.add(frogVariantKey);
    }

    public static void registerLightenUpBlock(Block block) {
        LIGHTEN_UP_BLOCKS.add(block);
    }

    public static void registerLootBastionLootTable(ResourceKey<LootTable> lootTableKey) {
        LOOT_BASTION_LOOT_TABLES.add(lootTableKey);
    }

    public static void registerPlantSeedBlock(Block block, boolean fromSniffer) {
        PLANT_SEED_BLOCKS.put(block, fromSniffer);
    }

    public static void registerSalvageSherdLootTable(ResourceKey<LootTable> lootTableKey) {
        SALVAGE_SHERD_LOOT_TABLES.add(lootTableKey);
    }

    public static void registerScrapingAxeItem(Item item) {
        SCRAPING_AXE_ITEMS.add(item);
    }

    public static void registerTacticalFishingBucketItem(Item item) {
        TACTICAL_FISHING_ITEMS.add(item);
    }

    public static void registerTrimWithAnyArmorPatternRecipe(ResourceKey<Recipe<?>> recipeKey) {
        TRIM_WITH_ANY_ARMOR_PATTERN_RECIPES.add(recipeKey);
    }

    public static void registerWaxOffBlock(Block block) {
        WAX_OFF_BLOCKS.add(block);
    }

    public static void registerWaxOnBlock(Block block) {
        WAX_ON_BLOCKS.add(block);
    }

    public static void registerWholePackWolfVariant(ResourceKey<WolfVariant> wolfVariantKey) {
        WHOLE_PACK_WOLF_VARIANTS.add(wolfVariantKey);
    }

    private static Advancement modifyAdventuringTime(Advancement advancement, HolderLookup.Provider lookup) {
        lookup.lookup(Registries.BIOME).ifPresent(biomeLookup -> {
            for(ResourceKey<Biome> biomeKey : ADVENTURING_TIME_BIOMES) {
                Optional<Holder.Reference<Biome>> biome = biomeLookup.get(biomeKey);
                if(biome.isPresent()) {
                    String name = biomeKey.identifier().toString();
                    advancement.zine$addCriterion(name, PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inBiome(biome.get())
                    ));
                    advancement.requirements().zine$addRequirement(List.of(name));
                }
            }
        });
        return advancement;
    }

    private static Advancement modifyAllEffects(Advancement advancement, HolderLookup.Provider lookup) {
        advancement.zine$getCriterion("all_effects", CriteriaTriggers.EFFECTS_CHANGED)
                .flatMap(EffectsChangedTrigger.TriggerInstance::effects)
                .ifPresent(predicate -> ALL_EFFECTS_STATUS_EFFECTS.keySet().forEach(predicate::zine$addEffect));
        return advancement;
    }

    private static Advancement modifyAllPotions(Advancement advancement, HolderLookup.Provider lookup) {
        advancement.zine$getCriterion("all_effects", CriteriaTriggers.EFFECTS_CHANGED)
                .flatMap(EffectsChangedTrigger.TriggerInstance::effects)
                .ifPresent(predicate -> ALL_EFFECTS_STATUS_EFFECTS.forEach((statusEffect, potion) -> {
                    if(potion) {
                        predicate.zine$addEffect(statusEffect);
                    }
                }));
        return advancement;
    }

    private static Advancement modifyBalancedDiet(Advancement advancement, HolderLookup.Provider lookup) {
        for(Item item : BALANCED_DIET_ITEMS) {
            String name = BuiltInRegistries.ITEM.getKey(item).toString();
            advancement.zine$addCriterion(name, ConsumeItemTrigger.TriggerInstance.usedItem(BuiltInRegistries.ITEM, item));
            advancement.requirements().zine$addRequirement(List.of(name));
        }
        return advancement;
    }

    private static Advancement modifyBredAllAnimals(Advancement advancement, HolderLookup.Provider lookup) {
        for(Reference2BooleanMap.Entry<EntityType<?>> entry : BREEDABLE_ANIMALS.reference2BooleanEntrySet()) {
            EntityType<?> entityType = entry.getKey();
            boolean laysEgg = entry.getBooleanValue();
            String name = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();

            if(laysEgg) {
                advancement.zine$addCriterion(name, BredAnimalsTrigger.TriggerInstance.bredAnimals(
                        Optional.of(EntityPredicate.Builder.entity().of(BuiltInRegistries.ENTITY_TYPE, entityType).build()),
                        Optional.of(EntityPredicate.Builder.entity().of(BuiltInRegistries.ENTITY_TYPE, entityType).build()),
                        Optional.empty()
                ));
            }else {
                advancement.zine$addCriterion(name, BredAnimalsTrigger.TriggerInstance.bredAnimals(
                        EntityPredicate.Builder.entity().of(BuiltInRegistries.ENTITY_TYPE, entityType)
                ));
            }
            advancement.requirements().zine$addRequirement(List.of(name));
        }
        return advancement;
    }

    private static Advancement modifyCompleteCatalogue(Advancement advancement, HolderLookup.Provider lookup) {
        lookup.lookup(Registries.CAT_VARIANT).ifPresent(catVariantLookup -> {
            for(ResourceKey<CatVariant> variantKey : COMPLETE_CATALOGUE_CAT_VARIANTS) {
                Optional<Holder.Reference<CatVariant>> variant = catVariantLookup.get(variantKey);
                if(variant.isPresent()) {
                    String name = variantKey.identifier().toString();
                    advancement.zine$addCriterion(name, TameAnimalTrigger.TriggerInstance.tamedAnimal(
                            EntityPredicate.Builder.entity().components(
                                    DataComponentMatchers.Builder.components()
                                            .exact(DataComponentExactPredicate.expect(DataComponents.CAT_VARIANT, variant.get()))
                                            .build()
                            )
                    ));
                    advancement.requirements().zine$addRequirement(List.of(name));
                }
            }
        });
        return advancement;
    }

    private static Advancement modifyExploreNether(Advancement advancement, HolderLookup.Provider lookup) {
        lookup.lookup(Registries.BIOME).ifPresent(biomeLookup -> {
            for(ResourceKey<Biome> biomeKey : EXPLORE_NETHER_BIOMES) {
                Optional<Holder.Reference<Biome>> biome = biomeLookup.get(biomeKey);
                if(biome.isPresent()) {
                    String name = biomeKey.identifier().toString();
                    advancement.zine$addCriterion(name, PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inBiome(biome.get())
                    ));
                    advancement.requirements().zine$addRequirement(List.of(name));
                }
            }
        });
        return advancement;
    }

    private static Advancement modifyFishyBusiness(Advancement advancement, HolderLookup.Provider lookup) {
        for(Item item : FISHY_BUSINESS_ITEMS) {
            String name = BuiltInRegistries.ITEM.getKey(item).toString();
            advancement.zine$addCriterion(name, FishingRodHookedTrigger.TriggerInstance.fishedItem(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(ItemPredicate.Builder.item().of(BuiltInRegistries.ITEM, item).build())
            ));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyFroglights(Advancement advancement, HolderLookup.Provider lookup) {
        advancement.zine$getCriterion("froglights", CriteriaTriggers.INVENTORY_CHANGED).ifPresent(conditions -> {
            ImmutableList.Builder<ItemPredicate> items = ImmutableList.builder();
            items.addAll(conditions.items());
            FROGLIGHTS_ITEMS.forEach(item -> items.add(ItemPredicate.Builder.item().of(BuiltInRegistries.ITEM, item).build()));
            advancement.zine$addCriterion("froglights", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                    new InventoryChangeTrigger.TriggerInstance(
                            conditions.player(),
                            conditions.slots(),
                            items.build()
                    )
            ));
        });
        return advancement;
    }

    private static Advancement modifyKillAMob(Advancement advancement, HolderLookup.Provider lookup) {
        for(EntityType<?> entityType : KILLABLE_MOBS) {
            String name = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
            advancement.zine$addCriterion(name, KilledTrigger.TriggerInstance.playerKilledEntity(
                    EntityPredicate.Builder.entity().of(BuiltInRegistries.ENTITY_TYPE, entityType)
            ));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyKillAllMobs(Advancement advancement, HolderLookup.Provider lookup) {
        for(EntityType<?> entityType : KILLABLE_MOBS) {
            String name = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
            advancement.zine$addCriterion(name, KilledTrigger.TriggerInstance.playerKilledEntity(
                    EntityPredicate.Builder.entity().of(BuiltInRegistries.ENTITY_TYPE, entityType)
            ));
            advancement.requirements().zine$addRequirement(List.of(name));
        }
        return advancement;
    }

    private static Advancement modifyLeashAllFrogVariants(Advancement advancement, HolderLookup.Provider lookup) {
        lookup.lookup(Registries.FROG_VARIANT).ifPresent(frogVariantLookup -> {
            ItemPredicate.Builder leadPredicate = ItemPredicate.Builder.item().of(BuiltInRegistries.ITEM, Items.LEAD);
            for(ResourceKey<FrogVariant> variantKey : LEASH_ALL_FROG_VARIANTS_FROG_VARIANTS) {
                Optional<Holder.Reference<FrogVariant>> variant = frogVariantLookup.get(variantKey);
                if(variant.isPresent()) {
                    String name = variantKey.identifier().toString();
                    advancement.zine$addCriterion(name, PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                            leadPredicate,
                            Optional.of(
                                    EntityPredicate.wrap(
                                            EntityPredicate.Builder.entity()
                                                    .of(BuiltInRegistries.ENTITY_TYPE, EntityType.FROG)
                                                    .components(DataComponentMatchers.Builder.components()
                                                            .exact(DataComponentExactPredicate.expect(DataComponents.FROG_VARIANT, variant.get()))
                                                            .build()
                                                    )
                                    )
                            )
                    ));
                    advancement.requirements().zine$addRequirement(List.of(name));
                }
            }
        });
        return advancement;
    }

    private static Advancement modifyLightenUp(Advancement advancement, HolderLookup.Provider lookup) {
        advancement.zine$getCriterion("lighten_up", CriteriaTriggers.ITEM_USED_ON_BLOCK)
                .flatMap(ItemUsedOnLocationTrigger.TriggerInstance::location)
                .ifPresent(predicate -> {
                    for(LootItemCondition lootCondition : predicate.zine$getConditions()) {
                        if(lootCondition instanceof LocationCheck locationCondition) {
                            locationCondition.predicate()
                                    .flatMap(LocationPredicate::block)
                                    .ifPresent(blockPredicate -> blockPredicate.zine$addBlocks(LIGHTEN_UP_BLOCKS));
                        }else if(lootCondition instanceof MatchTool(Optional<ItemPredicate> optional)) {
                            optional.ifPresent(itemPredicate -> itemPredicate.zine$addItems(SCRAPING_AXE_ITEMS));
                        }
                    }
                });
        return advancement;
    }

    private static Advancement modifyLootBastion(Advancement advancement, HolderLookup.Provider lookup) {
        for(ResourceKey<LootTable> lootTableKey : LOOT_BASTION_LOOT_TABLES) {
            String name = lootTableKey.identifier().toString();
            advancement.zine$addCriterion(name, LootTableTrigger.TriggerInstance.lootTableUsed(lootTableKey));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyPlantAnySnifferSeed(Advancement advancement, HolderLookup.Provider lookup) {
        for(Reference2BooleanMap.Entry<Block> entry : PLANT_SEED_BLOCKS.reference2BooleanEntrySet()) {
            if(!entry.getBooleanValue()) {
                continue;
            }
            Block block = entry.getKey();
            String name = BuiltInRegistries.BLOCK.getKey(block).toString();
            advancement.zine$addCriterion(name, ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(block));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyPlantSeed(Advancement advancement, HolderLookup.Provider lookup) {
        for(Block block : PLANT_SEED_BLOCKS.keySet()) {
            String name = BuiltInRegistries.BLOCK.getKey(block).toString();
            advancement.zine$addCriterion(name, ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(block));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifySalvageSherd(Advancement advancement, HolderLookup.Provider lookup) {
        for(ResourceKey<LootTable> lootTableKey : SALVAGE_SHERD_LOOT_TABLES) {
            String name = lootTableKey.identifier().toString();
            advancement.zine$addCriterion(name, LootTableTrigger.TriggerInstance.lootTableUsed(lootTableKey));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyTacticalFishing(Advancement advancement, HolderLookup.Provider lookup) {
        for(Item item : TACTICAL_FISHING_ITEMS) {
            String name = BuiltInRegistries.ITEM.getKey(item).toString();
            advancement.zine$addCriterion(name, FilledBucketTrigger.TriggerInstance.filledBucket(
                    ItemPredicate.Builder.item().of(BuiltInRegistries.ITEM, item)
            ));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyTrimWithAnyArmorPattern(Advancement advancement, HolderLookup.Provider lookup) {
        for(ResourceKey<Recipe<?>> recipeKey : TRIM_WITH_ANY_ARMOR_PATTERN_RECIPES) {
            String name = "armor_trimmed_" + recipeKey.identifier();
            advancement.zine$addCriterion(name, RecipeCraftedTrigger.TriggerInstance.craftedItem(recipeKey));
            advancement.requirements().zine$addRequirement(0, name);
        }
        return advancement;
    }

    private static Advancement modifyWaxOff(Advancement advancement, HolderLookup.Provider lookup) {
        advancement.zine$getCriterion("wax_off", CriteriaTriggers.ITEM_USED_ON_BLOCK)
                .flatMap(ItemUsedOnLocationTrigger.TriggerInstance::location)
                .ifPresent(predicate -> {
                    for(LootItemCondition lootCondition : predicate.zine$getConditions()) {
                        if(lootCondition instanceof LocationCheck locationCondition) {
                            locationCondition.predicate()
                                    .flatMap(LocationPredicate::block)
                                    .ifPresent(blockPredicate -> blockPredicate.zine$addBlocks(WAX_OFF_BLOCKS));
                        }else if(lootCondition instanceof MatchTool(Optional<ItemPredicate> optional)) {
                            optional.ifPresent(itemPredicate -> itemPredicate.zine$addItems(SCRAPING_AXE_ITEMS));
                        }
                    }
                });
        return advancement;
    }

    private static Advancement modifyWaxOn(Advancement advancement, HolderLookup.Provider lookup) {
        advancement.zine$getCriterion("wax_on", CriteriaTriggers.ITEM_USED_ON_BLOCK)
                .flatMap(ItemUsedOnLocationTrigger.TriggerInstance::location)
                .ifPresent(predicate -> {
                    for(LootItemCondition lootCondition : predicate.zine$getConditions()) {
                        if(lootCondition instanceof LocationCheck locationCondition) {
                            locationCondition.predicate()
                                    .flatMap(LocationPredicate::block)
                                    .ifPresent(blockPredicate -> blockPredicate.zine$addBlocks(WAX_ON_BLOCKS));
                        }
                    }
                });
        return advancement;
    }

    private static Advancement modifyWholePack(Advancement advancement, HolderLookup.Provider lookup) {
        lookup.lookup(Registries.WOLF_VARIANT).ifPresent(wolfVariantLookup -> {
            for(ResourceKey<WolfVariant> variantKey : WHOLE_PACK_WOLF_VARIANTS) {
                Optional<Holder.Reference<WolfVariant>> variant = wolfVariantLookup.get(variantKey);
                if(variant.isPresent()) {
                    String name = variantKey.identifier().toString();
                    advancement.zine$addCriterion(name, TameAnimalTrigger.TriggerInstance.tamedAnimal(
                            EntityPredicate.Builder.entity().components(
                                    DataComponentMatchers.Builder.components()
                                            .exact(DataComponentExactPredicate.expect(DataComponents.WOLF_VARIANT, variant.get()))
                                            .build()
                            )
                    ));
                    advancement.requirements().zine$addRequirement(List.of(name));
                }
            }
        });
        return advancement;
    }

    private static <T> TreeSet<T> set(Registry<T> registry) {
        return new TreeSet<>(Comparator.comparingInt(registry::getId));
    }

    private static <T> TreeSet<ResourceKey<T>> registryKeySet() {
        return new TreeSet<>(Comparator.comparing(ResourceKey::identifier));
    }
}
