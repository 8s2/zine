package com.eightsidedsquare.zine.common.world.structure;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public interface ZineSinglePoolElement {

    default void zine$setTemplate(Identifier template) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setTemplate(StructureTemplate template) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Either<Identifier, StructureTemplate> zine$getTemplate() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Holder<StructureProcessorList> zine$getProcessors() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setProcessors(Holder<StructureProcessorList> processors) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    @Nullable
    default LiquidSettings zine$getOverrideLiquidSettings() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setOverrideLiquidSettings(@Nullable LiquidSettings overrideLiquidSettings) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
