package com.eightsidedsquare.zine.common.entity.spawn;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Collection;

public interface ZineStructureCheck {

    default void zine$setRequiredStructures(HolderSet<Structure> requiredStructures) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredStructure(Holder<Structure> requiredStructure) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredStructure(HolderGetter<Structure> structureLookup, ResourceKey<Structure> requiredStructure) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredStructures(HolderSet<Structure> requiredStructures) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredStructures(HolderGetter<Structure> structureLookup, Collection<ResourceKey<Structure>> requiredStructures) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
