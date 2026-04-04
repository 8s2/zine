package com.eightsidedsquare.zine.common.util;

import net.minecraft.world.level.block.WeatheringCopper;

public interface ZineWeatheringCopperSet<T> {

    default T zine$get(WeatheringCopper.WeatherState oxidation, boolean waxed) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
