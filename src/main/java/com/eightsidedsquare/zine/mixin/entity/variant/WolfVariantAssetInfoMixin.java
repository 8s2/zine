package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineWolfVariant;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WolfVariant.AssetInfo.class)
public abstract class WolfVariantAssetInfoMixin implements ZineWolfVariant.ZineAssetInfo {

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture wild;

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture tame;

    @Shadow @Final @Mutable
    private ClientAsset.ResourceTexture angry;

    @Override
    public void zine$setWild(ClientAsset.ResourceTexture wild) {
        this.wild = wild;
    }

    @Override
    public void zine$setTame(ClientAsset.ResourceTexture tame) {
        this.tame = tame;
    }

    @Override
    public void zine$setAngry(ClientAsset.ResourceTexture angry) {
        this.angry = angry;
    }
}
