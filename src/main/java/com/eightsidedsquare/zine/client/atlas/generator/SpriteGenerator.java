package com.eightsidedsquare.zine.client.atlas.generator;

import com.eightsidedsquare.zine.client.ZineClient;
import com.eightsidedsquare.zine.client.atlas.GeneratorSpriteSource;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface SpriteGenerator {

    ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends SpriteGenerator>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    Codec<SpriteGenerator> CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatch(SpriteGenerator::getCodec, Function.identity());

    static void bootstrap() {
        ZineClient.REGISTRY.spriteGenerator("gradient", GradientSpriteGenerator.CODEC);
        ZineClient.REGISTRY.spriteGenerator("noise", NoiseSpriteGenerator.CODEC);
    }

    MapCodec<? extends SpriteGenerator> getCodec();

    GeneratorSpriteSource.Output generate(Identifier outputId, SpriteProperties properties);
}
