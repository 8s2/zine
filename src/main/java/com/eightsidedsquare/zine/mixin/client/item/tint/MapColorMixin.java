package com.eightsidedsquare.zine.mixin.client.item.tint;

import com.eightsidedsquare.zine.client.item.tint.ZineMapColor;
import net.minecraft.client.color.item.MapColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MapColor.class)
public abstract class MapColorMixin implements ZineMapColor {

    @Shadow @Final @Mutable
    private int defaultColor;

    @Override
    public void zine$setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }
}
