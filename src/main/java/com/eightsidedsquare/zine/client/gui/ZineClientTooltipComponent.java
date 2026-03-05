package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface ZineClientTooltipComponent {

    default void zine$cacheDimensions(Font textRenderer) {

    }

    default void zine$appendSearchableText(List<Component> texts) {

    }

}
