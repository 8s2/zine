package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineCatVariant;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CatVariant.class)
public abstract class CatVariantMixin implements ZineCatVariant {

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture adultAssetInfo;

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture babyAssetInfo;

    @Shadow @Final @Mutable
    private SpawnPrioritySelectors spawnConditions;

    @Override
    public void zine$setAdultAssetInfo(ClientAsset.ResourceTexture adultAssetInfo) {
        this.adultAssetInfo = adultAssetInfo;
    }

    @Override
    public void zine$setBabyAssetInfo(ClientAsset.ResourceTexture babyAssetInfo) {
        this.babyAssetInfo = babyAssetInfo;
    }

    @Override
    public void zine$setSpawnConditions(SpawnPrioritySelectors spawnConditions) {
        this.spawnConditions = spawnConditions;
    }
}
