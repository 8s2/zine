package com.eightsidedsquare.zine.mixin.client.texture;

import com.eightsidedsquare.zine.client.atlas.ZineNativeImage;
import com.mojang.blaze3d.platform.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NativeImage.class)
public abstract class NativeImageMixin implements ZineNativeImage {

    @Shadow private long pixels;

    @Override
    public long zine$getPointer() {
        return this.pixels;
    }
}
