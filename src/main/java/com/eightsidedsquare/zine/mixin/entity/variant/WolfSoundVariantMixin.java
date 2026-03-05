package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineAgeableMobSoundVariant;
import com.eightsidedsquare.zine.common.entity.variant.ZineWolfSoundSet;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WolfSoundVariant.class)
public abstract class WolfSoundVariantMixin implements ZineAgeableMobSoundVariant<WolfSoundVariant.WolfSoundSet> {

    @Shadow @Final @Mutable
    private WolfSoundVariant.WolfSoundSet adultSounds;

    @Shadow @Final @Mutable
    private WolfSoundVariant.WolfSoundSet babySounds;

    @Override
    public void zine$setAdultSounds(WolfSoundVariant.WolfSoundSet adultSounds) {
        this.adultSounds = adultSounds;
    }

    @Override
    public void zine$setBabySounds(WolfSoundVariant.WolfSoundSet babySounds) {
        this.babySounds = babySounds;
    }

    @Mixin(WolfSoundVariant.WolfSoundSet.class)
    public static abstract class WolfSoundSetMixin implements ZineWolfSoundSet {
        @Shadow @Final @Mutable
        private Holder<SoundEvent> ambientSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> deathSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> growlSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> hurtSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> pantSound;

        @Shadow @Final @Mutable
        private Holder<SoundEvent> whineSound;

        @Override
        public void zine$setAmbientSound(Holder<SoundEvent> ambientSound) {
            this.ambientSound = ambientSound;
        }

        @Override
        public void zine$setDeathSound(Holder<SoundEvent> deathSound) {
            this.deathSound = deathSound;
        }

        @Override
        public void zine$setGrowlSound(Holder<SoundEvent> growlSound) {
            this.growlSound = growlSound;
        }

        @Override
        public void zine$setHurtSound(Holder<SoundEvent> hurtSound) {
            this.hurtSound = hurtSound;
        }

        @Override
        public void zine$setPantSound(Holder<SoundEvent> pantSound) {
            this.pantSound = pantSound;
        }

        @Override
        public void zine$setWhineSound(Holder<SoundEvent> whineSound) {
            this.whineSound = whineSound;
        }
    }
}
