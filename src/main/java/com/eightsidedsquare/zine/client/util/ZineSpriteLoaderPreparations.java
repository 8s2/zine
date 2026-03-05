package com.eightsidedsquare.zine.client.util;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;

public interface ZineSpriteLoaderPreparations {

    default TextureAtlasSprite zine$getOrMissing(Identifier id) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
