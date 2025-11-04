package com.eightsidedsquare.zine.client.atlas.gradient;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record Gradient1D(List<GradientPoint<Integer>> points) implements Gradient {

    private static final Codec<GradientPoint<Integer>> POINT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.RGB.fieldOf("argb").forGetter(GradientPoint::v),
            Codec.floatRange(0, 1).fieldOf("t").forGetter(GradientPoint::t)
    ).apply(instance, GradientPoint::new));
    public static final MapCodec<Gradient1D> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.nonEmptyListCodec(POINT_CODEC).fieldOf("pts").forGetter(Gradient1D::points)
    ).apply(instance, Gradient1D::new));
    public static final Codec<Gradient1D> CODEC = CodecUtil.nonEmptyListCodec(POINT_CODEC).xmap(
            Gradient1D::new,
            Gradient1D::points
    );

    public Gradient1D(List<GradientPoint<Integer>> points) {
        this.points = new ArrayList<>(points);
        this.points.sort(GradientPoint::compareTo);
    }

    @Override
    public MapCodec<? extends Gradient> getCodec() {
        return MAP_CODEC;
    }

    @Override
    public int get(float u, float v, float w) {
        return Gradient.get(u, this.points, Function.identity());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements Gradient.Builder<Gradient1D> {
        private final List<GradientPoint<Integer>> points = new ArrayList<>();

        public Builder pt(int rgba, float t) {
            this.points.add(new GradientPoint<>(rgba, t));
            return this;
        }

        @Override
        public Gradient1D build() {
            return new Gradient1D(this.points);
        }
    }
}
