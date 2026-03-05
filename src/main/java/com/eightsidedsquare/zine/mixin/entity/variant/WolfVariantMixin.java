package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineWolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WolfVariant.class)
public abstract class WolfVariantMixin implements ZineWolfVariant {

    @Shadow @Final @Mutable
    private WolfVariant.AssetInfo adultInfo;

    @Shadow @Final @Mutable
    private WolfVariant.AssetInfo babyInfo;

    @Shadow @Final @Mutable
    private SpawnPrioritySelectors spawnConditions;

    @Override
    public void zine$setAdultInfo(WolfVariant.AssetInfo adultInfo) {
        this.adultInfo = adultInfo;
    }

    @Override
    public void zine$setBabyInfo(WolfVariant.AssetInfo babyInfo) {
        this.babyInfo = babyInfo;
    }

    @Override
    public void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        this.spawnConditions = spawnConditions;
    }
}
