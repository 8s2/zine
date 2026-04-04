package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ZineStyle;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Style.class)
public abstract class StyleMixin implements ZineStyle {

    @Shadow @Final @Nullable
    private TextColor color;
    @Shadow @Final @Nullable
    private Integer shadowColor;
    @Shadow @Final @Nullable
    private Boolean bold;
    @Shadow @Final @Nullable
    private Boolean italic;
    @Shadow @Final @Nullable
    private Boolean underlined;
    @Shadow @Final @Nullable
    private Boolean strikethrough;
    @Shadow @Final @Nullable
    private Boolean obfuscated;
    @Shadow @Final @Nullable
    private ClickEvent clickEvent;
    @Shadow @Final @Nullable
    private HoverEvent hoverEvent;
    @Shadow @Final @Nullable
    private String insertion;
    @Shadow @Final @Nullable
    private FontDescription font;
    @Unique
    private int outlineColor;

    @Invoker("<init>")
    private static Style zine$invokeInit(
            @Nullable TextColor color,
            @Nullable Integer shadowColor,
            @Nullable Boolean bold,
            @Nullable Boolean italic,
            @Nullable Boolean underlined,
            @Nullable Boolean strikethrough,
            @Nullable Boolean obfuscated,
            @Nullable ClickEvent clickEvent,
            @Nullable HoverEvent hoverEvent,
            @Nullable String insertion,
            @Nullable FontDescription font) {
        throw new AssertionError();
    }

    @Override
    public Style zine$copy() {
        Style style = zine$invokeInit(
                this.color,
                this.shadowColor,
                this.bold,
                this.italic,
                this.underlined,
                this.strikethrough,
                this.obfuscated,
                this.clickEvent,
                this.hoverEvent,
                this.insertion,
                this.font
        );
        style.zine$setOutlineColor(this.outlineColor);
        return style;
    }

    @Override
    public int zine$getOutlineColor() {
        return this.outlineColor;
    }

    @Override
    public Style zine$withOutlineColor(int outlineColor) {
        Style style = this.zine$copy();
        style.zine$setOutlineColor(outlineColor);
        return style;
    }

    @Override
    public void zine$setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
    }

    @ModifyExpressionValue(method = {
            "withColor(Lnet/minecraft/network/chat/TextColor;)Lnet/minecraft/network/chat/Style;",
            "withShadowColor",
            "withBold",
            "withItalic",
            "withUnderlined",
            "withStrikethrough",
            "withObfuscated",
            "withClickEvent",
            "withHoverEvent",
            "withInsertion",
            "withFont",
            "applyFormat(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/Style;",
            "applyLegacyFormat",
            "applyFormats([Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/Style;"
    }, at = @At(value = "NEW", target = "(Lnet/minecraft/network/chat/TextColor;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/network/chat/ClickEvent;Lnet/minecraft/network/chat/HoverEvent;Ljava/lang/String;Lnet/minecraft/network/chat/FontDescription;)Lnet/minecraft/network/chat/Style;"))
    private Style zine$copyOutlineColor(Style original) {
        original.zine$setOutlineColor(this.outlineColor);
        return original;
    }

    @ModifyExpressionValue(method = "applyTo", at = @At(value = "NEW", target = "(Lnet/minecraft/network/chat/TextColor;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/network/chat/ClickEvent;Lnet/minecraft/network/chat/HoverEvent;Ljava/lang/String;Lnet/minecraft/network/chat/FontDescription;)Lnet/minecraft/network/chat/Style;"))
    private Style zine$parentOutlineColor(Style original, Style parent) {
        original.zine$setOutlineColor(parent.zine$getOutlineColor());
        return original;
    }

    @Inject(method = "toString", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
    private void zine$toString(CallbackInfoReturnable<String> cir, @Local(name = "result") StringBuilder result) {
        if(this.zine$hasOutline()) {
            return;
        }
        if(result.length() > 1) {
            result.append(',');
        }
        result.append("zine:outlineColor=");
        result.append(this.outlineColor);
    }

    @ModifyExpressionValue(method = "equals", at = @At(value = "INVOKE", target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z", ordinal = 5))
    private boolean zine$equals(boolean original, @Local(name = "style") Style style) {
        return original && Objects.equals(this.outlineColor, style.zine$getOutlineColor());
    }

    @WrapOperation(method = "hashCode", at = @At(value = "INVOKE", target = "Ljava/util/Objects;hash([Ljava/lang/Object;)I"))
    private int zine$hashCode(Object[] originalValues, Operation<Integer> original) {
        Object[] values = new Object[originalValues.length];
        System.arraycopy(originalValues, 0, values, 0, originalValues.length);
        values[values.length - 1] = this.outlineColor;
        return original.call((Object) values);
    }
}
