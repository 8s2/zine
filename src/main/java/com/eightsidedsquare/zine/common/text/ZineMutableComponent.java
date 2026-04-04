package com.eightsidedsquare.zine.common.text;

import net.minecraft.network.chat.MutableComponent;

public interface ZineMutableComponent {

    default MutableComponent zine$freeze() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default MutableComponent zine$unfreeze() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default MutableComponent zine$withOutlineColor(int outlineColor) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
