package com.eightsidedsquare.zine.client.atlas;

import net.minecraft.util.Identifier;

public interface PalettedPermutationsAtlasSourceExtensions {

    default void zine$addNamespacedPermutation(String name, Identifier texture) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addPermutation(String name, Identifier texture) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addTexture(Identifier texture) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Identifier zine$getPaletteKey() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPaletteKey(Identifier paletteKey) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
