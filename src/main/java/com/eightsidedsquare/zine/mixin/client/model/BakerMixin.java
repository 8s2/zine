package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.model.ZineBaker;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ErrorCollectingSpriteGetter;
import net.minecraft.client.render.model.ModelTextures;
import net.minecraft.client.render.model.SimpleModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Baker.class)
public interface BakerMixin extends ZineBaker {

    @Shadow ErrorCollectingSpriteGetter getSpriteGetter();

    @Override
    default Sprite zine$getSprite(SpriteIdentifier id, SimpleModel model) {
        return this.getSpriteGetter().get(id, model);
    }

    @Override
    default Sprite zine$getMissingSprite(String name, SimpleModel model) {
        return this.getSpriteGetter().getMissing(name, model);
    }

    @Override
    default Sprite zine$getSprite(ModelTextures texture, String name, SimpleModel model) {
        return this.getSpriteGetter().get(texture, name, model);
    }

    @Override
    default Sprite zine$getSprite(SpriteIdentifier id) {
        return this.getSpriteGetter().zine$get(id);
    }

    @Override
    default Sprite zine$getMissingSprite() {
        return this.getSpriteGetter().zine$getMissing();
    }
}
