package com.eightsidedsquare.zine.mixin.entity.variant;

import com.eightsidedsquare.zine.common.entity.variant.ZineClientAssetResourceTexture;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientAsset.ResourceTexture.class)
public abstract class ClientAssetResourceTextureMixin implements ZineClientAssetResourceTexture {

    @Shadow @Final @Mutable
    private Identifier id;

    @Shadow @Final @Mutable
    private Identifier texturePath;

    @Shadow
    private static String lambda$new$0(String par1) {
        throw new AssertionError();
    }

    @Override
    public void zine$setId(Identifier id, boolean updateTexturePath) {
        this.id = id;
        if(updateTexturePath) {
            this.zine$setTexturePath(this.id.withPath(ClientAssetResourceTextureMixin::lambda$new$0));
        }
    }

    @Override
    public void zine$setTexturePath(Identifier texturePath) {
        this.texturePath = texturePath;
    }
}
