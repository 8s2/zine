package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

public interface ZineSoundSet {

    default void zine$setAmbientSound(Holder<SoundEvent> ambientSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHurtSound(Holder<SoundEvent> hurtSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDeathSound(Holder<SoundEvent> deathSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setStepSound(Holder<SoundEvent> stepSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
