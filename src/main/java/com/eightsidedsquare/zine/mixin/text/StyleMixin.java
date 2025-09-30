package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.CustomStyleAttribute;
import com.eightsidedsquare.zine.common.text.CustomStyleAttributeContainer;
import com.eightsidedsquare.zine.common.text.ZineStyle;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.text.*;
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

    @Shadow @Final @Nullable TextColor color;
    @Shadow @Final @Nullable Integer shadowColor;
    @Shadow @Final @Nullable Boolean bold;
    @Shadow @Final @Nullable Boolean italic;
    @Shadow @Final @Nullable Boolean underlined;
    @Shadow @Final @Nullable Boolean strikethrough;
    @Shadow @Final @Nullable Boolean obfuscated;
    @Shadow @Final @Nullable ClickEvent clickEvent;
    @Shadow @Final @Nullable HoverEvent hoverEvent;
    @Shadow @Final @Nullable String insertion;
    @Shadow @Final @Nullable StyleSpriteSource font;
    @Unique
    private CustomStyleAttributeContainer custom = CustomStyleAttributeContainer.Empty.INSTANCE;

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
            @Nullable StyleSpriteSource font) {
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
        style.zine$setCustomAttributeContainer(this.custom);
        return style;
    }

    @Override
    public CustomStyleAttributeContainer zine$getCustomAttributeContainer() {
        return this.custom;
    }

    @Override
    public <T> @Nullable T zine$getCustomAttribute(CustomStyleAttribute<T> attribute) {
        return this.custom.get(attribute);
    }

    @Override
    public void zine$setCustomAttributeContainer(CustomStyleAttributeContainer container) {
        this.custom = container;
    }

    @Override
    public <T> Style zine$withCustomAttribute(CustomStyleAttribute<T> attribute, T value) {
        Style style = this.zine$copy();
        style.zine$setCustomAttributeContainer(this.custom.with(attribute, value));
        return style;
    }

    @Override
    public Style zine$withCustomAttributes(CustomStyleAttributeContainer attributes) {
        Style style = this.zine$copy();
        style.zine$setCustomAttributeContainer(this.custom.with(attributes));
        return style;
    }

    @Override
    public boolean zine$containsCustomAttribute(CustomStyleAttribute<?> attribute) {
        return this.custom.contains(attribute);
    }

    @ModifyExpressionValue(method = {
            "withColor(Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/Style;",
            "withShadowColor",
            "withBold",
            "withItalic",
            "withUnderline",
            "withStrikethrough",
            "withObfuscated",
            "withClickEvent",
            "withHoverEvent",
            "withInsertion",
            "withFont",
            "withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            "withExclusiveFormatting",
            "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;"
    }, at = @At(value = "NEW", target = "(Lnet/minecraft/text/TextColor;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/text/ClickEvent;Lnet/minecraft/text/HoverEvent;Ljava/lang/String;Lnet/minecraft/text/StyleSpriteSource;)Lnet/minecraft/text/Style;"))
    private Style zine$copyCustomAttributes(Style original) {
        original.zine$setCustomAttributeContainer(this.custom);
        return original;
    }

    @ModifyExpressionValue(method = "withParent", at = @At(value = "NEW", target = "(Lnet/minecraft/text/TextColor;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/text/ClickEvent;Lnet/minecraft/text/HoverEvent;Ljava/lang/String;Lnet/minecraft/text/StyleSpriteSource;)Lnet/minecraft/text/Style;"))
    private Style zine$parentCustomAttributes(Style original, Style parent) {
        original.zine$setCustomAttributeContainer(parent.zine$getCustomAttributeContainer().with(this.custom));
        return original;
    }

    @Inject(method = "toString", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
    private void zine$toString(CallbackInfoReturnable<String> cir, @Local StringBuilder stringBuilder) {
        if(this.custom.isEmpty()) {
            return;
        }
        if(stringBuilder.length() > 1) {
            stringBuilder.append(',');
        }
        stringBuilder.append("zine:custom=");
        stringBuilder.append(this.custom);
    }

    @ModifyExpressionValue(method = "equals", at = @At(value = "INVOKE", target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z", ordinal = 5))
    private boolean zine$equals(boolean original, @Local(ordinal = 1) Style style) {
        return original && Objects.equals(this.custom, style.zine$getCustomAttributeContainer());
    }

    @WrapOperation(method = "hashCode", at = @At(value = "INVOKE", target = "Ljava/util/Objects;hash([Ljava/lang/Object;)I"))
    private int zine$hashCode(Object[] originalValues, Operation<Integer> original) {
        Object[] values = new Object[originalValues.length];
        System.arraycopy(originalValues, 0, values, 0, originalValues.length);
        values[values.length - 1] = this.custom;
        return original.call((Object) values);
    }
}
