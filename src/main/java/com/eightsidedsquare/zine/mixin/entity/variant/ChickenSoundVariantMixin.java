package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineAgeableMobSoundVariant;
import com.eightsidedsquare.zine.common.entity.variant.ZineSoundSet;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.chicken.ChickenSoundVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChickenSoundVariant.class)
public abstract class ChickenSoundVariantMixin implements ZineAgeableMobSoundVariant<ChickenSoundVariant.ChickenSoundSet> {

    @Shadow @Final @Mutable
    private ChickenSoundVariant.ChickenSoundSet adultSounds;

    @Shadow @Final @Mutable
    private ChickenSoundVariant.ChickenSoundSet babySounds;

    @Override
    public void zine$setAdultSounds(ChickenSoundVariant.ChickenSoundSet adultSounds) {
        this.adultSounds = adultSounds;
    }

    @Override
    public void zine$setBabySounds(ChickenSoundVariant.ChickenSoundSet babySounds) {
        this.babySounds = babySounds;
    }

    @Mixin(ChickenSoundVariant.ChickenSoundSet.class)
    public static abstract class ChickenSoundSetMixin implements ZineSoundSet {

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
