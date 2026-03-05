package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;

import java.util.List;

public interface ZineDataComponentExactPredicate {

    default void zine$setComponents(List<TypedDataComponent<?>> components) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addComponent(TypedDataComponent<?> component) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T> void zine$addComponent(DataComponentType<T> type, T value) {
        this.zine$addComponent(new TypedDataComponent<>(type, value));
    }

    default void zine$addComponents(List<TypedDataComponent<?>> components) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
