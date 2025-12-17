package com.eightsidedsquare.zine.mixin.client.model;

import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ErrorCollectingSpriteGetter;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net/minecraft/client/render/model/BakedModelManager$1")
public abstract class BakedModelManagerErrorCollectingSpriteGetterMixin implements ErrorCollectingSpriteGetter {

    @Shadow @Final
    private Sprite missingBlockSprite;

    @Shadow @Final
    private Sprite missingItemSprite;

    @Shadow @Final
    SpriteLoader.StitchResult field_61871;

    @Shadow @Final
    SpriteLoader.StitchResult field_64469;

    @SuppressWarnings("deprecation")
    @Override
    public Sprite zine$get(SpriteIdentifier id) {
        Identifier identifier = id.getAtlasId();
        boolean blockOrItem = identifier.equals(BakedModelManager.BLOCK_OR_ITEM);
        boolean item = identifier.equals(SpriteAtlasTexture.ITEMS_ATLAS_TEXTURE);
        boolean block = identifier.equals(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        if(blockOrItem || item) {
            Sprite sprite = this.field_64469.getSprite(id.getTextureId());
            if (sprite != null) {
                return sprite;
            }
        }
        if(blockOrItem || block) {
            Sprite sprite = this.field_61871.getSprite(id.getTextureId());
            if (sprite != null) {
                return sprite;
            }
        }
        return item ? this.missingItemSprite : this.missingBlockSprite;
    }

    @Override
    public Sprite zine$getMissing() {
        return this.missingBlockSprite;
    }
}
