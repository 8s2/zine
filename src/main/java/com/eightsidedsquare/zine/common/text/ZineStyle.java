package com.eightsidedsquare.zine.common.text;

import net.minecraft.text.Style;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface ZineStyle {

    default Style zine$copy() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default CustomStyleAttributeContainer zine$getCustomAttributeContainer() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    @Nullable
    default <T> T zine$getCustomAttribute(CustomStyleAttribute<T> attribute) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    @ApiStatus.Internal
    default void zine$setCustomAttributeContainer(CustomStyleAttributeContainer container) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Style zine$withCustomAttributes(CustomStyleAttributeContainer attributes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T> Style zine$withCustomAttribute(CustomStyleAttribute<T> attribute, T value) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default boolean zine$containsCustomAttribute(CustomStyleAttribute<?> attribute) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
