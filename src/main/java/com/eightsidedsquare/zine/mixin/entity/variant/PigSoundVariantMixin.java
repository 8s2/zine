package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineAgeableMobSoundVariant;
import com.eightsidedsquare.zine.common.entity.variant.ZineSoundSet;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.pig.PigSoundVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PigSoundVariant.class)
public abstract class PigSoundVariantMixin implements ZineAgeableMobSoundVariant<PigSoundVariant.PigSoundSet> {

    @Shadow @Final @Mutable
    private PigSoundVariant.PigSoundSet adultSounds;

    @Shadow @Final @Mutable
    private PigSoundVariant.PigSoundSet babySounds;

    @Override
    public void zine$setAdultSounds(PigSoundVariant.PigSoundSet adultSounds) {
        this.adultSounds = adultSounds;
    }

    @Override
    public void zine$setBabySounds(PigSoundVariant.PigSoundSet babySounds) {
        this.babySounds = babySounds;
    }

    @Mixin(PigSoundVariant.PigSoundSet.class)
    public static abstract class PigSoundSetMixin implements ZineSoundSet {

        @Shadow @Final @Mutable
        private Holder<SoundEvent> ambientSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> hurtSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> deathSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> stepSound;

        @Override
        public void zine$setAmbientSound(Holder<SoundEvent> ambientSound) {
            this.ambientSound = ambientSound;
        }

        @Override
        public void zine$setHurtSound(Holder<SoundEvent> hurtSound) {
            this.hurtSound = hurtSound;
        }

        @Override
        public void zine$setDeathSound(Holder<SoundEvent> deathSound) {
            this.deathSound = deathSound;
        }

        @Override
        public void zine$setStepSound(Holder<SoundEvent> stepSound) {
            this.stepSound = stepSound;
        }
    }
}
