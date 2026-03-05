package com.eightsidedsquare.zine.mixin.client.item.tint;

import com.eightsidedsquare.zine.client.item.tint.ZineConstant;
import net.minecraft.client.color.item.Constant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Constant.class)
public abstract class ConstantMixin implements ZineConstant {

    @Shadow @Final @Mutable
    private int value;

    @Override
    public void zine$setValue(int value) {
        this.value = value;
    }
}
