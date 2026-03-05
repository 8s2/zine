package com.eightsidedsquare.zine.client.gui;

import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltipData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

import java.util.List;

public record CompositeTooltipComponent(List<ClientTooltipComponent> components) implements ClientTooltipComponent {

    public CompositeTooltipComponent(CompositeTooltipData tooltipData) {
        this(
                tooltipData.data()
                        .stream()
                        .map(either ->
                                either.map(text -> ClientTooltipComponent.create(text.getVisualOrderText()), ClientTooltipComponent::create)
                        )
                        .toList()
        );
    }

    @Override
    public void zine$appendSearchableText(List<Component> texts) {
        for (ClientTooltipComponent component : this.components) {
            component.zine$appendSearchableText(texts);
        }
    }

    @Override
    public void zine$cacheDimensions(Font textRenderer) {
        for (ClientTooltipComponent component : this.components) {
            component.zine$cacheDimensions(textRenderer);
        }
    }

    @Override
    public int getHeight(Font textRenderer) {
        return this.components.stream().mapToInt(component -> component.getHeight(textRenderer)).sum();
    }

    @Override
    public int getWidth(Font textRenderer) {
        return this.components.stream().mapToInt(component -> component.getWidth(textRenderer)).max().orElse(0);
    }

    @Override
    public void renderText(GuiGraphics context, Font textRenderer, int x, int y) {
        for (ClientTooltipComponent component : this.components) {
            component.renderText(context, textRenderer, x, y);
            y += component.getHeight(textRenderer);
        }
    }

    @Override
    public void renderImage(Font textRenderer, int x, int y, int width, int height, GuiGraphics context) {
        for (ClientTooltipComponent component : this.components) {
            component.renderImage(textRenderer, x, y, width, height, context);
            y += component.getHeight(textRenderer);
        }
    }
}
