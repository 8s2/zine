package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.util.AssetInfo;

public interface ZineModelAndTexture<T> {

    default void zine$setModel(T model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setAsset(AssetInfo.TextureAssetInfo asset) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
