package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineModelAndTexture;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.ModelAndTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelAndTexture.class)
public abstract class ModelAndTextureMixin<T> implements ZineModelAndTexture<T> {

    @Shadow @Final @Mutable
    private T model;

    @Shadow @Final @Mutable
    private AssetInfo.TextureAssetInfo asset;

    @Override
    public void zine$setModel(T model) {
        this.model = model;
    }

    @Override
    public void zine$setAsset(AssetInfo.TextureAssetInfo asset) {
        this.asset = asset;
    }
}
