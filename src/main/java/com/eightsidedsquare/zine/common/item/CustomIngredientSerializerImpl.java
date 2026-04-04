package com.eightsidedsquare.zine.common.item;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record CustomIngredientSerializerImpl<T extends CustomIngredient>(Identifier id, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) implements CustomIngredientSerializer<T> {
    @Override
    public Identifier getIdentifier() {
        return this.id;
    }

    @Override
    public MapCodec<T> getCodec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> getStreamCodec() {
        return this.streamCodec;
    }
}
