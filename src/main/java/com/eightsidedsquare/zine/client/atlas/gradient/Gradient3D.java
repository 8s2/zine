package com.eightsidedsquare.zine.client.atlas.gradient;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public record Gradient3D(List<GradientPoint<Gradient2D>> points) implements Gradient {

    private static final Codec<GradientPoint<Gradient2D>> POINT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Gradient2D.CODEC.fieldOf("gradient").forGetter(GradientPoint::v),
            Codec.floatRange(0, 1).fieldOf("t").forGetter(GradientPoint::t)
    ).apply(instance, GradientPoint::new));
    public static final MapCodec<Gradient3D> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.nonEmptyListCodec(POINT_CODEC).fieldOf("pts").forGetter(Gradient3D::points)
    ).apply(instance, Gradient3D::new));
    public static final Codec<Gradient3D> CODEC = CodecUtil.nonEmptyListCodec(POINT_CODEC).xmap(
            Gradient3D::new,
            Gradient3D::points
    );

    @Override
    public MapCodec<? extends Gradient> getCodec() {
        return MAP_CODEC;
    }

    @Override
    public int get(float u, float v, float w) {
        return Gradient.get(w, this.points, gradient -> gradient.get(u, v, w));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements Gradient.Builder<Gradient3D> {
        private final List<GradientPoint<Gradient2D>> points = new ArrayList<>();

        public Builder pt(Gradient2D.Builder builder, float t) {
            this.points.add(new GradientPoint<>(builder.build(), t));
            return this;
        }

        @Override
        public Gradient3D build() {
            return new Gradient3D(this.points);
        }
    }
}
