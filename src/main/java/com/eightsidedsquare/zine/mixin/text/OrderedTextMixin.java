package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ZineOrderedText;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OrderedText.class)
public interface OrderedTextMixin extends ZineOrderedText {

    @Shadow boolean accept(CharacterVisitor visitor);

    @Override
    default Text zine$toText() {
        StringBuilder[] stringBuilder = {new StringBuilder()};
        Style[] lastStyle = {Style.EMPTY};
        MutableText mutableText = Text.empty();
        this.accept((index, style, codePoint) -> {
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
        return mutableText;
    }
}
