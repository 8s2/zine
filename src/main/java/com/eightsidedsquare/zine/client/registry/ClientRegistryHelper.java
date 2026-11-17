package com.eightsidedsquare.zine.client.registry;

import com.eightsidedsquare.zine.client.atlas.gradient.Gradient;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.UnbakedModelDeserializer;
import net.fabricmc.fabric.api.client.rendering.v1.SpriteSourceRegistry;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.core.registries.BuiltInClientRegistries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

public interface ClientRegistryHelper {
    /**
     * Creates an instance of the default implementation of {@link ClientRegistryHelper}.
     */
    static ClientRegistryHelper create(String namespace) {
        return new ClientRegistryHelperImpl(namespace);
    }

    /**
     * @param name the Identifier's path
     * @return an {@link Identifier} with the {@link ClientRegistryHelper}'s namespace
     */
    Identifier id(String name);

    /**
     * Registers a value to a given registry callback
     * @param name the path of the registered value's identifier
     * @param value the value to register
     * @param registryCallback the registry callback, usually a method reference that accepts an identifier and value
     * @return the registered value
     */
    default <T> T register(String name, T value, BiConsumer<Identifier, T> registryCallback) {
        registryCallback.accept(this.id(name), value);
        return value;
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
     * @param name the name of the sprite source
     * @param codec the codec of the sprite source
     * @return the registered sprite source codec
     */
    default <T extends SpriteSource> MapCodec<T> spriteSource(String name, MapCodec<T> codec) {
        return this.register(name, codec, SpriteSourceRegistry::register);
    }

    /**
     * @param name the name of the block state model
     * @param codec the codec of the block state model
     * @return the registered block state model codec
     */
    default <T extends CustomUnbakedBlockStateModel> MapCodec<T> blockStateModel(String name, MapCodec<T> codec) {
        return this.register(name, codec, CustomUnbakedBlockStateModel::register);
    }

    /**
     * @param name the name of the conditional item model property
     * @param codec the codec of the conditional item model property
     * @return the registered conditional item model property codec
     */
    default <T extends ConditionalItemModelProperty> MapCodec<T> conditionalItemModelProperty(String name, MapCodec<T> codec) {
        return this.register(BuiltInClientRegistries.CONDITIONAL_ITEM_MODEL_PROPERTY_TYPE, name, codec);
    }

    /**
     * @param name the name of the debug screen entry
     * @param debugScreenEntry the debug screen entry
     * @return the registered debug screen entry
     * @param <T> the type of the debug screen entry
     */
    default <T extends DebugScreenEntry> T debugScreenEntry(String name, T debugScreenEntry) {
        return this.register(BuiltInClientRegistries.DEBUG_SCREEN_ENTRY, name, debugScreenEntry);
    }

    /**
     * @param name the name of the extra model key
     * @return an {@link ExtraModelKey} with the {@link ClientRegistryHelper}'s namespace
     * @param <T> type of the extra model
     */
    default <T> ExtraModelKey<T> extraModelKey(String name) {
        return ExtraModelKey.create(() -> this.id(name).toString());
    }

    /**
     * @param name the name of the gradient
     * @param codec the codec of the gradient
     * @return the registered gradient codec
     */
    default <T extends Gradient> MapCodec<T> gradient(String name, MapCodec<T> codec) {
        return this.register(name, codec, Gradient.ID_MAPPER::put);
    }

    /**
     * @param name the name of the unbaked item model
     * @param codec the codec of the unbaked item model
     * @return the registered unbaked item model codec
     */
    default <T extends ItemModel.Unbaked> MapCodec<T> itemModel(String name, MapCodec<T> codec) {
        return this.register(BuiltInClientRegistries.ITEM_MODEL_TYPE, name, codec);
    }

    /**
     * @param name the name of the entity model layer
     * @param type the sub-name or type of the entity model layer
     * @return the instantiated entity model layer
     */
    default ModelLayerLocation modelLayer(String name, String type) {
        return new ModelLayerLocation(this.id(name), type);
    }

    /**
     * @param name the name of the entity model layer
     * @return the instantiated entity model layer
     */
    default ModelLayerLocation modelLayer(String name) {
        return this.modelLayer(name, "main");
    }

    /**
     * @param name the name of the range select property
     * @param codec the codec of the range select property
     * @return the registered range select property codec
     */
    default <T extends RangeSelectItemModelProperty> MapCodec<T> rangeSelectProperty(String name, MapCodec<T> codec) {
        return this.register(BuiltInClientRegistries.RANGE_SELECT_ITEM_MODEL_PROPERTY_TYPE, name, codec);
    }

    /**
     * @param name the name of the select property
     * @param type the type of the select property
     * @return the registered select property type
     */
    default <P extends SelectItemModelProperty<T>, T> SelectItemModelProperty.Type<P, T> selectProperty(String name, SelectItemModelProperty.Type<P, T> type) {
        return this.register(BuiltInClientRegistries.SELECT_ITEM_MODEL_PROPERTY_TYPE, name, type);
    }

    /**
     * @param name the name of the unbaked special model renderer
     * @param codec the codec of the unbaked special model renderer
     * @return the registered unbaked special model renderer codec
     */
    default <T extends SpecialModelRenderer.Unbaked<?>> MapCodec<T> specialModel(String name, MapCodec<T> codec) {
        return this.register(BuiltInClientRegistries.SPECIAL_MODEL_RENDERER_TYPE, name, codec);
    }

    /**
     * @param name the name of the tint source
     * @param codec the codec of the tint source
     * @return the registered tint source codec
     */
    default <T extends ItemTintSource> MapCodec<T> tintSource(String name, MapCodec<T> codec) {
        return this.register(BuiltInClientRegistries.ITEM_TINT_SOURCE_TYPE, name, codec);
    }

    /**
     * @param name the name of the unbaked model deserializer
     * @param deserializer the unbaked model deserializer to register
     * @return the registered unbaked model deserializer
     */
    default <T extends UnbakedModelDeserializer> T modelDeserializer(String name, T deserializer) {
        return this.register(name, deserializer, UnbakedModelDeserializer::register);
    }
}
