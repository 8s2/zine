package com.eightsidedsquare.zine.client.item;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public interface ZineUnbakedSpecialModelWrapper {

    default void zine$setBase(Identifier base) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setModel(SpecialModelRenderer.Unbaked<?> model) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTransformation(@Nullable Transformation transformation) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
