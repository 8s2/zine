package com.eightsidedsquare.zine.common.world.structure;

import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;

import java.util.List;

public interface ZineListPoolElement {

    default List<StructurePoolElement> zine$getElements() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setElements(List<StructurePoolElement> elements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addElement(StructurePoolElement element) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addElements(List<StructurePoolElement> elements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
