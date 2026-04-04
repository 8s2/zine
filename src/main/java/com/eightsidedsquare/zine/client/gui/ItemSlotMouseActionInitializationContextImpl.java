package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public record ItemSlotMouseActionInitializationContextImpl(AbstractContainerScreen<?> screen) implements ItemSlotMouseActionInitializationCallback.Context {

    @Override
    public void accept(ItemSlotMouseAction itemSlotMouseAction) {
        this.screen.addItemSlotMouseAction(itemSlotMouseAction);
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
