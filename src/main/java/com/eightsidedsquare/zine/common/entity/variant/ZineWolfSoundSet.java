package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public interface ZineWolfSoundSet {

    default void zine$setAmbientSound(Holder<SoundEvent> ambientSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setAmbientSound(SoundEvent ambientSound) {
        this.zine$setAmbientSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(ambientSound));
    }

    default void zine$setDeathSound(Holder<SoundEvent> deathSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDeathSound(SoundEvent deathSound) {
        this.zine$setAmbientSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(deathSound));
    }

    default void zine$setGrowlSound(Holder<SoundEvent> growlSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setGrowlSound(SoundEvent growlSound) {
        this.zine$setAmbientSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(growlSound));
    }

    default void zine$setHurtSound(Holder<SoundEvent> hurtSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setHurtSound(SoundEvent hurtSound) {
        this.zine$setAmbientSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(hurtSound));
    }

    default void zine$setPantSound(Holder<SoundEvent> pantSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPantSound(SoundEvent pantSound) {
        this.zine$setAmbientSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(pantSound));
    }

    default void zine$setWhineSound(Holder<SoundEvent> whineSound) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setWhineSound(SoundEvent whineSound) {
        this.zine$setAmbientSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(whineSound));
    }

}
