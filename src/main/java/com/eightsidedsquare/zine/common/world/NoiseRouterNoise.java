package com.eightsidedsquare.zine.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;

import java.util.function.Function;

public enum NoiseRouterNoise implements StringRepresentable {
    BARRIER("barrier", NoiseRouter::barrierNoise),
    FLUID_LEVEL_FLOODEDNESS("fluid_level_floodedness", NoiseRouter::fluidLevelFloodednessNoise),
    FLUID_LEVEL_SPREAD("fluid_level_spread", NoiseRouter::fluidLevelSpreadNoise),
    LAVA("lava", NoiseRouter::lavaNoise),
    TEMPERATURE("temperature", NoiseRouter::temperature),
    VEGETATION("vegetation", NoiseRouter::vegetation),
    CONTINENTS("continents", NoiseRouter::continents),
    EROSION("erosion", NoiseRouter::erosion),
    DEPTH("depth", NoiseRouter::depth),
    RIDGES("ridges", NoiseRouter::ridges),
    PRELIMINARY_SURFACE_LEVEL("preliminary_surface_level", NoiseRouter::preliminarySurfaceLevel),
    FINAL_DENSITY("final_density", NoiseRouter::finalDensity),
    VEIN_TOGGLE("vein_toggle", NoiseRouter::veinToggle),
    VEIN_RIDGED("vein_ridged", NoiseRouter::veinRidged),
    VEIN_GAP("vein_gap", NoiseRouter::veinGap);

    public static final Codec<NoiseRouterNoise> CODEC = StringRepresentable.fromEnum(NoiseRouterNoise::values);

    private final String name;
    public final Function<NoiseRouter, DensityFunction> densityFunctionGetter;

    NoiseRouterNoise(String name, Function<NoiseRouter, DensityFunction> densityFunctionGetter) {
        this.name = name;
        this.densityFunctionGetter = densityFunctionGetter;
    }

    @Override
    public String toString() {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}