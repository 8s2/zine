package com.eightsidedsquare.zinetest.mixin.client;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancementTab.class)
public abstract class AdvancementTabMixin {

    @Expression("? <= 15")
    @ModifyExpressionValue(method = "drawContents", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean test(boolean original, @Local(name = "x") int x) {
//        System.out.println(x);
        return original;
    }

}
