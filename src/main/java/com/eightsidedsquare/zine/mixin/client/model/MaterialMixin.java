package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.model.ZineMaterial;
import com.mojang.blaze3d.platform.Transparency;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Material.class)
public abstract class MaterialMixin implements ZineMaterial {

    @Mixin(Material.Baked.class)
    public static abstract class BakedMixin implements ZineMaterial.Baked {
        @Shadow
        @Final
        private boolean forceTranslucent;
        @Shadow
        @Final
        private TextureAtlasSprite sprite;
        @Unique
        private int materialFlags = -1;
        @Unique
        private Transparency transparency;

        @Override
        public int zine$getMaterialFlags() {
            if(this.materialFlags == -1) {
                boolean translucent = this.forceTranslucent || this.zine$getTransparency().hasTranslucent();
                boolean animated = this.sprite.contents().isAnimated();
                this.materialFlags = (translucent ? 1 : 0) | (animated ? 2 : 0);
            }
            return this.materialFlags;
        }

        @Override
        public Transparency zine$getTransparency() {
            if(this.transparency == null) {
                this.transparency = this.sprite.contents().computeTransparency(0, 0, 1, 1);
            }
            return this.transparency;
        }
    }
}
