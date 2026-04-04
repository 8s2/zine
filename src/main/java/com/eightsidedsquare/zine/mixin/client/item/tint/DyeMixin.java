package com.eightsidedsquare.zine.mixin.client.item.tint;

import com.eightsidedsquare.zine.client.item.tint.ZineDye;
import net.minecraft.client.color.item.Dye;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Dye.class)
public abstract class DyeMixin implements ZineDye {

    @Shadow @Final @Mutable
    private int defaultColor;

    @Override
    public void zine$setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }
}

