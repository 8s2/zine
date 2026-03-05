package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineAgeableMobSoundVariant;
import com.eightsidedsquare.zine.common.entity.variant.ZineCatSoundSet;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CatSoundVariant.class)
public abstract class CatSoundVariantMixin implements ZineAgeableMobSoundVariant<CatSoundVariant.CatSoundSet> {

    @Shadow @Final @Mutable
    private CatSoundVariant.CatSoundSet adultSounds;

    @Shadow @Final @Mutable
    private CatSoundVariant.CatSoundSet babySounds;

    @Override
    public void zine$setAdultSounds(CatSoundVariant.CatSoundSet adultSounds) {
        this.adultSounds = adultSounds;
    }

    @Override
    public void zine$setBabySounds(CatSoundVariant.CatSoundSet babySounds) {
        this.babySounds = babySounds;
    }

    @Mixin(CatSoundVariant.CatSoundSet.class)
    public static abstract class CatSoundSetMixin implements ZineCatSoundSet {

        @Shadow @Final @Mutable
        private Holder<SoundEvent> ambientSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> strayAmbientSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> hissSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> hurtSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> deathSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> eatSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> begForFoodSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> purrSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> purreowSound;

        @Override
        public void zine$setAmbientSound(Holder<SoundEvent> ambientSound) {
            this.ambientSound = ambientSound;
        }

        @Override
        public void zine$setStrayAmbientSound(Holder<SoundEvent> strayAmbientSound) {
            this.strayAmbientSound = strayAmbientSound;
        }

        @Override
        public void zine$setHissSound(Holder<SoundEvent> hissSound) {
            this.hissSound = hissSound;
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
        public void zine$setEatSound(Holder<SoundEvent> eatSound) {
            this.eatSound = eatSound;
        }

        @Override
        public void zine$setBegForFoodSound(Holder<SoundEvent> begForFoodSound) {
            this.begForFoodSound = begForFoodSound;
        }

        @Override
        public void zine$setPurrSound(Holder<SoundEvent> purrSound) {
            this.purrSound = purrSound;
        }

        @Override
        public void zine$setPurreowSound(Holder<SoundEvent> purreowSound) {
            this.purreowSound = purreowSound;
        }

    }
}
