package com.eightsidedsquare.zine.mixin.client.gui;

import com.eightsidedsquare.zine.client.gui.TooltipSubmenuHandlerInitializationCallback;
import com.eightsidedsquare.zine.client.gui.TooltipSubmenuHandlerInitializationContextImpl;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {

    private HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void zine$init(CallbackInfo ci) {
        TooltipSubmenuHandlerInitializationCallback.EVENT.invoker()
                .addTooltipSubmenuHandlers(new TooltipSubmenuHandlerInitializationContextImpl((HandledScreen<?>) (Object) this));
    }
}
