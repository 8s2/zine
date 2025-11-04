package com.eightsidedsquare.zine.client.atlas.gradient;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public record Gradient2D(List<GradientPoint<Gradient1D>> points) implements Gradient {

    private static final Codec<GradientPoint<Gradient1D>> POINT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Gradient1D.CODEC.fieldOf("gradient").forGetter(GradientPoint::v),
            Codec.floatRange(0, 1).fieldOf("t").forGetter(GradientPoint::t)
    ).apply(instance, GradientPoint::new));
    public static final MapCodec<Gradient2D> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.nonEmptyListCodec(POINT_CODEC).fieldOf("pts").forGetter(Gradient2D::points)
    ).apply(instance, Gradient2D::new));
    public static final Codec<Gradient2D> CODEC = CodecUtil.nonEmptyListCodec(POINT_CODEC).xmap(
            Gradient2D::new,
            Gradient2D::points
    );

    @Override
    public MapCodec<? extends Gradient> getCodec() {
        return MAP_CODEC;
    }

    @Override
    public int get(float u, float v, float w) {
        return Gradient.get(v, this.points, gradient -> gradient.get(u, v, w));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements Gradient.Builder<Gradient2D> {
        private final List<GradientPoint<Gradient1D>> points = new ArrayList<>();

        public Builder pt(Gradient1D.Builder builder, float t) {
            this.points.add(new GradientPoint<>(builder.build(), t));
            return this;
        }

        @Override
        public Gradient2D build() {
            return new Gradient2D(this.points);
        }
    }
}
