package com.eightsidedsquare.zine.common.block;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public interface ZineBlockBehaviour {
    @Nullable
    default VoxelShape zine$getClientOutlineShape(BlockState state, BlockGetter level, BlockHitResult hitResult, CollisionContext context) {
        return null;
    }

    interface ZineBlockStateBase {
        @Nullable
        default VoxelShape zine$getClientOutlineShape(BlockGetter level, BlockHitResult hitResult, CollisionContext context) {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }
    }
}
