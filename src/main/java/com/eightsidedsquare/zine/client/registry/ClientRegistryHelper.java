package com.eightsidedsquare.zine.client.registry;

import com.eightsidedsquare.zine.client.atlas.generator.SpriteGenerator;
import com.eightsidedsquare.zine.client.atlas.gradient.Gradient;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.UnbakedModelDeserializer;
import net.fabricmc.fabric.api.client.rendering.v1.AtlasSourceRegistry;
import net.minecraft.client.gui.hud.debug.DebugHudEntries;
import net.minecraft.client.gui.hud.debug.DebugHudEntry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelTypes;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.util.Identifier;

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
     * @param name the name of the atlas source
     * @param codec the codec of the atlas source
     * @return the registered atlas source codec
     */
    default <T extends AtlasSource> MapCodec<T> atlasSource(String name, MapCodec<T> codec) {
        return this.register(name, codec, AtlasSourceRegistry::register);
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
     * @param name the name of the boolean property
     * @param codec the codec of the boolean property
     * @return the registered boolean property codec
     */
    default <T extends BooleanProperty> MapCodec<T> booleanProperty(String name, MapCodec<T> codec) {
        return this.register(name, codec, BooleanProperties.ID_MAPPER::put);
    }

    /**
     * @param name the name of the debug hud entry
     * @param debugHudEntry the debug hud entry
     * @return the registered debug hud entry
     * @param <T> the type of the debug hud entry
     */
    default <T extends DebugHudEntry> T debugHudEntry(String name, T debugHudEntry) {
        return this.register(name, debugHudEntry, DebugHudEntries::register);
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
        return this.register(name, codec, ItemModelTypes.ID_MAPPER::put);
    }

    /**
     * @param name the name of the entity model layer
     * @param type the sub-name or type of the entity model layer
     * @return the instantiated entity model layer
     */
    default EntityModelLayer modelLayer(String name, String type) {
        return new EntityModelLayer(this.id(name), type);
    }

    /**
     * @param name the name of the entity model layer
     * @return the instantiated entity model layer
     */
    default EntityModelLayer modelLayer(String name) {
        return this.modelLayer(name, "main");
    }

    /**
     * @param name the name of the numeric property
     * @param codec the codec of the numeric property
     * @return the registered numeric property codec
     */
    default <T extends NumericProperty> MapCodec<T> numericProperty(String name, MapCodec<T> codec) {
        return this.register(name, codec, NumericProperties.ID_MAPPER::put);
    }

    /**
     * @param name the name of the select property
     * @param type the type of the select property
     * @return the registered select property type
     */
    default <P extends SelectProperty<T>, T> SelectProperty.Type<P, T> selectProperty(String name, SelectProperty.Type<P, T> type) {
        return this.register(name, type, SelectProperties.ID_MAPPER::put);
    }

    /**
     * @param name the name of the unbaked special model renderer
     * @param codec the codec of the unbaked special model renderer
     * @return the registered unbaked special model renderer codec
     */
    default <T extends SpecialModelRenderer.Unbaked> MapCodec<T> specialModel(String name, MapCodec<T> codec) {
        return this.register(name, codec, SpecialModelTypes.ID_MAPPER::put);
    }

    /**
     * @param name the name of the sprite generator
     * @param codec the codec of the sprite generator
     * @return the registered sprite generator codec
     */
    default <T extends SpriteGenerator> MapCodec<T> spriteGenerator(String name, MapCodec<T> codec) {
        return this.register(name, codec, SpriteGenerator.ID_MAPPER::put);
    }

    /**
     * @param name the name of the tint source
     * @param codec the codec of the tint source
     * @return the registered tint source codec
     */
    default <T extends TintSource> MapCodec<T> tintSource(String name, MapCodec<T> codec) {
        return this.register(name, codec, TintSourceTypes.ID_MAPPER::put);
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
