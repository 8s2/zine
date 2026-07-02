package com.eightsidedsquare.zinetest.common.block;

import com.eightsidedsquare.zinetest.common.block.entity.NestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class NestBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Block.column(10, 0, 6);

    public NestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape zine$getClientOutlineShape(BlockState state, BlockGetter level, BlockHitResult hitResult, CollisionContext context) {
        return context.isDescending() ? Shapes.block() : SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new NestBlockEntity(worldPosition, blockState);
    }
}
