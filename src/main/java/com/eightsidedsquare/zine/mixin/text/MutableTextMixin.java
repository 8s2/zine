package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.CustomStyleAttribute;
import com.eightsidedsquare.zine.common.text.CustomStyleAttributeContainer;
import com.eightsidedsquare.zine.common.text.ZineMutableText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MutableText.class)
public abstract class MutableTextMixin implements Text, ZineMutableText {

    @Shadow public abstract MutableText setStyle(Style style);

    @Shadow public abstract Style getStyle();

    @Unique
    private boolean frozen = false;

    @Unique
    private MutableText zine$cast() {
        return (MutableText) (Object) this;
    }

    @Override
    public MutableText zine$freeze() {
        this.frozen = true;
        return this.zine$cast();
    }

    @Override
    public MutableText zine$unfreeze() {
        this.frozen = false;
        return this.zine$cast();
    }

    @Override
    public <T> MutableText zine$withCustomAttribute(CustomStyleAttribute<T> attribute, T value) {
        return this.setStyle(this.getStyle().zine$withCustomAttribute(attribute, value));
    }

    @Override
    public MutableText zine$withCustomAttributes(CustomStyleAttributeContainer attributes) {
        return this.setStyle(this.getStyle().zine$withCustomAttributes(attributes));
    }

    @Inject(method = "setStyle", at = @At("HEAD"), cancellable = true)
    private void zine$cancelSetStyleIfFrozen(Style style, CallbackInfoReturnable<MutableText> cir) {
        if(this.frozen) {
            cir.setReturnValue(this.zine$cast());
        }
    }

    @Inject(method = "append(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;", at = @At("HEAD"), cancellable = true)
    private void zine$cancelAppendIfFrozen(Text text, CallbackInfoReturnable<MutableText> cir) {
        if(this.frozen) {
            cir.setReturnValue(this.zine$cast());
        }
    }
}
