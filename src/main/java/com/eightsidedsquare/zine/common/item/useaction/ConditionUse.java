package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.util.ClipContextSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.google.common.base.Suppliers;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public record ConditionUse(
        ItemUseCondition condition,
        UseItemAction onTrue,
        UseItemAction onFalse,
        ClipContext.Block blockShapeHandling,
        ClipContext.Fluid fluidShapeHandling
) implements UseItemAction {
    public static final SyncedCodec<ConditionUse> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    ItemUseCondition.CODEC.fieldOf("condition").forGetter(ConditionUse::condition),
                    UseItemAction.CODEC.fieldOf("on_true").forGetter(ConditionUse::onTrue),
                    UseItemAction.CODEC.fieldOf("on_false").forGetter(ConditionUse::onFalse),
                    ClipContextSerialization.BLOCK_CODEC.optionalFieldOf("block_shape_handling", ClipContext.Block.OUTLINE)
                            .forGetter(ConditionUse::blockShapeHandling),
                    ClipContextSerialization.FLUID_CODEC.optionalFieldOf("fluid_shape_handling", ClipContext.Fluid.NONE)
                            .forGetter(ConditionUse::fluidShapeHandling)
            ).apply(i, ConditionUse::new)),
            StreamCodec.composite(
                    ItemUseCondition.STREAM_CODEC,
                    ConditionUse::condition,
                    UseItemAction.STREAM_CODEC,
                    ConditionUse::onTrue,
                    UseItemAction.STREAM_CODEC,
                    ConditionUse::onFalse,
                    ClipContextSerialization.BLOCK_STREAM_CODEC,
                    ConditionUse::blockShapeHandling,
                    ClipContextSerialization.FLUID_STREAM_CODEC,
                    ConditionUse::fluidShapeHandling,
                    ConditionUse::new
            )
    );

    public ConditionUse(ItemUseCondition condition, UseItemAction onTrue, UseItemAction onFalse) {
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
        return UseItemAction.getHitResult(level, player, this.blockShapeHandling, this.fluidShapeHandling);
    }

    @Override
    public SyncedCodec<? extends UseItemAction> type() {
        return TYPE;
    }
}
