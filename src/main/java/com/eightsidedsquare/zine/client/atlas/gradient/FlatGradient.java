package com.eightsidedsquare.zine.client.atlas.gradient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record FlatGradient(int color) implements Gradient {

    public static final MapCodec<FlatGradient> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("argb").forGetter(FlatGradient::color)
    ).apply(i, FlatGradient::new));

    @Override
    public MapCodec<? extends Gradient> getCodec() {
        return CODEC;
    }

    @Override
    public int get(float u, float v, float w) {
        return this.color;
    }
}
