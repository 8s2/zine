package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

public interface ZineCatSoundSet {

    default void zine$setAmbientSound(Holder<SoundEvent> ambientSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setStrayAmbientSound(Holder<SoundEvent> strayAmbientSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHissSound(Holder<SoundEvent> hissSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHurtSound(Holder<SoundEvent> hurtSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDeathSound(Holder<SoundEvent> deathSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setEatSound(Holder<SoundEvent> eatSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBegForFoodSound(Holder<SoundEvent> begForFoodSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPurrSound(Holder<SoundEvent> purrSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPurreowSound(Holder<SoundEvent> purreowSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
