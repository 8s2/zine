package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.resources.Identifier;

public interface ZineClientAssetResourceTexture {

    default void zine$setId(Identifier id, boolean updateTexturePath) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTexturePath(Identifier texturePath) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
