package com.eightsidedsquare.zine.common.world.structure;

import net.minecraft.structure.processor.StructureProcessor;

import java.util.List;

public interface StructureProcessorListExtensions {

    default List<StructureProcessor> zine$getProcessors() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setProcessors(List<StructureProcessor> processors) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addProcessor(StructureProcessor processor) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addProcessors(List<StructureProcessor> processors) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
