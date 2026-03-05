package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.TextUtilImpl;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComponentSerialization.class)
public abstract class ComponentSerializationMixin {

    @Inject(method = "createCodec", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/ComponentSerialization;createLegacyComponentMatcher(Lnet/minecraft/util/ExtraCodecs$LateBoundIdMapper;Ljava/util/function/Function;Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;"))
    private static void zine$captureTextContentIds(Codec<Component> selfCodec, CallbackInfoReturnable<Codec<Component>> cir, @Local ExtraCodecs.LateBoundIdMapper<String, MapCodec<? extends ComponentContents>> idMapper) {
        TextUtilImpl.textContentIds = idMapper;
    }

}
