package com.eightsidedsquare.zine.mixin.client.text;

import com.eightsidedsquare.zine.common.text.ZineText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(Text.class)
public interface TextMixin extends ZineText {
    @Override
    default List<Text> zine$wrap(int width) {
        return MinecraftClient.getInstance().textRenderer
                .wrapLines((Text) this, width)
                .stream()
                .map(OrderedText::zine$toText)
                .toList();
    }
}
