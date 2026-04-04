package com.eightsidedsquare.zine.common.text;

import net.minecraft.network.chat.Component;

public interface ZineFormattedCharSequence {

    default Component zine$toComponent() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
