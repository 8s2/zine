package com.eightsidedsquare.zine.mixin.client.gui;

import com.eightsidedsquare.zine.client.gui.TooltipSubmenuHandlerInitializationCallback;
import com.eightsidedsquare.zine.client.gui.TooltipSubmenuHandlerInitializationContextImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin extends Screen {

    private AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void zine$init(CallbackInfo ci) {
        TooltipSubmenuHandlerInitializationCallback.EVENT.invoker()
                .addTooltipSubmenuHandlers(new TooltipSubmenuHandlerInitializationContextImpl((AbstractContainerScreen<?>) (Object) this));
    }
}
