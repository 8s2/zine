package com.eightsidedsquare.zine.client.atlas.generator;

import com.eightsidedsquare.zine.client.atlas.GeneratorAtlasSource;
import com.eightsidedsquare.zine.client.atlas.gradient.Gradient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public record NoiseSpriteGenerator(Gradient gradient,
                                   DoublePerlinNoiseSampler.NoiseParameters parameters,
                                   long seed,
                                   Vector2fc scale,
                                   Vector3fc velocity,
                                   float blendMargin) implements SpriteGenerator {

    public static final MapCodec<NoiseSpriteGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Gradient.CODEC.fieldOf("gradient").forGetter(NoiseSpriteGenerator::gradient),
            DoublePerlinNoiseSampler.NoiseParameters.CODEC.fieldOf("parameters").forGetter(NoiseSpriteGenerator::parameters),
            Codec.LONG.optionalFieldOf("seed", 0L).forGetter(NoiseSpriteGenerator::seed),
            Codecs.VECTOR_2F.optionalFieldOf("scale", new Vector2f(1, 1)).forGetter(NoiseSpriteGenerator::scale),
            Codecs.VECTOR_3F.optionalFieldOf("velocity", new Vector3f(0, 0, 1)).forGetter(NoiseSpriteGenerator::velocity),
            Codec.floatRange(0, 0.5f).optionalFieldOf("blend_margin", 0f).forGetter(NoiseSpriteGenerator::blendMargin)

    ).apply(instance, NoiseSpriteGenerator::new));

    @Override
    public MapCodec<? extends SpriteGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public GeneratorAtlasSource.Output generate(Identifier outputId, SpriteProperties properties) {
        DoublePerlinNoiseSampler sampler = DoublePerlinNoiseSampler.create(Random.create(this.seed), this.parameters);
        if(this.blendMargin == 0) {
            return (u, v, w) -> this.gradient.get(this.value(sampler, u, v, w), 0, w);
        }
        return (u, v, w) -> this.gradient.get(this.valueUVW(sampler, u, v, w), 0, w);
    }

    private float valueU(DoublePerlinNoiseSampler sampler, float u, float v, float w) {
        float blend = this.blendFac(u);
        float a = this.value(sampler, u, v, w);
        if(blend == 0) {
            return a;
        }
        float b = this.value(sampler, 1 - u, v, w);
        return MathHelper.lerp(blend, a, b);
    }

    private float valueUV(DoublePerlinNoiseSampler sampler, float u, float v, float w) {
        float blend = this.blendFac(v);
        float a = this.valueU(sampler, u, v, w);
        if(blend == 0) {
            return a;
        }
        float b = this.valueU(sampler, u, 1 - v, w);
        return MathHelper.lerp(blend, a, b);
    }

    private float valueUVW(DoublePerlinNoiseSampler sampler, float u, float v, float w) {
        float blend = this.blendFac(w);
        float a = this.valueUV(sampler, u, v, w);
        if(blend == 0) {
            return a;
        }
        float b = this.valueUV(sampler, u, v, 1 - w);
        return MathHelper.lerp(blend, a, b);
    }

    private float value(DoublePerlinNoiseSampler sampler, float u, float v, float w) {
        double value = sampler.sample(u * this.scale.x() + this.velocity.x() * w, v * this.scale.y() + this.velocity.y() * w, this.velocity.z() * w);
        return (float) (value + 1) * 0.5f;
    }

    private float blendFac(float x) {
        float r = 1f / (2f * this.blendMargin);
        return (1 - MathHelper.cos(MathHelper.PI * Math.max(0, Math.abs(1 - 2 * x) * r - r + 1f))) * 0.25f;
    }
}
