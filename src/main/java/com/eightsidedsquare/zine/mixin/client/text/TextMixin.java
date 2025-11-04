package com.eightsidedsquare.zine.mixin.client.text;

import com.eightsidedsquare.zine.common.text.ZineText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(Text.class)
public interface TextMixin extends ZineText {
    @Override
    default List<Text> zine$wrap(int width) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        List<Text> lines = new ArrayList<>();
        StringBuilder[] stringBuilder = new StringBuilder[1];
        Style[] lastStyle = new Style[1];
        for (OrderedText orderedText : textRenderer.wrapLines((Text) this, width)) {
            stringBuilder[0] = new StringBuilder();
            lastStyle[0] = Style.EMPTY;
            MutableText mutableText = Text.empty();
            orderedText.accept((index, style, codePoint) -> {
                if(!style.equals(lastStyle[0])) {
                    if(!stringBuilder[0].isEmpty()) {
                        mutableText.append(Text.literal(stringBuilder[0].toString()).setStyle(lastStyle[0]));
                        stringBuilder[0] = new StringBuilder();
                    }
                    lastStyle[0] = style;
                }
                stringBuilder[0].appendCodePoint(codePoint);
                return true;
            });
            if(!stringBuilder[0].isEmpty()) {
                mutableText.append(Text.literal(stringBuilder[0].toString()).setStyle(lastStyle[0]));
            }
            lines.add(mutableText);
        }
        return lines;
    }
}
