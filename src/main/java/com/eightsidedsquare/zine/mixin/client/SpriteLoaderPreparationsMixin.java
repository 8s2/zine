package com.eightsidedsquare.zine.mixin.client;

import com.eightsidedsquare.zine.client.util.ZineSpriteLoaderPreparations;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpriteLoader.Preparations.class)
public abstract class SpriteLoaderPreparationsMixin implements ZineSpriteLoaderPreparations {

    @Shadow
    public abstract @Nullable TextureAtlasSprite getSprite(Identifier id);

    @Shadow
    @Final
    private TextureAtlasSprite missing;

    @Override
    public TextureAtlasSprite zine$getOrMissing(Identifier id) {
        TextureAtlasSprite sprite = this.getSprite(id);
        return sprite == null ? this.missing : sprite;
    }
}
