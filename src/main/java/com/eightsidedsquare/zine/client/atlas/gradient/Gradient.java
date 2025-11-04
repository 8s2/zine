package com.eightsidedsquare.zine.client.atlas.gradient;

import com.eightsidedsquare.zine.client.ZineClient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;

import java.util.List;
import java.util.function.Function;

public interface Gradient {

    Codecs.IdMapper<Identifier, MapCodec<? extends Gradient>> ID_MAPPER = new Codecs.IdMapper<>();
    Codec<Gradient> CODEC = ID_MAPPER.getCodec(Identifier.CODEC).dispatch(Gradient::getCodec, Function.identity());

    static void bootstrap() {
        ZineClient.REGISTRY.gradient("flat", FlatGradient.CODEC);
        ZineClient.REGISTRY.gradient("1d", Gradient1D.MAP_CODEC);
        ZineClient.REGISTRY.gradient("2d", Gradient2D.MAP_CODEC);
        ZineClient.REGISTRY.gradient("3d", Gradient3D.MAP_CODEC);
    }

    MapCodec<? extends Gradient> getCodec();

    int get(float u, float v, float w);

    static <T> int get(float t, List<GradientPoint<T>> points, Function<T, Integer> colorGetter) {
        GradientPoint<T> end = points.getLast();
        if(points.size() == 1) {
            return colorGetter.apply(end.v());
        }
        if(t >= 1 || t >= end.t()) {
            return colorGetter.apply(end.v());
        }
        GradientPoint<T> start = points.getFirst();
        if(t <= 0 || t <= start.t()) {
            return colorGetter.apply(start.v());
        }
        for(GradientPoint<T> point : points) {
            if(t == point.t()) {
                return colorGetter.apply(point.v());
            }else if(t > point.t()) {
                start = point;
            }else {
                end = point;
                break;
            }
        }
        float delta = (t - start.t()) / (end.t() - start.t());
        return ColorHelper.lerp(delta, colorGetter.apply(start.v()), colorGetter.apply(end.v()));
    }

    interface Builder<T extends Gradient> {
        T build();
    }

}
