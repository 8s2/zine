package com.eightsidedsquare.zine.client.atlas.gradient;

import org.jetbrains.annotations.NotNull;

public record GradientPoint<T>(T v, float t) implements Comparable<GradientPoint<?>> {
    @Override
    public int compareTo(@NotNull GradientPoint<?> pt) {
        return Float.compare(this.t, pt.t);
    }
}
