package com.eightsidedsquare.zine.client.gui;

import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltipData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
    public void zine$cacheDimensions(Font font) {
        for (ClientTooltipComponent component : this.components) {
            component.zine$cacheDimensions(font);
        }
    }

    @Override
    public int getHeight(Font font) {
        return this.components.stream().mapToInt(component -> component.getHeight(font)).sum();
    }

    @Override
    public int getWidth(Font font) {
        return this.components.stream().mapToInt(component -> component.getWidth(font)).max().orElse(0);
    }

    @Override
    public void extractText(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        for (ClientTooltipComponent component : this.components) {
            component.extractText(graphics, font, x, y);
            y += component.getHeight(font);
        }
    }

    @Override
    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        for (ClientTooltipComponent component : this.components) {
            component.extractImage(font, x, y, w, h, graphics);
            y += component.getHeight(font);
        }
    }
}
