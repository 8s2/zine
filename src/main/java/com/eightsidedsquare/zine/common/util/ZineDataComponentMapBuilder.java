package com.eightsidedsquare.zine.common.util;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import org.jetbrains.annotations.Nullable;

public interface ZineDataComponentMapBuilder {

    @Nullable
    default <T> T zine$get(DataComponentType<T> type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T> DataComponentMap.Builder zine$add(TypedDataComponent<T> component) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T> DataComponentMap.Builder zine$remove(DataComponentType<T> type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
