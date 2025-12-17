package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipSubmenuHandler;

public record TooltipSubmenuHandlerInitializationContextImpl(HandledScreen<?> screen) implements TooltipSubmenuHandlerInitializationCallback.Context {

    @Override
    public void accept(TooltipSubmenuHandler handler) {
        this.screen.addTooltipSubmenuHandler(handler);
    }

    @Override
    public HandledScreen<?> screen() {
        return this.screen;
    }

    @Override
    public MinecraftClient client() {
        return this.screen.client;
    }
}
