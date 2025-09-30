package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineWolfVariantWolfAssetInfo;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.util.AssetInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WolfVariant.WolfAssetInfo.class)
public abstract class WolfVariantWolfAssetInfoMixin implements ZineWolfVariantWolfAssetInfo {

    @Shadow @Final @Mutable
    private AssetInfo.TextureAssetInfo wild;

    @Shadow @Final @Mutable
    private AssetInfo.TextureAssetInfo tame;

    @Shadow @Final @Mutable
    private AssetInfo.TextureAssetInfo angry;

    @Override
    public void zine$setWild(AssetInfo.TextureAssetInfo wild) {
        this.wild = wild;
    }

    @Override
    public void zine$setTame(AssetInfo.TextureAssetInfo tame) {
        this.tame = tame;
    }

    @Override
    public void zine$setAngry(AssetInfo.TextureAssetInfo angry) {
        this.angry = angry;
    }
}
