package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.NbtPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ZineBlockPredicate {

    default void zine$setBlocks(@Nullable HolderSet<Block> blocks) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addBlock(Block block) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addBlocks(Collection<Block> blocks) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setState(@Nullable StatePropertiesPredicate state) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setNbt(@Nullable NbtPredicate nbt) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
