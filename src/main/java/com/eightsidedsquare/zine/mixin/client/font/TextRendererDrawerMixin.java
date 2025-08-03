package com.eightsidedsquare.zine.mixin.client.font;

import com.eightsidedsquare.zine.client.font.ZineOutlinable;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.font.BakedGlyph;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TextRenderer.Drawer.class)
public abstract class TextRendererDrawerMixin implements ZineOutlinable {

    @Unique
    private int outlineColor;

    @Override
    public int zine$getOutlineColor() {
        return this.outlineColor;
    }

    @Override
    public void zine$setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
    }

    // TODO: Update
//    @ModifyExpressionValue(method = "accept", at = @At(value = "NEW", target = "(FFIILnet/minecraft/client/font/BakedGlyph;Lnet/minecraft/text/Style;FF)Lnet/minecraft/client/font/BakedGlyph$DrawnGlyph;"))
//    private BakedGlyph.DrawnGlyph zine$applyOutlineColor(BakedGlyph.DrawnGlyph glyph) {
//        if(this.zine$hasOutline()) {
//            glyph.zine$setOutlineColor(this.zine$getOutlineColor());
//        }
//        return glyph;
//    }
}
