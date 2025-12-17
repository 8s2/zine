package com.eightsidedsquare.zine.client.gui;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipSubmenuHandler;

public interface TooltipSubmenuHandlerInitializationCallback {

    Event<Callback> EVENT = EventFactory.createArrayBacked(
            Callback.class,
            callbacks -> ctx -> {
                for (Callback callback : callbacks) {
                    callback.addTooltipSubmenuHandlers(ctx);
                }
            }
    );

    interface Context {

        void accept(TooltipSubmenuHandler tooltipSubmenuHandler);

        HandledScreen<?> screen();

        MinecraftClient client();

    }

    @FunctionalInterface
    interface Callback {

        void addTooltipSubmenuHandlers(Context ctx);

    }

}
