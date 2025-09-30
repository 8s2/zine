package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.TextUtilImpl;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.text.TextContent;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextCodecs.class)
public abstract class TextCodecsMixin {

    @Inject(method = "createCodec", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TextCodecs;dispatchingCodec(Lnet/minecraft/util/dynamic/Codecs$IdMapper;Ljava/util/function/Function;Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;"))
    private static void zine$captureTextContentIds(Codec<Text> selfCodec, CallbackInfoReturnable<Codec<Text>> cir, @Local Codecs.IdMapper<String, MapCodec<? extends TextContent>> idMapper) {
        TextUtilImpl.textContentIds = idMapper;
    }

}
