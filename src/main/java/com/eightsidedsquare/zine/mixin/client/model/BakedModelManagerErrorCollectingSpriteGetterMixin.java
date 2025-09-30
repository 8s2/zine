package com.eightsidedsquare.zine.mixin.client.model;

import net.minecraft.client.render.model.ErrorCollectingSpriteGetter;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Atlases;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net/minecraft/client/render/model/BakedModelManager$1")
public abstract class BakedModelManagerErrorCollectingSpriteGetterMixin implements ErrorCollectingSpriteGetter {

    @Shadow @Final private Sprite missingSprite;

    @Shadow @Final
    SpriteLoader.StitchResult field_61871;

    @Override
    public Sprite zine$get(SpriteIdentifier id) {
        if (id.getAtlasId().equals(Atlases.BLOCKS)) {
            Sprite sprite = this.field_61871.getSprite(id.getTextureId());
            if (sprite != null) {
                return sprite;
            }
        }
        return this.missingSprite;
    }

    @Override
    public Sprite zine$getMissing() {
        return this.missingSprite;
    }
}
