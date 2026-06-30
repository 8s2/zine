package com.eightsidedsquare.zine.mixin.block;

import com.eightsidedsquare.zine.common.block.ZineBlockBehaviour;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin implements ZineBlockBehaviour {
    @Mixin(BlockBehaviour.BlockStateBase.class)
    public static abstract class BlockStateBaseMixin implements ZineBlockStateBase {
        @Shadow
        public abstract Block getBlock();

        @Shadow
        protected abstract BlockState asState();

        @Override
        public @Nullable VoxelShape zine$getClientOutlineShape(BlockGetter level, BlockHitResult hitResult, CollisionContext context) {
            return this.getBlock().zine$getClientOutlineShape(this.asState(), level, hitResult, context);
        }
    }
}
