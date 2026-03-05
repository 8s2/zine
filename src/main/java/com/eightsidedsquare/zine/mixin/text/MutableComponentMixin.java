package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ZineMutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MutableComponent.class)
public abstract class MutableComponentMixin implements Component, ZineMutableComponent {

    @Shadow public abstract MutableComponent setStyle(Style style);

    @Shadow public abstract Style getStyle();

    @Unique
    private boolean frozen = false;

    @Unique
    private MutableComponent zine$cast() {
        return (MutableComponent) (Object) this;
    }

    @Override
    public MutableComponent zine$freeze() {
        this.frozen = true;
        return this.zine$cast();
    }

    @Override
    public MutableComponent zine$unfreeze() {
        this.frozen = false;
        return this.zine$cast();
    }

    @Override
    public MutableComponent zine$withOutlineColor(int outlineColor) {
        return this.setStyle(this.getStyle().zine$withOutlineColor(outlineColor));
    }

    @Inject(method = "setStyle", at = @At("HEAD"), cancellable = true)
    private void zine$cancelSetStyleIfFrozen(Style style, CallbackInfoReturnable<MutableComponent> cir) {
        if(this.frozen) {
            cir.setReturnValue(this.zine$cast());
        }
    }

    @Inject(method = "append(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;", at = @At("HEAD"), cancellable = true)
    private void zine$cancelAppendIfFrozen(Component text, CallbackInfoReturnable<MutableComponent> cir) {
        if(this.frozen) {
            cir.setReturnValue(this.zine$cast());
        }
    }
}
