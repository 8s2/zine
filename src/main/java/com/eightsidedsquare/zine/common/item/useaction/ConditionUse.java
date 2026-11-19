package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.util.ClipContextSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.google.common.base.Suppliers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public record ConditionUse(
        ItemUseCondition condition,
        ItemUseAction onTrue,
        ItemUseAction onFalse,
        ClipContext.Block blockShapeHandling,
        ClipContext.Fluid fluidShapeHandling
) implements ItemUseAction {
    public static final SyncedCodec<ConditionUse> TYPE = SyncedCodec.create(
            ItemUseCondition.CODEC.fieldOf("condition"),
            ItemUseCondition.STREAM_CODEC,
            ConditionUse::condition,
            ItemUseAction.CODEC.fieldOf("on_true"),
            ItemUseAction.STREAM_CODEC,
            ConditionUse::onTrue,
            ItemUseAction.CODEC.fieldOf("on_false"),
            ItemUseAction.STREAM_CODEC,
            ConditionUse::onFalse,
            ClipContextSerialization.BLOCK_CODEC.optionalFieldOf("block_shape_handling", ClipContext.Block.OUTLINE),
            ClipContextSerialization.BLOCK_STREAM_CODEC,
            ConditionUse::blockShapeHandling,
            ClipContextSerialization.FLUID_CODEC.optionalFieldOf("fluid_shape_handling", ClipContext.Fluid.NONE),
            ClipContextSerialization.FLUID_STREAM_CODEC,
            ConditionUse::fluidShapeHandling,
            ConditionUse::new
    );

    public ConditionUse(ItemUseCondition condition, ItemUseAction onTrue, ItemUseAction onFalse) {
        this(condition, onTrue, onFalse, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        Supplier<BlockHitResult> hitResultSupplier = Suppliers.memoize(() -> this.getHitResult(level, player));
        if (this.condition.test(level, player, player.getItemInHand(hand), hitResultSupplier)) {
            return this.onTrue.use(level, player, hand);
        }
        return this.onFalse.use(level, player, hand);
    }

    private BlockHitResult getHitResult(Level level, Player player) {
        return ItemUseAction.getHitResult(level, player, this.blockShapeHandling, this.fluidShapeHandling);
    }

    @Override
    public SyncedCodec<? extends ItemUseAction> type() {
        return TYPE;
    }
}
