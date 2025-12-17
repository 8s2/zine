package com.eightsidedsquare.zine.client.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

import java.util.List;

public interface ZineTooltipComponent {

    default void zine$cacheDimensions(TextRenderer textRenderer) {

    }

    default void zine$appendSearchableText(List<Text> texts) {

    }

}
