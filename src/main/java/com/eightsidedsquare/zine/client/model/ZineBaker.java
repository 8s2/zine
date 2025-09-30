package com.eightsidedsquare.zine.client.model;

import com.eightsidedsquare.zine.client.util.SpriteIds;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.minecraft.client.render.model.ModelTextures;
import net.minecraft.client.render.model.SimpleModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Atlases;
import net.minecraft.util.Identifier;

public interface ZineBaker {

    default Sprite zine$getSprite(SpriteIdentifier id, SimpleModel model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Sprite zine$getMissingSprite(String name, SimpleModel model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Sprite zine$getSprite(ModelTextures texture, String name, SimpleModel model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Sprite zine$getSprite(SpriteIdentifier id) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Sprite zine$getSprite(Identifier id) {
        return this.zine$getSprite(SpriteIds.block(id));
    }

    default Sprite zine$getMissingSprite() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default SpriteFinder zine$getSpriteFinder(Identifier atlasId) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default SpriteFinder zine$getBlockSpriteFinder() {
        return this.zine$getSpriteFinder(Atlases.BLOCKS);
    }

}
