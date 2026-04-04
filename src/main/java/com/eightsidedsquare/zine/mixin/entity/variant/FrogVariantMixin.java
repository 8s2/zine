package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineFrogVariant;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FrogVariant.class)
public abstract class FrogVariantMixin implements ZineFrogVariant {

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture assetInfo;

    @Shadow @Final @Mutable
    private SpawnPrioritySelectors spawnConditions;

    @Override
    public void zine$setAssetInfo(ClientAsset.ResourceTexture assetInfo) {
        this.assetInfo = assetInfo;
    }

    @Override
    public void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        this.spawnConditions = spawnConditions;
    }

}
