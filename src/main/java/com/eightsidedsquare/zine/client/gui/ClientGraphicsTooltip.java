package com.eightsidedsquare.zine.client.gui;

import com.eightsidedsquare.zine.common.item.tooltip.GraphicsTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClientGraphicsTooltip implements ClientTooltipComponent {
    private final List<GraphicsTooltip.Text> texts;
    private final List<GraphicsTooltip.Sprite> sprites;
    private final List<GraphicsTooltip.Rectangle> rectangles;
    private int width;
    private int height;

    public ClientGraphicsTooltip(List<GraphicsTooltip.Text> texts, List<GraphicsTooltip.Sprite> sprites, List<GraphicsTooltip.Rectangle> rectangles) {
        this.texts = texts;
        this.sprites = sprites;
        this.rectangles = rectangles;
    }

    public ClientGraphicsTooltip(GraphicsTooltip tooltip) {
        this(tooltip.texts(), tooltip.sprites(), tooltip.rectangles());
    }

    @Override
    public void zine$cacheDimensions(Font font) {
        this.width = 0;
        this.height = 0;
        for (GraphicsTooltip.Text text : this.texts) {
            int textWidth;
            int textHeight;
            if (text.maxWidth() > 0) {
                textWidth = Math.min(text.maxWidth(), font.width(text.text()));
                textHeight = font.wordWrapHeight(text.text(), text.maxWidth());
            } else {
                textWidth = font.width(text.text());
                textHeight = font.lineHeight;
            }
            this.width = Math.max(text.x() + textWidth, this.width);
            this.height = Math.max(text.y() + textHeight, this.height);
        }
        for (GraphicsTooltip.Sprite sprite : this.sprites) {
            this.width = Math.max(sprite.x() + sprite.width(), this.width);
            this.height = Math.max(sprite.y() + sprite.height(), this.height);
        }
        for (GraphicsTooltip.Rectangle rectangle : this.rectangles) {
            this.width = Math.max(rectangle.x() + rectangle.width(), this.width);
            this.height = Math.max(rectangle.y() + rectangle.height(), this.height);
        }
    }

    @Override
    public int getHeight(Font font) {
        return this.height;
    }

    @Override
    public int getWidth(Font font) {
        return this.width;
    }

    @Override
    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        for (GraphicsTooltip.Text text : this.texts) {
            if (text.maxWidth() > 0) {
                graphics.textWithWordWrap(font, text.text(), x + text.x(), y + text.y(), text.maxWidth(), -1);
            } else {
                graphics.text(font, text.text(), x + text.x(), y + text.y(), -1);
            }
        }
        for (GraphicsTooltip.Sprite sprite : this.sprites) {
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    sprite.sprite(),
                    x + sprite.x(),
                    y + sprite.y(),
                    sprite.width(),
                    sprite.height(),
                    sprite.color()
            );
        }
        for (GraphicsTooltip.Rectangle rectangle : this.rectangles) {
            graphics.fillGradient(
                    x + rectangle.x(),
                    y + rectangle.y(),
                    x + rectangle.x() + rectangle.width(),
                    y + rectangle.y() + rectangle.height(),
                    rectangle.fromColor(),
                    rectangle.toColor()
            );
        }
    }

    @Override
    public void zine$appendSearchableText(List<Component> texts) {
        for (GraphicsTooltip.Text text : this.texts) {
            texts.add(text.text());
        }
    }
}
