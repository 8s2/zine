package com.eightsidedsquare.zine.client.gui;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public interface ItemSlotMouseActionInitializationCallback {

    Event<Callback> EVENT = EventFactory.createArrayBacked(
            Callback.class,
            callbacks -> ctx -> {
                for (Callback callback : callbacks) {
                    callback.addItemSlotMouseActions(ctx);
                }
            }
    );

    interface Context {

        void accept(ItemSlotMouseAction itemSlotMouseAction);

        AbstractContainerScreen<?> screen();

        Minecraft client();

    }

    @FunctionalInterface
    interface Callback {

        void addItemSlotMouseActions(Context ctx);

    }

}
