package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.core.ClientAsset;

public interface ZineModelAndTexture<T> {

    default void zine$setModel(T model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setAsset(ClientAsset.ResourceTexture asset) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
