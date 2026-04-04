package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ExtendedStyleMapCodec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Style.Serializer.class)
public abstract class StyleCodecsMixin {

    @Shadow @Final @Mutable
    public static MapCodec<Style> MAP_CODEC;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/network/chat/Style$Serializer;MAP_CODEC:Lcom/mojang/serialization/MapCodec;", shift = At.Shift.AFTER))
    private static void zine$modifyCodec(CallbackInfo ci) {
        MAP_CODEC = new ExtendedStyleMapCodec(MAP_CODEC);
    }

}
