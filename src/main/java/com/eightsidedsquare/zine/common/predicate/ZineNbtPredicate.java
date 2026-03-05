package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.nbt.CompoundTag;

public interface ZineNbtPredicate {

    default void zine$setNbt(CompoundTag nbt) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
