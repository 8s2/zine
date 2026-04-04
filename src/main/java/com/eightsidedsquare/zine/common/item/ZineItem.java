package com.eightsidedsquare.zine.common.item;

import net.minecraft.resources.Identifier;

public interface ZineItem {

    default boolean zine$modelEquals(Identifier modelId) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
