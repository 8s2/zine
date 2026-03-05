package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineAgeableMobVariant;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.animal.pig.PigVariant;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PigVariant.class)
public abstract class PigVariantMixin implements ZineAgeableMobVariant<PigVariant.ModelType> {

    @Shadow @Final @Mutable
    private ModelAndTexture<PigVariant.ModelType> modelAndTexture;

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture babyTexture;

    @Shadow @Final @Mutable
    private SpawnPrioritySelectors spawnConditions;

    @Override
    public void zine$setModelAndTexture(ModelAndTexture<PigVariant.ModelType> modelAndTexture) {
        this.modelAndTexture = modelAndTexture;
    }

    @Override
    public void zine$setBabyTexture(ClientAsset.ResourceTexture babyTexture) {
        this.babyTexture = babyTexture;;
    }

    @Override
    public void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        this.spawnConditions = spawnConditions;
    }
}
