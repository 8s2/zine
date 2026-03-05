package com.eightsidedsquare.zine.common.entity.variant;

public interface ZineAgeableMobSoundVariant<T> {

    default void zine$setAdultSounds(T adultSounds) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBabySounds(T babySounds) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
