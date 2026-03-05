package com.eightsidedsquare.zine.mixin.text;

import com.eightsidedsquare.zine.common.text.ZineFormattedCharSequence;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FormattedCharSequence.class)
public interface FormattedCharSequenceMixin extends ZineFormattedCharSequence {

    @Shadow boolean accept(FormattedCharSink visitor);

    @Override
    default Component zine$toText() {
        StringBuilder[] stringBuilder = {new StringBuilder()};
        Style[] lastStyle = {Style.EMPTY};
        MutableComponent mutableText = Component.empty();
        this.accept((index, style, codePoint) -> {
            if(!style.equals(lastStyle[0])) {
                if(!stringBuilder[0].isEmpty()) {
                    mutableText.append(Component.literal(stringBuilder[0].toString()).setStyle(lastStyle[0]));
                    stringBuilder[0] = new StringBuilder();
                }
                lastStyle[0] = style;
            }
            stringBuilder[0].appendCodePoint(codePoint);
            return true;
        });
        if(!stringBuilder[0].isEmpty()) {
            mutableText.append(Component.literal(stringBuilder[0].toString()).setStyle(lastStyle[0]));
        }
        return mutableText;
    }
}
