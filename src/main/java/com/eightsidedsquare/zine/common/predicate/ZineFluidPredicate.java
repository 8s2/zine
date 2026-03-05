package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ZineFluidPredicate {

    default void zine$setFluids(@Nullable HolderSet<Fluid> fluids) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addFluid(Fluid fluid) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addFluids(Collection<Fluid> fluids) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setState(@Nullable StatePropertiesPredicate state) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
