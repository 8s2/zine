package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public record TooltipSubmenuHandlerInitializationContextImpl(AbstractContainerScreen<?> screen) implements TooltipSubmenuHandlerInitializationCallback.Context {

    @Override
    public void accept(ItemSlotMouseAction handler) {
        this.screen.addItemSlotMouseAction(handler);
    }

    @Override
    public AbstractContainerScreen<?> screen() {
        return this.screen;
    }

    @Override
    public Minecraft client() {
        return this.screen.minecraft;
    }
}
