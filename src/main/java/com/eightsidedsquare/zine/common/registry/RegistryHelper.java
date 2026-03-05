package com.eightsidedsquare.zine.common.registry;

import com.eightsidedsquare.zine.common.item.CustomIngredientSerializerImpl;
import com.eightsidedsquare.zine.common.recipe.RecipeTypeImpl;
import com.eightsidedsquare.zine.common.text.TextUtil;
import com.eightsidedsquare.zine.common.text.TextUtilImpl;
import com.eightsidedsquare.zine.common.util.codec.RegistryCodecGroup;
import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.data.DataSource;
import net.minecraft.network.chat.contents.objects.ObjectInfo;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.NumberFormatType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.server.dialog.action.Action;
import net.minecraft.server.dialog.body.DialogBody;
import net.minecraft.server.dialog.input.InputControl;
import net.minecraft.server.jsonrpc.IncomingRpcMethod;
import net.minecraft.server.jsonrpc.OutgoingRpcMethod;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.Util;
import net.minecraft.util.debug.DebugSubscription;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.attribute.AttributeType;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import net.minecraft.world.item.slot.SlotSource;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifierType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.function.*;

/**
 * A helper class to streamline object registration.
 *
 * <p>Use {@link #create(String)} to instantiate a {@link RegistryHelper} for a given namespace.
 * For best practice, store the registry helper as a static constant that can be accessed across different index classes.
 *
 * <p>If your mod or your mod's dependencies add new registries,
 * an implementation of this class or {@link RegistryHelperImpl} can be made, with methods to support them.
 */
public interface RegistryHelper {

    /**
     * Creates an instance of the default implementation of {@link RegistryHelper}.
     */
    static RegistryHelper create(String namespace) {
        return new RegistryHelperImpl(namespace);
    }

    /**
     * @param name the Identifier's path
     * @return an {@link Identifier} with the {@link RegistryHelper}'s namespace
     */
    Identifier id(String name);

    /**
     * @param registryKey the registry key of the registry in the root registry
     * @param name the path of the registry key's value
     * @return the registry key for a value of the registry specified by {@code registryKey}.
     * @param <T> the type of the registry key
     */
    default <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, this.id(name));
    }

    /**
     * Registers a value to the given registry.
     * @param registry the static registry to register {@code value} to
     * @param name the path of the registered value's identifier
     * @param value the value to register
     * @return {@code value}, now registered
     */
    default <T, V extends T> V register(Registry<T> registry, String name, V value) {
        return Registry.register(registry, this.id(name), value);
    }

    /**
     * Registers a value to the given registry.
     * @param registry the static registry to register {@code value} to
     * @param name the path of the registered value's identifier
     * @param value the value to register
     * @return registered {@code value} wrapped in a {@link Holder.Reference}
     */
    default <T> Holder.Reference<T> registerReference(Registry<T> registry, String name, T value) {
        return Registry.registerForHolder(registry, this.id(name), value);
    }

    /**
     * Creates a registry queue. See {@link RegistryQueue} for more info.
     * @param registry the registry that the registry queue uses
     * @return the registry queue
     * @param <T> the type of the registry
     */
    default <T> RegistryQueue<T> createQueue(Registry<T> registry) {
        return new RegistryQueueImpl<>(registry, this);
    }

    /**
     * @param name the name of the item
     * @param item the item to register
     * @return the registered item
     * @param <T> the type of the item
     * @apiNote Prioritize the other {@code item} methods over this one,
     * as they handle passing a required {@link ResourceKey} to the {@link Item.Properties}.
     */
    default <T extends Item> T item(String name, T item) {
        return this.register(BuiltInRegistries.ITEM, name, item);
    }

    /**
     *
     * @param name the name of the item
     * @param settings the item's settings
     * @param factory the factory to instantiate an {@link Item} of type {@code <T>} with the given settings
     * @return the registered item created by the {@code factory} with {@code settings}
     * @param <T> the type of the item
     */
    default <T extends Item> T item(String name, Item.Properties settings, Function<Item.Properties, T> factory) {
        return this.item(name, factory.apply(settings.setId(this.key(Registries.ITEM, name))));
    }

    /**
     * Registers an item of type {@link Item}.
     * @param name the name of the item
     * @param settings the item's settings
     * @return the registered item with {@code settings}
     */
    default Item item(String name, Item.Properties settings) {
        return this.item(name, settings, Item::new);
    }

    /**
     * Registers an item of type {@link Item} with the default item settings.
     * @param name the name of the item
     * @return the registered item
     */
    default Item item(String name) {
        return this.item(name, new Item.Properties());
    }

    /**
     * Registers a block item.
     * @param name the name of the item
     * @param block the block that the block item will place
     * @param settings the item's settings
     * @param factory the factory to instantiate a {@link BlockItem} of type {@code <T>} with the given settings
     * @return the registered block item created by the {@code factory} with {@code settings}
     * @param <T> the type of the block item
     */
    default <T extends BlockItem> T item(String name, Block block, Item.Properties settings, BiFunction<Block, Item.Properties, T> factory) {
        return this.item(name, settings.useBlockDescriptionPrefix(), itemSettings -> factory.apply(block, itemSettings));
    }

    /**
     * Registers an item of type {@link BlockItem}.
     * @param name the name of the item
     * @param block the block that the block item will place
     * @return the registered block item
     */
    default BlockItem item(String name, Block block) {
        return this.item(name, block, new Item.Properties(), BlockItem::new);
    }

    /**
     * Registers an item of type {@link SpawnEggItem}.
     * @param name the name of the item
     * @param entityType the entity type to be spawned by the spawn egg
     * @return the registered spawn egg item
     */
    default SpawnEggItem item(String name, EntityType<?> entityType) {
        return this.item(name, new Item.Properties().spawnEgg(entityType), SpawnEggItem::new);
    }

    /**
     *
     * @param name the name of the block
     * @param block the block to register
     * @return the registered block
     * @param <T> the type of the block
     * @apiNote Prioritize the other {@code block} methods over this one,
     * as they handle passing a required {@link ResourceKey} to the {@link net.minecraft.world.level.block.state.BlockBehaviour.Properties}.
     */
    default <T extends Block> T block(String name, T block) {
        return this.register(BuiltInRegistries.BLOCK, name, block);
    }

    /**
     * @param name the name of the block
     * @param settings the block's settings
     * @param factory the factory to instantiate a {@link Block} of type {@code <T>} with the given settings
     * @return the registered block created by the {@code factory} with {@code settings}
     * @param <T> the type of the block
     */
    default <T extends Block> T block(String name, net.minecraft.world.level.block.state.BlockBehaviour.Properties settings, Function<net.minecraft.world.level.block.state.BlockBehaviour.Properties, T> factory) {
        return this.block(name, factory.apply(settings.setId(this.key(Registries.BLOCK, name))));
    }

    /**
     * Registers a block of type {@link Block}.
     * @param name the name of the block
     * @param settings the block's settings
     * @return the registered block with {@code settings}
     */
    default Block block(String name, net.minecraft.world.level.block.state.BlockBehaviour.Properties settings) {
        return this.block(name, settings, Block::new);
    }

    /**
     * Registers a {@link BlockItem} alongside the block.
     * Use {@link Block#asItem()} to get the instance of the item.
     * @param name the name of the block and item
     * @param block the block to register, and the block that the block item will place
     * @return the registered block
     * @param <T> the type of the block
     * @apiNote Prioritize the other {@code blockWithItem} methods over this one,
     * as they handle passing a required {@link ResourceKey} to the {@link net.minecraft.world.level.block.state.BlockBehaviour.Properties}.
     */
    default <T extends Block> T blockWithItem(String name, T block) {
        return this.registerBlockItem(name, this.block(name, block));
    }

    /**
     * Registers a {@link BlockItem} alongside a block.
     * Use {@link Block#asItem()} to get the instance of the item.
     * @param name the name of the block and item
     * @param settings the block's settings
     * @param factory the factory to instantiate a {@link Block} of type {@code <T>} with the given settings
     * @return the registered block created by the {@code factory} with {@code settings}
     * @param <T> the type of the block
     */
    default <T extends Block> T blockWithItem(String name, net.minecraft.world.level.block.state.BlockBehaviour.Properties settings, Function<net.minecraft.world.level.block.state.BlockBehaviour.Properties, T> factory) {
        return this.registerBlockItem(name, this.block(name, settings, factory));
    }

    /**
     * Registers a {@link BlockItem} alongside a block.
     * Use {@link Block#asItem()} to get the instance of the item.
     * @param name the name of the block and item
     * @param settings the block's settings
     * @return the registered block with {@code settings}
     */
    default Block blockWithItem(String name, net.minecraft.world.level.block.state.BlockBehaviour.Properties settings) {
        return this.registerBlockItem(name, this.block(name, settings));
    }

    /**
     * Registers an entity type given an entity type builder. Use {@link EntityType.Builder#createNothing(MobCategory)},
     * {@link EntityType.Builder#of(EntityType.EntityFactory, MobCategory)},
     * {@link EntityType.Builder#createLiving(EntityType.EntityFactory, MobCategory, UnaryOperator)},
     * or {@link EntityType.Builder#createMob(EntityType.EntityFactory, MobCategory, UnaryOperator)}
     * to create a builder.
     * @param name the name of the entity type
     * @param builder the entity type builder
     * @return the built and registered entity type
     * @param <T> the type of the entity
     */
    default <T extends Entity> EntityType<T> entity(String name, EntityType.Builder<T> builder) {
        return this.register(BuiltInRegistries.ENTITY_TYPE, name, builder.build(this.key(Registries.ENTITY_TYPE, name)));
    }

    /**
     * Registers a block entity type given a block entity type builder.
     * Use {@link FabricBlockEntityTypeBuilder#create(FabricBlockEntityTypeBuilder.Factory, Block...)}
     * to create a builder.
     * @param name the name of the block entity type
     * @param builder the block entity type builder
     * @return the built and registered block entity type
     * @param <T> the type of the block entity
     */
    default <T extends BlockEntity> BlockEntityType<T> blockEntity(String name, FabricBlockEntityTypeBuilder<T> builder) {
        return this.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, name, builder.build());
    }

    /**
     * @param name the name of the sound event
     * @param soundEvent the sound event to register
     * @return the registered sound event
     */
    default SoundEvent sound(String name, SoundEvent soundEvent) {
        return this.register(BuiltInRegistries.SOUND_EVENT, name, soundEvent);
    }

    /**
     * Registers a {@link SoundEvent} without a specified range.
     * @param name the name of the sound event
     * @return the registered sound event
     */
    default SoundEvent sound(String name) {
        return this.sound(name, SoundEvent.createVariableRangeEvent(this.id(name)));
    }

    /**
     * Registers a {@link SoundEvent} wrapped in a {@link Holder.Reference}.
     * @param name the name of the sound event
     * @param soundEvent the sound event to register
     * @return the registered sound event
     */
    default Holder.Reference<SoundEvent> soundReference(String name, SoundEvent soundEvent) {
        return this.registerReference(BuiltInRegistries.SOUND_EVENT, name, soundEvent);
    }

    /**
     * Registers a {@link SoundEvent} wrapped in a {@link Holder.Reference} without a specified range.
     * @param name the name of the sound event
     * @return the registered sound event
     */
    default Holder.Reference<SoundEvent> soundReference(String name) {
        return this.soundReference(name, SoundEvent.createVariableRangeEvent(this.id(name)));
    }

    /**
     * Creates a {@link SoundType} with its break, step, place, hit, and fall sounds automatically registered.
     * These individual sounds can be accessed with {@link SoundType#getBreakSound()}, {@link SoundType#getStepSound()}, etc.
     * @param name the base name of the sound events of the block sound group
     * @param volume the volume of the block sound group's sounds
     * @param pitch the pitch of the block sound group's sounds
     * @return the block sound group, containing all registered sound events
     */
    default SoundType blockSoundGroup(String name, float volume, float pitch) {
        return new SoundType(
                volume,
                pitch,
                this.sound("block." + name + ".break"),
                this.sound("block." + name + ".step"),
                this.sound("block." + name + ".place"),
                this.sound("block." + name + ".hit"),
                this.sound("block." + name + ".fall")
        );
    }

    /**
     * Registers a data component type, which is used by items and block entities.
     * @param name the name of the component type
     * @param componentType the component type to register
     * @return the registered component type
     * @param <T> the type of the component type
     */
    default <T> DataComponentType<T> dataComponent(String name, DataComponentType<T> componentType) {
        return this.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, componentType);
    }

    /**
     * Registers a data component type, which is used by items and block entities.
     * Use {@link DataComponentType#builder()} to create a builder.
     * @param name the name of the component type
     * @param builder the component type's builder
     * @return the registered and built component type
     * @param <T> the type of the component type
     */
    default <T> DataComponentType<T> dataComponent(String name, DataComponentType.Builder<T> builder) {
        return this.dataComponent(name, builder.build());
    }

    /**
     * Registers a data component type, which is used by items and block entities.
     * @param name the name of the component type
     * @param builderOperator the operator that configures the component type builder
     * @return the registered and built component type
     * @param <T> the type of the component type
     */
    default <T> DataComponentType<T> dataComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return this.dataComponent(name, builderOperator.apply(DataComponentType.builder()));
    }

    /**
     * Registers a registry entry data component type, which is used by items and block entities,
     * based on the entry codec and packet codec of a {@link RegistryCodecGroup}.
     * @param name the name of the component type
     * @param registryCodecGroup the registry codec group which provides the codec and packet codec for the component type
     * @return the registered and built component type
     * @param <T> the registry entry type of the component type
     */
    default <T> DataComponentType<Holder<T>> dataComponent(String name, RegistryCodecGroup<T> registryCodecGroup) {
        return this.dataComponent(name, builder ->
                builder.persistent(registryCodecGroup.holderCodec()).networkSynchronized(registryCodecGroup.streamCodec()).cacheEncoding()
        );
    }

    /**
     * Registers a game rule
     * @param name the name of the game rule
     * @param gameRule the game rule to register
     * @return the registered game rule
     * @param <T> the type of the game rule
     */
    default <T> GameRule<T> gameRule(String name, GameRule<T> gameRule) {
        return this.register(BuiltInRegistries.GAME_RULE, name, gameRule);
    }

    /**
     * Registers a boolean game rule
     * @param name the name of the game rule
     * @param category the category of the game rule
     * @param defaultValue the default value of the game rule
     * @return the registered game rule
     */
    default GameRule<Boolean> gameRule(String name, GameRuleCategory category, boolean defaultValue) {
        return this.gameRule(name, new GameRule<>(category, GameRuleType.BOOL, BoolArgumentType.bool(), GameRuleTypeVisitor::visitBoolean, Codec.BOOL, bool -> bool ? 1 : 0, defaultValue, FeatureFlagSet.of()));
    }

    /**
     * Registers an integer game rule
     * @param name the name of the game rule
     * @param category the category of the game rule
     * @param defaultValue the default value of the game rule
     * @param minValue the min value of the game rule
     * @return the registered game rule
     */
    default GameRule<Integer> gameRule(String name, GameRuleCategory category, int defaultValue, int minValue) {
        return this.gameRule(name, category, defaultValue, minValue, Integer.MAX_VALUE, FeatureFlagSet.of());
    }

    /**
     * Registers an integer game rule
     * @param name the name of the game rule
     * @param category the category of the game rule
     * @param defaultValue the default value of the game rule
     * @param minValue the min value of the game rule
     * @param maxValue the max value of the game rule
     * @return the registered game rule
     */
    default GameRule<Integer> gameRule(String name, GameRuleCategory category, int defaultValue, int minValue, int maxValue) {
        return this.gameRule(name, category, defaultValue, minValue, maxValue, FeatureFlagSet.of());
    }

    /**
     * Registers an integer game rule
     * @param name the name of the game rule
     * @param category the category of the game rule
     * @param defaultValue the default value of the game rule
     * @param minValue the min value of the game rule
     * @param maxValue the max value of the game rule
     * @param featureSet the feature set of the game rule
     * @return the registered game rule
     */
    default GameRule<Integer> gameRule(String name, GameRuleCategory category, int defaultValue, int minValue, int maxValue, FeatureFlagSet featureSet) {
        return this.gameRule(name, new GameRule<>(category, GameRuleType.INT, IntegerArgumentType.integer(minValue, maxValue), GameRuleTypeVisitor::visitInteger, Codec.intRange(minValue, maxValue), i -> i, defaultValue, featureSet));
    }

    /**
     * Registers an enchantment component type, which is used by enchantments.
     * @param name the name of the component type
     * @param componentType the component type to register
     * @return the registered component type
     * @param <T> the type of the component type
     */
    default <T> DataComponentType<T> enchantmentComponent(String name, DataComponentType<T> componentType) {
        return this.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, name, componentType);
    }

    /**
     * Registers an enchantment component type, which is used by enchantments.
     * Use {@link DataComponentType#builder()} to create a builder.
     * @param name the name of the component type
     * @param builder the component type's builder
     * @return the registered and built component type
     * @param <T> the type of the component type
     */
    default <T> DataComponentType<T> enchantmentComponent(String name, DataComponentType.Builder<T> builder) {
        return this.enchantmentComponent(name, builder.build());
    }

    /**
     * Registers an enchantment component type, which is used by enchantments.
     * @param name the name of the component type
     * @param builderOperator the operator that configures the component type builder
     * @return the registered and built component type
     * @param <T> the type of the component type
     */
    default <T> DataComponentType<T> enchantmentComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return this.enchantmentComponent(name, builderOperator.apply(DataComponentType.builder()));
    }

    /**
     * @param name the name of the particle type
     * @param particleType the particle type to register
     * @return the registered particle type
     * @param <E> the type of the particle type's particle effect
     * @param <T> the type of the particle type
     */
    default <E extends ParticleOptions, T extends ParticleType<E>> T particle(String name, T particleType) {
        return this.register(BuiltInRegistries.PARTICLE_TYPE, name, particleType);
    }

    /**
     * Registers a simple particle type, which has an effect with no parameters.
     * @param name the name of the particle type
     * @param alwaysSpawn {@code true} to always spawn the particle regardless of distance.
     * @return the registered particle type
     */
    default SimpleParticleType particle(String name, boolean alwaysSpawn) {
        return this.particle(name, FabricParticleTypes.simple(alwaysSpawn));
    }

    /**
     * Registers a simple particle type, which has an effect with no parameters.
     * This particle will not spawn depending on distance.
     * @param name the name of the particle type
     * @return the registered particle type
     */
    default SimpleParticleType particle(String name) {
        return this.particle(name, false);
    }

    /**
     * Registers a complex particle type, which typically has an effect with parameters.
     * @param name the name of the particle type
     * @param alwaysSpawn {@code true} to always spawn the particle regardless of distance.
     * @param codec the codec for serialization
     * @param packetCodec the packet codec for network serialization
     * @return the registered particle type
     * @param <T> the type of the particle type's particle effect
     */
    default <T extends ParticleOptions> ParticleType<T> particle(String name, boolean alwaysSpawn, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec) {
        return this.particle(name, FabricParticleTypes.complex(alwaysSpawn, codec, packetCodec));
    }

    /**
     * Registers a complex particle type, which typically has an effect with parameters.
     * This particle will not spawn depending on distance.
     * @param name the name of the particle type
     * @param codec the codec for serialization
     * @param packetCodec the packet codec for network serialization
     * @return the registered particle type
     * @param <T> the type of the particle type's particle effect
     */
    default <T extends ParticleOptions> ParticleType<T> particle(String name, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec) {
        return this.particle(name, false, codec, packetCodec);
    }

    /**
     * Registers a complex particle type, which typically has an effect with parameters.
     * @param name the name of the particle type
     * @param alwaysSpawn {@code true} to always spawn the particle regardless of distance.
     * @param codecGetter a function which, given the particle type, returns the codec for serialization
     * @param packetCodecGetter a function which, given the particle type, returns the packet codec for network serialization
     * @return the registered particle type
     * @param <T> the type of the particle type's particle effect
     */
    default <T extends ParticleOptions> ParticleType<T> particle(String name, boolean alwaysSpawn, Function<ParticleType<T>, MapCodec<T>> codecGetter, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> packetCodecGetter) {
        return this.particle(name, FabricParticleTypes.complex(alwaysSpawn, codecGetter, packetCodecGetter));
    }

    /**
     * Registers a complex particle type, which typically has an effect with parameters.
     * This particle will not spawn depending on distance.
     * @param name the name of the particle type
     * @param codecGetter a function which, given the particle type, returns the codec for serialization
     * @param packetCodecGetter a function which, given the particle type, returns the packet codec for network serialization
     * @return the registered particle type
     * @param <T> the type of the particle type's particle effect
     */
    default <T extends ParticleOptions> ParticleType<T> particle(String name, Function<ParticleType<T>, MapCodec<T>> codecGetter, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> packetCodecGetter) {
        return this.particle(name, false, codecGetter, packetCodecGetter);
    }

    /**
     * @param name the name of the item group
     * @param itemGroup the item group to register
     * @return the registered item group
     */
    default CreativeModeTab itemGroup(String name, CreativeModeTab itemGroup) {
        return this.register(BuiltInRegistries.CREATIVE_MODE_TAB, name, itemGroup);
    }

    /**
     * Use {@link FabricCreativeModeTab#builder()}
     * @param name the name of the item group
     * @param builder the builder of the item group to register
     * @return the registered and built item group
     */
    default CreativeModeTab itemGroup(String name, CreativeModeTab.Builder builder) {
        return this.itemGroup(name, builder.build());
    }

    /**
     * Registers a {@link GameEvent} wrapped in a {@link Holder.Reference}.
     * @param name the name of the game event
     * @param gameEvent the game event to register
     * @return the registered game event
     */
    default Holder.Reference<GameEvent> gameEvent(String name, GameEvent gameEvent) {
        return this.registerReference(BuiltInRegistries.GAME_EVENT, name, gameEvent);
    }

    /**
     * Registers a {@link GameEvent} wrapped in a {@link Holder.Reference}.
     * @param name the name of the game event
     * @param radius the radius of the game event
     * @return the registered game event
     */
    default Holder.Reference<GameEvent> gameEvent(String name, int radius) {
        return this.gameEvent(name, new GameEvent(radius));
    }

    /**
     * Registers a {@link GameEvent} wrapped in a {@link Holder.Reference} with a default radius of 16.
     * @param name the name of the game event
     * @return the registered game event
     */
    default Holder.Reference<GameEvent> gameEvent(String name) {
        return this.gameEvent(name, 16);
    }

    /**
     * @param name the name of the fluid
     * @param fluid the fluid to register
     * @return the registered fluid
     * @param <T> the type of the fluid
     */
    default <T extends Fluid> T fluid(String name, T fluid) {
        return this.register(BuiltInRegistries.FLUID, name, fluid);
    }

    /**
     * Registers a {@link MobEffect} wrapped in a {@link Holder.Reference}.
     * @param name the name of the status effect
     * @param statusEffect the status effect to register
     * @return the registered status effect
     */
    default Holder.Reference<MobEffect> statusEffect(String name, MobEffect statusEffect) {
        return this.registerReference(BuiltInRegistries.MOB_EFFECT, name, statusEffect);
    }

    /**
     * Registers a {@link MobEffect} wrapped in a {@link Holder.Reference}.
     * @param name the name of the status effect
     * @param category the category of the status effect
     * @param color the color of the status effect in RGB format
     * @return the registered status effect
     */
    default Holder.Reference<MobEffect> statusEffect(String name, MobEffectCategory category, int color) {
        return this.statusEffect(name, new MobEffect(category, color));
    }

    /**
     * Registers a {@link MobEffect} wrapped in a {@link Holder.Reference}.
     * @param name the name of the status effect
     * @param category the category of the status effect
     * @param color the color of the status effect in RGB format
     * @param particleEffect the particle spawned by the status effect
     * @return the registered status effect
     */
    default Holder.Reference<MobEffect> statusEffect(String name, MobEffectCategory category, int color, ParticleOptions particleEffect) {
        return this.statusEffect(name, new MobEffect(category, color, particleEffect));
    }

    /**
     * Registers a {@link MobEffect} wrapped in a {@link Holder.Reference}.
     * @param name the name of the status effect
     * @param category the category of the status effect
     * @param color the color of the status effect in RGB format
     * @param statusEffectOperator operator to apply changes to the status effect after registration
     * @return the registered status effect
     */
    default Holder.Reference<MobEffect> statusEffect(String name, MobEffectCategory category, int color, UnaryOperator<MobEffect> statusEffectOperator) {
        return this.statusEffect(name, statusEffectOperator.apply(new MobEffect(category, color)));
    }

    /**
     * Registers a {@link MobEffect} wrapped in a {@link Holder.Reference}.
     * @param name the name of the status effect
     * @param category the category of the status effect
     * @param color the color of the status effect in RGB format
     * @param particleEffect the particle spawned by the status effect
     * @param statusEffectOperator operator to apply changes to the status effect after registration
     * @return the registered status effect
     */
    default Holder.Reference<MobEffect> statusEffect(String name, MobEffectCategory category, int color, ParticleOptions particleEffect, UnaryOperator<MobEffect> statusEffectOperator) {
        return this.statusEffect(name, statusEffectOperator.apply(new MobEffect(category, color, particleEffect)));
    }

    /**
     * Registers a {@link Potion} wrapped in a {@link Holder.Reference}.
     * @param name the name of the potion
     * @param potion the potion to register
     * @return the registered potion
     */
    default Holder.Reference<Potion> potion(String name, Potion potion) {
        return this.registerReference(BuiltInRegistries.POTION, name, potion);
    }

    /**
     * Registers a {@link Potion} wrapped in a {@link Holder.Reference}.
     * @param name the name of the potion
     * @param statusEffectInstances an array of status effect instances that the potion contains
     * @return the registered potion
     * @apiNote This method sets the base name of the potion to {@code name}.
     * Use {@link #potion(String, Potion)} to register "long" and "strong" variants of a potion,
     * since these should use the original potion's base name.
     */
    default Holder.Reference<Potion> potion(String name, MobEffectInstance... statusEffectInstances) {
        return this.potion(name, new Potion(name, statusEffectInstances));
    }

    /**
     * @param name the name of the custom stat
     * @param formatter the formatting of the custom stat
     * @return the registered custom stat
     */
    default Identifier customStat(String name, StatFormatter formatter) {
        Identifier stat = this.register(BuiltInRegistries.CUSTOM_STAT, name, this.id(name));
        Stats.CUSTOM.get(stat, formatter);
        return stat;
    }

    /**
     * Registers a custom stat with default formatting
     * @param name the name of the custom stat
     * @return the registered custom stat
     */
    default Identifier customStat(String name) {
        return this.customStat(name, StatFormatter.DEFAULT);
    }

    /**
     * @param name the name of the stat type
     * @param statType the stat type to register
     * @return the registered stat type
     * @param <T> the type of stat
     */
    default <T> StatType<T> stat(String name, StatType<T> statType) {
        return this.register(BuiltInRegistries.STAT_TYPE, name, statType);
    }

    /**
     * @param name the name of the stat type
     * @param registry the stat type's registry
     * @return the registered stat type
     * @param <T> the type of stat
     */
    default <T> StatType<T> stat(String name, Registry<T> registry) {
        Component text = Component.translatable(Util.makeDescriptionId("stat_type", this.id(name)));
        return this.stat(name, new StatType<>(registry, text));
    }

    /**
     * @param name the name of the rule test type
     * @param type the rule test type to register
     * @return the registered rule test type
     * @param <T> the type of rule test
     */
    default <T extends RuleTest> RuleTestType<T> ruleTest(String name, RuleTestType<T> type) {
        return this.register(BuiltInRegistries.RULE_TEST, name, type);
    }

    /**
     * @param name the name of the rule test type
     * @param codec the codec of the rule test
     * @return the registered rule test type
     * @param <T> the type of the rule test
     */
    default <T extends RuleTest> RuleTestType<T> ruleTest(String name, MapCodec<T> codec) {
        return this.ruleTest(name, () -> codec);
    }

    /**
     * @param name the name of the rule block entity modifier type
     * @param type the rule block entity modifier type to register
     * @return the registered rule block entity modifier type
     * @param <T> the type of the rule block entity modifier
     */
    default <T extends RuleBlockEntityModifier> RuleBlockEntityModifierType<T> ruleBlockEntityModifier(String name, RuleBlockEntityModifierType<T> type) {
        return this.register(BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER, name, type);
    }

    /**
     * @param name the name of the rule block entity modifier type
     * @param codec the codec of the rule block entity modifier
     * @return the registered rule block entity modifier type
     * @param <T> the type of the rule block entity modifier
     */
    default <T extends RuleBlockEntityModifier> RuleBlockEntityModifierType<T> ruleBlockEntityModifier(String name, MapCodec<T> codec) {
        return this.ruleBlockEntityModifier(name, () -> codec);
    }

    /**
     * @param name the name of the pos rule test type
     * @param type the pos rule test type to register
     * @return the registered pos rule test type
     * @param <T> the type of pos rule test
     */
    default <T extends PosRuleTest> PosRuleTestType<T> posRuleTest(String name, PosRuleTestType<T> type) {
        return this.register(BuiltInRegistries.POS_RULE_TEST, name, type);
    }

    /**
     * @param name the name of the pos rule test type
     * @param codec the codec of the pos rule test
     * @return the registered pos rule test type
     * @param <T> the type of the pos rule test
     */
    default <T extends PosRuleTest> PosRuleTestType<T> posRuleTest(String name, MapCodec<T> codec) {
        return this.posRuleTest(name, () -> codec);
    }

    /**
     * @param name the name of the menu type
     * @param type the menu type to register
     * @return the registered menu type
     * @param <T> the type of menu
     */
    default <T extends AbstractContainerMenu> MenuType<T> menu(String name, MenuType<T> type) {
        return this.register(BuiltInRegistries.MENU, name, type);
    }

    /**
     * Registers a {@link MenuType} with {@link FeatureFlags#VANILLA_SET} feature flags.
     * @param name the name of the menu
     * @param supplier the menu's supplier
     * @return the registered menu type
     * @param <T> the type of menu
     */
    default <T extends AbstractContainerMenu> MenuType<T> menu(String name, MenuType.MenuSupplier<T> supplier) {
        return this.menu(name, new MenuType<>(supplier, FeatureFlags.VANILLA_SET));
    }

    /**
     * @param name the name of the recipe type
     * @return the registered recipe type
     * @param <T> the type of recipe
     */
    default <T extends Recipe<?>> RecipeType<T> recipeType(String name) {
        return this.register(BuiltInRegistries.RECIPE_TYPE, name, new RecipeTypeImpl<>(name));
    }

    /**
     * @param name the name of the recipe serializer
     * @param serializer the
     * @return the registered recipe serializer
     * @param <T> the type of recipe
     * @param <S> the type of the recipe serializer
     */
    default <T extends Recipe<?>, S extends RecipeSerializer<T>> S recipeSerializer(String name, S serializer) {
        return this.register(BuiltInRegistries.RECIPE_SERIALIZER, name, serializer);
    }

    /**
     * @param name the name of the entity attribute
     * @param attribute the entity attribute to register
     * @return the registered entity attribute
     */
    default Holder<Attribute> attribute(String name, Attribute attribute) {
        return this.registerReference(BuiltInRegistries.ATTRIBUTE, name, attribute);
    }

    /**
     * @param name the name of the position source type
     * @param type the position source type to register
     * @return the registered position source type
     * @param <S> the type of position source
     * @param <T> the type of the position source type
     */
    default <S extends PositionSource, T extends PositionSourceType<S>> T positionSource(String name, T type) {
        return this.register(BuiltInRegistries.POSITION_SOURCE_TYPE, name, type);
    }

    /**
     * @param name the name of the argument serializer
     * @param clazz the class of the argument type
     * @param serializer the argument serializer to register
     * @return the registered argument serializer
     * @param <A> the type of argument type
     * @param <T> the type of argument type properties
     * @param <S> the type of the argument serializer
     */
    default <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, S extends ArgumentTypeInfo<A, T>> S argumentSerializer(String name, Class<? extends A> clazz, S serializer) {
        ArgumentTypeRegistry.registerArgumentType(this.id(name), clazz, serializer);
        return serializer;
    }

    /**
     * Registers a {@link VillagerType} with namespaced {@code name} as its name.
     * @param name the name of the villager type
     * @return the registered villager type
     */
    default VillagerType villagerType(String name) {
        return this.register(BuiltInRegistries.VILLAGER_TYPE, name, new VillagerType());
    }

    /**
     * @param name the name of the villager profession
     * @param profession the villager profession to register
     * @return the registered villager profession
     */
    default VillagerProfession villagerProfession(String name, VillagerProfession profession) {
        return this.register(BuiltInRegistries.VILLAGER_PROFESSION, name, profession);
    }

    /**
     * @param name the name of the villager profession
     * @param heldJobSite the predicate that returns {@code true} if a point of interest is a held workstation
     * @param acquirableJobSite the predicate that returns {@code true} if a point of interest is an acquirable workstation
     * @param requestedItems the immutable set of items that the villager type can gather
     * @param secondaryPoi the immutable set of secondary job sites
     * @param workSound the sound event played when the villager works, or null
     * @return the registered villager profession
     */
    default VillagerProfession villagerProfession(String name,
                                                  Predicate<Holder<PoiType>> heldJobSite,
                                                  Predicate<Holder<PoiType>> acquirableJobSite,
                                                  ImmutableSet<Item> requestedItems,
                                                  ImmutableSet<Block> secondaryPoi,
                                                  @Nullable SoundEvent workSound,
                                                  Int2ObjectMap<ResourceKey<TradeSet>> tradeSetsByLevel) {
        Identifier id = this.id(name);
        Component text = Component.translatable("entity." + id.getNamespace() + ".villager." + id.getPath());
        return this.villagerProfession(name, new VillagerProfession(text, heldJobSite, acquirableJobSite, requestedItems, secondaryPoi, workSound, tradeSetsByLevel));
    }

    /**
     * @param name the name of the villager profession
     * @param heldWorkstation the registry key of the held workstation point of interest type
     * @param gatherableItems the immutable set of items that the villager type can gather
     * @param secondaryJobSites the immutable set of secondary job sites
     * @param workSound the sound event played when the villager works, or null
     * @return the registered villager profession
     */
    default VillagerProfession villagerProfession(String name,
                                                  ResourceKey<PoiType> heldWorkstation,
                                                  ImmutableSet<Item> gatherableItems,
                                                  ImmutableSet<Block> secondaryJobSites,
                                                  @Nullable SoundEvent workSound,
                                                  Int2ObjectMap<ResourceKey<TradeSet>> tradeSetsByLevel) {
        Predicate<Holder<PoiType>> predicate = entry -> entry.is(heldWorkstation);
        return this.villagerProfession(name, predicate, predicate, gatherableItems, secondaryJobSites, workSound, tradeSetsByLevel);
    }

    /**
     * @param name the name of the villager profession
     * @param heldWorkstation the predicate that returns {@code true} if a point of interest is a held workstation
     * @param acquirableWorkstation the predicate that returns {@code true} if a point of interest is an acquirable workstation
     * @param workSound the sound event played when the villager works, or null
     * @return the registered villager profession
     */
    default VillagerProfession villagerProfession(String name,
                                                  Predicate<Holder<PoiType>> heldWorkstation,
                                                  Predicate<Holder<PoiType>> acquirableWorkstation,
                                                  @Nullable SoundEvent workSound,
                                                  Int2ObjectMap<ResourceKey<TradeSet>> tradeSetsByLevel) {
        return this.villagerProfession(name, heldWorkstation, acquirableWorkstation, ImmutableSet.of(), ImmutableSet.of(), workSound, tradeSetsByLevel);
    }

    /**
     * @param name the name of the villager profession
     * @param heldWorkstation the registry key of the held workstation point of interest type
     * @param workSound the sound event played when the villager works, or null
     * @return the registered villager profession
     */
    default VillagerProfession villagerProfession(String name, ResourceKey<PoiType> heldWorkstation, @Nullable SoundEvent workSound, Int2ObjectMap<ResourceKey<TradeSet>> tradeSetsByLevel) {
        Predicate<Holder<PoiType>> predicate = entry -> entry.is(heldWorkstation);
        return this.villagerProfession(name, predicate, predicate, workSound, tradeSetsByLevel);
    }

    /**
     * @param name the name of the point of interest type
     * @param type the point of interest type to register
     * @return the registry key for the registered point of interest type
     */
    default ResourceKey<PoiType> poi(String name, PoiType type) {
        Holder<PoiType> entry = this.registerReference(BuiltInRegistries.POINT_OF_INTEREST_TYPE, name, type);
        PoiTypes.registerBlockStates(entry, type.matchingStates());
        return this.key(Registries.POINT_OF_INTEREST_TYPE, name);
    }

    /**
     * @param name the name of the point of interest type
     * @param states the set of block states that constitute the point of interest type
     * @param ticketCount the ticket count of the point of interest type
     * @param searchDistance the search distance of the point of interest type
     * @return the registered point of interest type
     */
    default ResourceKey<PoiType> poi(String name, Set<BlockState> states, int ticketCount, int searchDistance) {
        return this.poi(name, new PoiType(states, ticketCount, searchDistance));
    }

    /**
     * @param name the name of the point of interest type
     * @param block the block whose block states constitute the point of interest type
     * @param ticketCount the ticket count of the point of interest type
     * @param searchDistance the search distance of the point of interest type
     * @return the registered point of interest type
     */
    default ResourceKey<PoiType> poi(String name, Block block, int ticketCount, int searchDistance) {
        return this.poi(name, PoiTypes.getBlockStates(block), ticketCount, searchDistance);
    }

    /**
     * Registers a {@link PoiType} with a default ticket count and search distance of 1.
     * @param name the name of the point of interest type
     * @param states the set of block states that constitute the point of interest type
     * @return the registered point of interest type
     */
    default ResourceKey<PoiType> poi(String name, Set<BlockState> states) {
        return this.poi(name, new PoiType(states, 1, 1));
    }

    /**
     * Registers a {@link PoiType} with a default ticket count and search distance of 1.
     * @param name the name of the point of interest type
     * @param block the block whose block states constitute the point of interest type
     * @return the registered point of interest type
     */
    default ResourceKey<PoiType> poi(String name, Block block) {
        return this.poi(name, PoiTypes.getBlockStates(block), 1, 1);
    }

    /**
     * @param name the name of the memory module type
     * @param type the memory module type to register
     * @return the registered memory module type
     * @param <T> the type of the memory module type
     */
    default <T> MemoryModuleType<T> memoryModule(String name, MemoryModuleType<T> type) {
        return this.register(BuiltInRegistries.MEMORY_MODULE_TYPE, name, type);
    }

    /**
     * Registers a non-persistent {@link MemoryModuleType}.
     * @param name the name of the memory module type
     * @return the registered memory module type
     * @param <T> the type of the memory module type
     */
    default <T> MemoryModuleType<T> memoryModule(String name) {
        return this.memoryModule(name, new MemoryModuleType<>(Optional.empty()));
    }

    /**
     * Registers a persistent {@link MemoryModuleType}.
     * @param name the name of the memory module type
     * @param codec the codec of the memory module type's value
     * @return the registered memory module type
     * @param <T> the type of the memory module type
     */
    default <T> MemoryModuleType<T> memoryModule(String name, Codec<T> codec) {
        return this.memoryModule(name, new MemoryModuleType<>(Optional.of(codec)));
    }

    /**
     * @param name the name of the sensor type
     * @param type the sensor type to register
     * @return the registered sensor type
     * @param <T> the type of sensor
     */
    default <T extends Sensor<?>> SensorType<T> sensor(String name, SensorType<T> type) {
        return this.register(BuiltInRegistries.SENSOR_TYPE, name, type);
    }

    /**
     * @param name the name of the sensor type
     * @param supplier the supplier for an instance of the sensor, usually a method reference to the sensor's constructor
     * @return the registered sensor type
     * @param <T> the type of sensor
     */
    default <T extends Sensor<?>> SensorType<T> sensor(String name, Supplier<T> supplier) {
        return this.sensor(name, new SensorType<>(supplier));
    }

    /**
     * @param name the name of the activity
     * @param activity the activity to register
     * @return the registered activity
     */
    default Activity activity(String name, Activity activity) {
        return this.register(BuiltInRegistries.ACTIVITY, name, activity);
    }

    /**
     * Registers an {@link Activity} with {@code name} for its name parameter.
     * @param name the name of the activity
     * @return the registered activity
     */
    default Activity activity(String name) {
        return this.activity(name, new Activity(name));
    }


    /**
     * @param name the name of the loot pool entry
     * @param codec the codec of the loot pool entry
     * @return the registered loot pool entry codec
     * @param <T> the type of loot pool entry
     */
    default <T extends LootPoolEntryContainer> MapCodec<T> lootPoolEntry(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, name, codec);
    }

    /**
     * @param name the name of the loot function
     * @param codec the codec of the loot function
     * @return the registered loot function codec
     * @param <T> the type of loot function
     */
    default <T extends LootItemFunction> MapCodec<T> lootFunction(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, name, codec);
    }

    /**
     * @param name the name of the loot condition
     * @param codec the codec of the loot condition
     * @return the registered loot condition codec
     * @param <T> the type of loot condition
     */
    default <T extends LootItemCondition> MapCodec<T> lootCondition(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.LOOT_CONDITION_TYPE, name, codec);
    }

    /**
     * @param name the name of the loot number provider
     * @param codec the codec of the loot number provider
     * @return the registered loot number provider codec
     * @param <T> the type of loot number provider
     */
    default <T extends NumberProvider> MapCodec<T> lootNumberProvider(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE, name, codec);
    }

    /**
     * @param name the name of the loot NBT provider
     * @param codec the codec of the loot NBT provider
     * @return the registered loot NBT provider codec
     * @param <T> the type of loot NBT provider
     */
    default <T extends NbtProvider> MapCodec<T> lootNbtProvider(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.LOOT_NBT_PROVIDER_TYPE, name, codec);
    }

    /**
     * @param name the name of the loot score provider
     * @param codec the codec of the loot score provider
     * @return the registered loot score provider codec
     */
    default <T extends ScoreboardNameProvider> MapCodec<T> lootScoreProvider(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.LOOT_SCORE_PROVIDER_TYPE, name, codec);
    }

    /**
     * @param name the name of the float provider type
     * @param type the float provider type to register
     * @return the registered float provider type
     * @param <T> the type of float provider
     */
    default <T extends FloatProvider> FloatProviderType<T> floatProvider(String name, FloatProviderType<T> type) {
        return this.register(BuiltInRegistries.FLOAT_PROVIDER_TYPE, name, type);
    }

    /**
     * @param name the name of the float provider type
     * @param codec the codec of the float provider
     * @return the registered float provider type
     * @param <T> the type of float provider
     */
    default <T extends FloatProvider> FloatProviderType<T> floatProvider(String name, MapCodec<T> codec) {
        return this.floatProvider(name, () -> codec);
    }

    /**
     * @param name the name of the int provider type
     * @param type the int provider type to register
     * @return the registered int provider type
     * @param <T> the type of int provider
     */
    default <T extends IntProvider> IntProviderType<T> intProvider(String name, IntProviderType<T> type) {
        return this.register(BuiltInRegistries.INT_PROVIDER_TYPE, name, type);
    }

    /**
     * @param name the name of the int provider type
     * @param codec the codec of the int provider
     * @return the registered int provider type
     * @param <T> the type of int provider
     */
    default <T extends IntProvider> IntProviderType<T> intProvider(String name, MapCodec<T> codec) {
        return this.intProvider(name, () -> codec);
    }

    /**
     * @param name the name of the height provider type
     * @param type the height provider type to register
     * @return the registered height provider type
     * @param <T> the type of height provider
     */
    default <T extends HeightProvider> HeightProviderType<T> heightProvider(String name, HeightProviderType<T> type) {
        return this.register(BuiltInRegistries.HEIGHT_PROVIDER_TYPE, name, type);
    }

    /**
     * @param name the name of the height provider type
     * @param codec the codec of the height provider
     * @return the registered height provider type
     * @param <T> the type of height provider
     */
    default <T extends HeightProvider> HeightProviderType<T> heightProvider(String name, MapCodec<T> codec) {
        return this.heightProvider(name, () -> codec);
    }

    /**
     * @param name the name of the block predicate type
     * @param type the block predicate type to register
     * @return the registered block predicate type
     * @param <T> the type of block predicate
     */
    default <T extends BlockPredicate> BlockPredicateType<T> blockPredicate(String name, BlockPredicateType<T> type) {
        return this.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, name, type);
    }

    /**
     * @param name the name of the block predicate type
     * @param codec the codec of the block predicate
     * @return the registered block predicate type
     * @param <T> the type of block predicate
     */
    default <T extends BlockPredicate> BlockPredicateType<T> blockPredicate(String name, MapCodec<T> codec) {
        return this.blockPredicate(name, () -> codec);
    }

    /**
     * @param name the name of the carver
     * @param carver the carver to register
     * @return the registered carver
     * @param <T> the type of the carver config
     * @param <C> the type of the carver
     */
    default <T extends CarverConfiguration, C extends WorldCarver<T>> C carver(String name, C carver) {
        return this.register(BuiltInRegistries.CARVER, name, carver);
    }

    /**
     * @param name the name of the feature
     * @param feature the feature to register
     * @return the registered feature
     * @param <T> the type of the feature config
     * @param <F> the type of the feature
     */
    default <T extends FeatureConfiguration, F extends Feature<T>> F feature(String name, F feature) {
        return this.register(BuiltInRegistries.FEATURE, name, feature);
    }

    /**
     * @param name the name of the structure placement type
     * @param type the structure placement type to register
     * @return the registered structure placement type
     * @param <T> the type of structure placement
     */
    default <T extends StructurePlacement> StructurePlacementType<T> structurePlacement(String name, StructurePlacementType<T> type) {
        return this.register(BuiltInRegistries.STRUCTURE_PLACEMENT, name, type);
    }

    /**
     * @param name the name of the structure placement type
     * @param codec the codec of the structure placement
     * @return the registered structure placement type
     * @param <T> the type of structure placement
     */
    default <T extends StructurePlacement> StructurePlacementType<T> structurePlacement(String name, MapCodec<T> codec) {
        return this.structurePlacement(name, () -> codec);
    }

    /**
     * @param name the name of the structure piece type
     * @param type the structure piece type to register
     * @return the registered structure piece type
     * @param <T> the type of the structure piece type
     * @see #simpleStructurePiece(String, StructurePieceType.ContextlessType)
     * @see #managerAwareStructurePiece(String, StructurePieceType.StructureTemplateType)
     */
    default <T extends StructurePieceType> T structurePiece(String name, T type) {
        return this.register(BuiltInRegistries.STRUCTURE_PIECE, name, type);
    }

    /**
     * @param name the name of the structure piece type
     * @param type the simple structure piece type to register
     * @return the registered structure piece type
     * @param <T> the type of the simple structure piece type
     */
    default <T extends StructurePieceType.ContextlessType> T simpleStructurePiece(String name, T type) {
        return this.register(BuiltInRegistries.STRUCTURE_PIECE, name, type);
    }

    /**
     * @param name the name of the structure piece type
     * @param type the manager aware structure piece type to register
     * @return the registered structure piece type
     * @param <T> the type of the manager aware structure piece type
     */
    default <T extends StructurePieceType.StructureTemplateType> T managerAwareStructurePiece(String name, T type) {
        return this.register(BuiltInRegistries.STRUCTURE_PIECE, name, type);
    }

    /**
     * @param name the name of the structure type
     * @param type the structure type to register
     * @return the registered structure type
     * @param <T> the type of structure
     */
    default <T extends Structure> StructureType<T> structure(String name, StructureType<T> type) {
        return this.register(BuiltInRegistries.STRUCTURE_TYPE, name, type);
    }

    /**
     * @param name the name of the structure type
     * @param codec the codec of the structure
     * @return the registered structure type
     * @param <T> the type of structure
     */
    default <T extends Structure> StructureType<T> structure(String name, MapCodec<T> codec) {
        return this.structure(name, () -> codec);
    }

    /**
     * @param name the name of the placement modifier type
     * @param type the placement modifier type to register
     * @return the registered placement modifier type
     * @param <T> the type of placement modifier
     */
    default <T extends PlacementModifier> PlacementModifierType<T> placementModifier(String name, PlacementModifierType<T> type) {
        return this.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, name, type);
    }

    /**
     * @param name the name of the placement modifier type
     * @param codec the codec of the placemenet modifier
     * @return the registered placement modifier type
     * @param <T> the type of placement modifier
     */
    default <T extends PlacementModifier> PlacementModifierType<T> placementModifier(String name, MapCodec<T> codec) {
        return this.placementModifier(name, () -> codec);
    }

    /**
     * @param name the name of the block state provider type
     * @param type the block state provider type to register
     * @return the registered block state provider type
     * @param <T> the type of block state provider
     */
    default <T extends BlockStateProvider> BlockStateProviderType<T> blockStateProvider(String name, BlockStateProviderType<T> type) {
        return this.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, name, type);
    }

    /**
     * @param name the name of the block state provider type
     * @param codec the codec of the block state provider
     * @return the registered block state provider type
     * @param <T> the type of block state provider
     */
    default <T extends BlockStateProvider> BlockStateProviderType<T> blockStateProvider(String name, MapCodec<T> codec) {
        return this.blockStateProvider(name, new BlockStateProviderType<>(codec));
    }

    /**
     * @param name the name of the foliage placer type
     * @param type the foliage placer type to register
     * @return the registered foliage placer type
     * @param <T> the type of foliage placer
     */
    default <T extends FoliagePlacer> FoliagePlacerType<T> foliagePlacer(String name, FoliagePlacerType<T> type) {
        return this.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, name, type);
    }

    /**
     * @param name the name of the foliage placer type
     * @param codec the codec of the foliage placer
     * @return the registered foliage placer type
     * @param <T> the type of foliage placer
     */
    default <T extends FoliagePlacer> FoliagePlacerType<T> foliagePlacer(String name, MapCodec<T> codec) {
        return this.foliagePlacer(name, new FoliagePlacerType<>(codec));
    }

    /**
     * @param name the name of the trunk placer type
     * @param type the trunk placer
     * @return the registered trunk placer type
     * @param <T> the type of trunk placer
     */
    default <T extends TrunkPlacer> TrunkPlacerType<T> trunkPlacer(String name, TrunkPlacerType<T> type) {
        return this.register(BuiltInRegistries.TRUNK_PLACER_TYPE, name, type);
    }

    /**
     * @param name the name of the trunk placer type
     * @param codec the codec of the trunk placer
     * @return the registered trunk placer type
     * @param <T> the type of trunk placer
     */
    default <T extends TrunkPlacer> TrunkPlacerType<T> trunkPlacer(String name, MapCodec<T> codec) {
        return this.trunkPlacer(name, new TrunkPlacerType<>(codec));
    }

    /**
     * @param name the name of the root placer type
     * @param type the root placer type to register
     * @return the registered root placer type
     * @param <T> the type of root placer
     */
    default <T extends RootPlacer> RootPlacerType<T> rootPlacer(String name, RootPlacerType<T> type) {
        return this.register(BuiltInRegistries.ROOT_PLACER_TYPE, name, type);
    }

    /**
     * @param name the name of the root placer type
     * @param codec the codec of the root placer
     * @return the registered root placer type
     * @param <T> the type of root placer
     */
    default <T extends RootPlacer> RootPlacerType<T> rootPlacer(String name, MapCodec<T> codec) {
        return this.rootPlacer(name, new RootPlacerType<>(codec));
    }

    /**
     * @param name the name of the tree decorator type
     * @param type the tree decorator type to register
     * @return the registered tree decorator type
     * @param <T> the type of tree decorator
     */
    default <T extends TreeDecorator> TreeDecoratorType<T> treeDecorator(String name, TreeDecoratorType<T> type) {
        return this.register(BuiltInRegistries.TREE_DECORATOR_TYPE, name, type);
    }

    /**
     * @param name the name of the tree decorator type
     * @param codec the codec of the tree decorator
     * @return the registered tree decorator type
     * @param <T> the type of tree decorator
     */
    default <T extends TreeDecorator> TreeDecoratorType<T> treeDecorator(String name, MapCodec<T> codec) {
        return this.treeDecorator(name, new TreeDecoratorType<>(codec));
    }

    /**
     * @param name the name of the feature size type
     * @param type the feature size type to register
     * @return the registered feature size type
     * @param <T> the type of feature size
     */
    default <T extends FeatureSize> FeatureSizeType<T> featureSize(String name, FeatureSizeType<T> type) {
        return this.register(BuiltInRegistries.FEATURE_SIZE_TYPE, name, type);
    }

    /**
     * @param name the name of the feature size type
     * @param codec the codec of the feature size
     * @return the registered feature size type
     * @param <T> the type of feature size
     */
    default <T extends FeatureSize> FeatureSizeType<T> featureSize(String name, MapCodec<T> codec) {
        return this.featureSize(name, new FeatureSizeType<>(codec));
    }

    /**
     * @param name the name of the biome source
     * @param codec the codec of the biome source
     * @return the registered biome source codec
     * @param <T> the type of biome source
     */
    default <T extends BiomeSource> MapCodec<T> biomeSource(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.BIOME_SOURCE, name, codec);
    }

    /**
     * @param name the name of the chunk generator
     * @param codec the codec of the chunk generator
     * @return the registered chunk generator codec
     * @param <T> the type of chunk generator
     */
    default <T extends ChunkGenerator> MapCodec<T> chunkGenerator(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.CHUNK_GENERATOR, name, codec);
    }

    /**
     * @param name the name of the material condition
     * @param codec the codec of the material condition
     * @return the registered material condition codec
     * @param <T> the type of material condition
     */
    default <T extends SurfaceRules.ConditionSource> MapCodec<T> materialCondition(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.MATERIAL_CONDITION, name, codec);
    }

    /**
     * @param name the name of the material rule
     * @param codec the codec of the material rule
     * @return the registered material rule codec
     * @param <T> the type of material rule
     */
    default <T extends SurfaceRules.RuleSource> MapCodec<T> materialRule(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.MATERIAL_RULE, name, codec);
    }

    /**
     * @param name the name of the density function
     * @param codec the codec of the density function
     * @return the registered density function codec
     * @param <T> the type of density function
     */
    default <T extends DensityFunction> MapCodec<T> densityFunction(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, name, codec);
    }

    /**
     * @param name the name of the block type
     * @param codec the codec of the block type
     * @return the registered block type codec
     * @param <T> the type of block type
     */
    default <T extends Block> MapCodec<T> blockType(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.BLOCK_TYPE, name, codec);
    }

    /**
     * @param name the name of the structure processor type
     * @param type the structure processor type to register
     * @return the registered structure processor type
     * @param <T> the type of structure processor
     */
    default <T extends StructureProcessor> StructureProcessorType<T> structureProcessor(String name, StructureProcessorType<T> type) {
        return this.register(BuiltInRegistries.STRUCTURE_PROCESSOR, name, type);
    }

    /**
     * @param name the name of the structure processor type
     * @param codec the codec of the structure processor
     * @return the registered structure processor type
     * @param <T> the type of structure processor
     */
    default <T extends StructureProcessor> StructureProcessorType<T> structureProcessor(String name, MapCodec<T> codec) {
        return this.structureProcessor(name, () -> codec);
    }

    /**
     * @param name the name of the structure pool element type
     * @param type the structure pool element type to register
     * @return the registered structure pool element type
     * @param <T> the type of structure pool element
     */
    default <T extends StructurePoolElement> StructurePoolElementType<T> structurePoolElement(String name, StructurePoolElementType<T> type) {
        return this.register(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, name, type);
    }

    /**
     * @param name the name of the structure pool element type
     * @param codec the codec of the structure pool element
     * @return the registered structure pool element type
     * @param <T> the type of structure pool element
     */
    default <T extends StructurePoolElement> StructurePoolElementType<T> structurePoolElement(String name, MapCodec<T> codec) {
        return this.structurePoolElement(name, () -> codec);
    }

    /**
     * @param name the name of the pool alias binding
     * @param codec the codec of the pool alias binding
     * @return the registered pool alias binding codec
     * @param <T> the type of pool alias binding
     */
    default <T extends PoolAliasBinding> MapCodec<T> poolAliasBinding(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.POOL_ALIAS_BINDING_TYPE, name, codec);
    }

    /**
     * @param name the name of the decorated pot pattern
     * @param pattern the decorated pot pattern to register
     * @return the registered decorated pot pattern
     */
    default DecoratedPotPattern decoratedPotPattern(String name, DecoratedPotPattern pattern) {
        return this.register(BuiltInRegistries.DECORATED_POT_PATTERN, name, pattern);
    }

    /**
     * Registers a {@link DecoratedPotPattern} using {@code name} as the pattern's asset id.
     * @param name the name of the decorated pot pattern
     * @return the registered decorated pot pattern
     */
    default DecoratedPotPattern decoratedPotPattern(String name) {
        return this.decoratedPotPattern(name, new DecoratedPotPattern(this.id(name)));
    }

    /**
     * @param name the name of the criterion
     * @param criterion the criterion to register
     * @return the registered criterion
     * @param <T> the type of the criterion
     */
    default <T extends CriterionTrigger<?>> T criterion(String name, T criterion) {
        return this.register(BuiltInRegistries.TRIGGER_TYPES, name, criterion);
    }

    /**
     * @param name the name of the number format type
     * @param type the number format type to register
     * @return the registered number format type
     * @param <T> the type of number format
     */
    default <T extends NumberFormat> NumberFormatType<T> numberFormat(String name, NumberFormatType<T> type) {
        return this.register(BuiltInRegistries.NUMBER_FORMAT_TYPE, name, type);
    }

    /**
     * @param name the name of the entity sub-predicate
     * @param codec the codec of the entity sub-predicate
     * @return the registered entity sub-predicate codec
     * @param <T> the type of entity sub-predicate
     */
    default <T extends EntitySubPredicate> MapCodec<T> entitySubPredicate(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, name, codec);
    }

    /**
     * @param name the name of the data component predicate
     * @param codec the codec of the data component predicate
     * @return the registered data component predicate type
     * @param <T> the type of data component predicate
     */
    default <T extends DataComponentPredicate> DataComponentPredicate.Type<T> dataComponentPredicate(String name, Codec<T> codec) {
        return this.register(BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE, name, new DataComponentPredicate.ConcreteType<>(codec));
    }

    /**
     * Registers a {@link MapDecorationType} wrapped in a {@link Holder.Reference}.
     * @param name the name of the map decoration type
     * @param type the map decoration type to register
     * @return the registered map decoration type
     */
    default Holder.Reference<MapDecorationType> mapDecoration(String name, MapDecorationType type) {
        return this.registerReference(BuiltInRegistries.MAP_DECORATION_TYPE, name, type);
    }

    /**
     * Registers a {@link MapDecorationType} wrapped in a {@link Holder.Reference}.
     * {@code name} is used as the map decoration type's asset id.
     * @param name the name of the map decoration type
     * @param showOnItemFrame {@code true} for showing the map decoration on item frames
     * @param mapColor the color of the map decoration in RGB format
     * @param explorationMapElement {@code true} disallows the map from being expanded in a cartography table
     * @param trackCount {@code true} for tracking the amount of this map decoration on a map
     * @return the registered map decoration type
     */
    default Holder.Reference<MapDecorationType> mapDecoration(String name, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount) {
        return this.mapDecoration(name, new MapDecorationType(this.id(name), showOnItemFrame, mapColor, explorationMapElement, trackCount));
    }

    /**
     * Registers a {@link MapDecorationType} wrapped in a {@link Holder.Reference}.
     * {@code mapColor} is defaulted to white and {@code explorationMapElement} is defaulted to {@code false}.
     * @param name the name of the map decoration type
     * @param showOnItemFrame {@code true} for showing the map decoration on item frames
     * @param trackCount {@code true} for tracking the amount of this map decoration on a map
     * @return the registered map decoration type
     */
    default Holder.Reference<MapDecorationType> mapDecoration(String name, boolean showOnItemFrame, boolean trackCount) {
        return this.mapDecoration(name, showOnItemFrame, -1, false, trackCount);
    }

    /**
     * @param name the name of the enchantment level based value
     * @param codec the codec of the enchantment level based value
     * @return the registered enchantment level based value codec
     * @param <T> the type of enchantment level based value
     */
    default <T extends LevelBasedValue> MapCodec<T> enchantmentLevelBasedValue(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.ENCHANTMENT_LEVEL_BASED_VALUE_TYPE, name, codec);
    }

    /**
     * @param name the name of the enchantment entity effect
     * @param codec the codec of the enchantment entity effect
     * @return the registered enchantment entity effect codec
     * @param <T> the type of enchantment entity effect
     */
    default <T extends EnchantmentEntityEffect> MapCodec<T> enchantmentEntityEffect(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, name, codec);
    }

    /**
     * @param name the name of the enchantment location based effect
     * @param codec the codec of the enchantment location based effect
     * @return the registered enchantment location based effect codec
     * @param <T> the type of enchantment location based effect
     */
    default <T extends EnchantmentLocationBasedEffect> MapCodec<T> enchantmentLocationBasedEffect(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, name, codec);
    }

    /**
     * @param name the name of the enchantment value effect
     * @param codec the codec of the enchantment value effect
     * @return the registered enchantment value effect codec
     * @param <T> the type of enchantment value effect
     */
    default <T extends EnchantmentValueEffect> MapCodec<T> enchantmentValueEffect(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.ENCHANTMENT_VALUE_EFFECT_TYPE, name, codec);
    }

    /**
     * @param name the name of the enchantment provider
     * @param codec the codec of the enchantment provider
     * @return the registered enchantment provider codec
     * @param <T> the type of enchantment provider
     */
    default <T extends EnchantmentProvider> MapCodec<T> enchantmentProvider(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.ENCHANTMENT_PROVIDER_TYPE, name, codec);
    }

    /**
     * @param name the name of the consume effect type
     * @param codec the codec of the consume effect for serialization
     * @param packetCodec the packet codec of the consume effect for network serialization
     * @return the registered consume effect type
     * @param <T> the type of consume effect
     */
    default <T extends ConsumeEffect> ConsumeEffect.Type<T> consumeEffect(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        return this.register(BuiltInRegistries.CONSUME_EFFECT_TYPE, name, new ConsumeEffect.Type<>(codec, packetCodec));
    }

    /**
     * @param name the name of the recipe display serializer
     * @param codec the codec of the recipe display for serialization
     * @param packetCodec the packet codec of the recipe display for network serialization
     * @return the registered recipe display serializer
     * @param <T> the type of recipe display
     */
    default <T extends RecipeDisplay> RecipeDisplay.Type<T> recipeDisplay(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        return this.register(BuiltInRegistries.RECIPE_DISPLAY, name, new RecipeDisplay.Type<>(codec, packetCodec));
    }

    /**
     * @param name the name of the slot display serializer
     * @param codec the codec of the slot display for serialization
     * @param packetCodec the packet codec of the slot display for network serialization
     * @return the registered slot display serializer
     * @param <T> the type of slot display
     */
    default <T extends SlotDisplay> SlotDisplay.Type<T> slotDisplay(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        return this.register(BuiltInRegistries.SLOT_DISPLAY, name, new SlotDisplay.Type<>(codec, packetCodec));
    }

    /**
     * @param name the name of the recipe book category
     * @param category the recipe book category to register
     * @return the registered recipe book category
     */
    default RecipeBookCategory recipeBookCategory(String name, RecipeBookCategory category) {
        return this.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, name, category);
    }

    /**
     * @param name the name of the recipe book category
     * @return the registered recipe book category
     */
    default RecipeBookCategory recipeBookCategory(String name) {
        return this.recipeBookCategory(name, new RecipeBookCategory());
    }

    /**
     * @param name the name of the ticket type
     * @param expiryTicks the duration of chunk tickets of this type
     * @param flags packed int flags of the ticket type, such as {@link TicketType#FLAG_PERSIST},
     *              {@link TicketType#FLAG_LOADING}, {@link TicketType#FLAG_SIMULATION},
     *              {@link TicketType#FLAG_KEEP_DIMENSION_ACTIVE}, and {@link TicketType#FLAG_CAN_EXPIRE_IF_UNLOADED}
     * @return the registered ticket type
     */
    default TicketType ticketType(String name, long expiryTicks, int flags) {
        return this.register(BuiltInRegistries.TICKET_TYPE, name, new TicketType(expiryTicks, flags));
    }

    /**
     * @param name the name of the test environment definition
     * @param codec the codec of the test environment definition
     * @return the registered test environment definition codec
     * @param <T> the type of test environment definition
     */
    default <T extends TestEnvironmentDefinition<?>> MapCodec<T> testEnvironmentDefinition(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.TEST_ENVIRONMENT_DEFINITION_TYPE, name, codec);
    }

    /**
     * @param name the name of the test instance
     * @param codec the codec of the test instance
     * @return the registered test instance codec
     * @param <T> the type of test instance
     */
    default <T extends GameTestInstance> MapCodec<T> testInstance(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.TEST_INSTANCE_TYPE, name, codec);
    }

    /**
     * @param name the name of the test function
     * @param testFunction the test function to register
     * @return the registered test function
     */
    default Consumer<GameTestHelper> testFunction(String name, Consumer<GameTestHelper> testFunction) {
        return this.register(BuiltInRegistries.TEST_FUNCTION, name, testFunction);
    }

    /**
     * @param name the name of the spawn condition
     * @param codec the codec of the spawn condition
     * @return the registered spawn condition codec
     * @param <T> the type of spawn condition
     */
    default <T extends SpawnCondition> MapCodec<T> spawnCondition(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.SPAWN_CONDITION_TYPE, name, codec);
    }

    /**
     * @param name the name of the dialog
     * @param codec the codec of the dialog
     * @return the registered dialog codec
     * @param <T> the type of dialog
     */
    default <T extends Dialog> MapCodec<T> dialog(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.DIALOG_TYPE, name, codec);
    }

    /**
     * @param name the name of the dialog body
     * @param codec the codec of the dialog body
     * @return the registered dialog body codec
     * @param <T> the type of dialog body
     */
    default <T extends DialogBody> MapCodec<T> dialogBody(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.DIALOG_BODY_TYPE, name, codec);
    }

    /**
     * @param name the name of the dialog action
     * @param codec the codec of the dialog action
     * @return the registered dialog action codec
     * @param <T> the type of dialog action
     */
    default <T extends Action> MapCodec<T> dialogAction(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.DIALOG_ACTION_TYPE, name, codec);
    }

    /**
     * @param name the name of the input control
     * @param codec the codec of the input control
     * @return the registered input control codec
     * @param <T> the type of input control
     */
    default <T extends InputControl> MapCodec<T> inputControl(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.INPUT_CONTROL_TYPE, name, codec);
    }

    /**
     * @param name the name of the debug subscription
     * @param type the debug subscription type to register
     * @return the registered debug subscription
     * @param <T> the type of debug subscription
     */
    default <T> DebugSubscription<T> debugSubscription(String name, DebugSubscription<T> type) {
        return this.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, name, type);
    }

    /**
     * @param name the name of the debug subscription
     * @return the registered debug subscription
     * @param <T> the type of debug subscription
     */
    default <T> DebugSubscription<T> debugSubscription(String name) {
        return this.debugSubscription(name, new DebugSubscription<>(null));
    }

    /**
     * @param name the name of the debug subscription
     * @param packetCodec the packet codec of the debug subscription
     * @return the registered debug subscription
     * @param <T> the type of debug subscription
     */
    default <T> DebugSubscription<T> debugSubscription(String name, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec) {
        return this.debugSubscription(name, new DebugSubscription<>(packetCodec));
    }

    /**
     * @param name the name of the debug subscription
     * @param packetCodec the packet codec of the debug subscription
     * @param expiry the expiry of the debug subscription
     * @return the registered debug subscription
     * @param <T> the type of debug subscription
     */
    default <T> DebugSubscription<T> debugSubscription(String name, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec, int expiry) {
        return this.debugSubscription(name, new DebugSubscription<>(packetCodec, expiry));
    }

    /**
     * @param name the name of the incoming rpc method
     * @param builder the builder for the incoming rpc method
     * @return the registered incoming rpc method
     */
    default <Params, Result> IncomingRpcMethod<Params, Result> incomingRpcMethod(String name, IncomingRpcMethod.IncomingRpcMethodBuilder<Params, Result> builder) {
        return this.register(BuiltInRegistries.INCOMING_RPC_METHOD, name, builder.build());
    }

    /**
     * @param name the name of the outgoing rpc method
     * @param builder the builder for the outgoing rpc method
     * @return the registered outgoing rpc method
     */
    default <Params, Result> OutgoingRpcMethod<Params, Result> outgoingRpcMethod(String name, OutgoingRpcMethod.OutgoingRpcMethodBuilder<Params, Result> builder) {
        return this.register(BuiltInRegistries.OUTGOING_RPC_METHOD, name, builder.build());
    }

    /**
     * @param name the name of the permission
     * @param codec the codec of the permission
     * @return the registered permission codec
     * @param <T> the type of permission
     */
    default <T extends Permission> MapCodec<T> permission(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.PERMISSION_TYPE, name, codec);
    }

    /**
     * @param name the name of the permission check
     * @param codec the codec of the permission check
     * @return the registered permission check codec
     * @param <T> the type of permission check
     */
    default <T extends PermissionCheck> MapCodec<T> permissionCheck(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.PERMISSION_CHECK_TYPE, name, codec);
    }

    /**
     * @param name the name of the environment attribute
     * @param builder the builder of the environment attribute
     * @return the registered environment attribute
     * @param <T> the type of the environment attribute
     */
    default <T> EnvironmentAttribute<T> environmentAttribute(String name, EnvironmentAttribute.Builder<T> builder) {
        return this.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, name, builder.build());
    }

    /**
     * @param name the name of the environment attribute type
     * @param type the environment attribute type to register
     * @return the registered environment attribute type
     * @param <T> the type of the environment attribute type
     */
    default <T> AttributeType<T> environmentAttributeType(String name, AttributeType<T> type) {
        return this.register(BuiltInRegistries.ATTRIBUTE_TYPE, name, type);
    }

    /**
     * @param name the name of the slot source
     * @param codec the codec of the slot source
     * @return the registered slot source codec
     * @param <T> the type of slot source
     */
    default <T extends SlotSource> MapCodec<T> slotSource(String name, MapCodec<T> codec) {
        return this.register(BuiltInRegistries.SLOT_SOURCE_TYPE, name, codec);
    }

    /**
     * @param name the name of the entity data serializer
     * @param trackedDataHandler the entity data serializer to register
     * @return the registered entity data serializer
     * @param <T> the type of entity data
     */
    default <T> EntityDataSerializer<T> entityData(String name, EntityDataSerializer<T> trackedDataHandler) {
        FabricEntityDataRegistry.register(this.id(name), trackedDataHandler);
        return trackedDataHandler;
    }

    /**
     * @param name the name of the entity data serializer
     * @param codec the packet codec of the entity data serializer
     * @return the registered entity data serializer
     * @param <T> the type of entity data
     */
    default <T> EntityDataSerializer<T> entityData(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
        return this.entityData(name, EntityDataSerializer.forValueType(codec));
    }

    /**
     * @param name the name of the text content
     * @param codec the codec of the text content
     * @return the registered text content codec
     * @param <T> the type of text content
     */
    default <T extends ComponentContents> MapCodec<T> textContent(String name, MapCodec<T> codec) {
        TextUtil.registerTextContent(this.id(name), codec);
        return codec;
    }

    /**
     * @param name the name of the text object contents
     * @param codec the codec of the text object contents
     * @return the registered text object contents codec
     * @param <T> the type of text object contents
     */
    default <T extends ObjectInfo> MapCodec<T> textObjectContents(String name, MapCodec<T> codec) {
        TextUtil.registerTextObjectContents(this.id(name), codec);
        return codec;
    }

    /**
     * @param name the name of the nbt data source
     * @param codec the codec of the nbt data source
     * @return the registered nbt data source codec
     * @param <T> the type of nbt data source
     */
    default <T extends DataSource> MapCodec<T> nbtDataSource(String name, MapCodec<T> codec) {
        TextUtilImpl.registerNbtDataSource(this.id(name), codec);
        return codec;
    }

    /**
     * @param name the name of the block set type
     * @param builder the builder for the block set type
     * @return the registered block set type
     */
    default BlockSetType blockSetType(String name, BlockSetTypeBuilder builder) {
        return builder.register(this.id(name));
    }

    /**
     * @param name the name of the wood type
     * @param builder the builder for the wood type
     * @param blockSetType the block set type for the wood type
     * @return the registered wood type
     */
    default WoodType woodType(String name, WoodTypeBuilder builder, BlockSetType blockSetType) {
        return builder.register(this.id(name), blockSetType);
    }

    /**
     * @param name the name of the custom ingredient serializer
     * @param codec the codec of the custom ingredient
     * @param streamCodec the stream codec of the custom ingredient
     * @return the registered custom ingredient serializer
     * @param <T> the type of custom ingredient
     */
    default <T extends CustomIngredient> CustomIngredientSerializer<T> customIngredient(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        CustomIngredientSerializer<T> serializer = new CustomIngredientSerializerImpl<>(this.id(name), codec, streamCodec);
        CustomIngredientSerializer.register(serializer);
        return serializer;
    }

    private <T extends Block> T registerBlockItem(String name, T block) {
        Item.BY_BLOCK.put(block, this.item(name, block));
        return block;
    }
}
