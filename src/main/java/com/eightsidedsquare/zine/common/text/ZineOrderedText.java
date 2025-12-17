package com.eightsidedsquare.zine.common.text;

import net.minecraft.text.Text;

public interface ZineOrderedText {

    default Text zine$toText() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
