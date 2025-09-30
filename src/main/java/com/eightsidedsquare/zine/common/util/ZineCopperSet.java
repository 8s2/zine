package com.eightsidedsquare.zine.common.util;

import net.minecraft.block.Oxidizable;

public interface ZineCopperSet<T> {

    default T zine$get(Oxidizable.OxidationLevel oxidation, boolean waxed) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
