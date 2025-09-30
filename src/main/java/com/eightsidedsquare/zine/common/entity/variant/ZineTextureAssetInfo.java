package com.eightsidedsquare.zine.common.entity.variant;

import net.minecraft.util.Identifier;

public interface ZineTextureAssetInfo {

    default void zine$setId(Identifier id, boolean updateTexturePath) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTexturePath(Identifier texturePath) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
