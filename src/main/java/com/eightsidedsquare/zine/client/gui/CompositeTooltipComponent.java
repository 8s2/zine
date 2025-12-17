package com.eightsidedsquare.zine.client.gui;

import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.Text;

import java.util.List;

public record CompositeTooltipComponent(List<TooltipComponent> components) implements TooltipComponent {

    public CompositeTooltipComponent(CompositeTooltipData tooltipData) {
        this(
                tooltipData.data()
                        .stream()
                        .map(either ->
                                either.map(text -> TooltipComponent.of(text.asOrderedText()), TooltipComponent::of)
                        )
                        .toList()
        );
    }

    @Override
    public void zine$appendSearchableText(List<Text> texts) {
        for (TooltipComponent component : this.components) {
            component.zine$appendSearchableText(texts);
        }
    }

    @Override
    public void zine$cacheDimensions(TextRenderer textRenderer) {
        for (TooltipComponent component : this.components) {
            component.zine$cacheDimensions(textRenderer);
        }
    }

    @Override
    public int getHeight(TextRenderer textRenderer) {
        return this.components.stream().mapToInt(component -> component.getHeight(textRenderer)).sum();
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return this.components.stream().mapToInt(component -> component.getWidth(textRenderer)).max().orElse(0);
    }

    @Override
    public void drawText(DrawContext context, TextRenderer textRenderer, int x, int y) {
        for (TooltipComponent component : this.components) {
            component.drawText(context, textRenderer, x, y);
            y += component.getHeight(textRenderer);
        }
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
        for (TooltipComponent component : this.components) {
            component.drawItems(textRenderer, x, y, width, height, context);
            y += component.getHeight(textRenderer);
        }
    }
}
