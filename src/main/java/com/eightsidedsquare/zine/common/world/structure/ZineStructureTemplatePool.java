package com.eightsidedsquare.zine.common.world.structure;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.List;
import java.util.function.Function;

public interface ZineStructureTemplatePool {

    default ObjectArrayList<StructurePoolElement> zine$getElements() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default List<Pair<StructurePoolElement, Integer>> zine$getElementWeights() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addElement(StructurePoolElement element, int weight) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addElement(
            Function<StructureTemplatePool.Projection, ? extends StructurePoolElement> elementGetter,
            int weight,
            StructureTemplatePool.Projection projection) {
        this.zine$addElement(elementGetter.apply(projection), weight);
    }

    default void zine$setFallback(Holder<StructureTemplatePool> fallback) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
