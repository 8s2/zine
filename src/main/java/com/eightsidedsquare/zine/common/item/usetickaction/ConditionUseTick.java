package com.eightsidedsquare.zine.common.item.usetickaction;

import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.util.ClipContextSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.google.common.base.Suppliers;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;
import java.util.function.Supplier;

public record ConditionUseTick(
        ItemUseCondition condition,
        Optional<ItemUseTickAction> onTrue,
        Optional<ItemUseTickAction> onFalse,
        ClipContext.Block blockShapeHandling,
        ClipContext.Fluid fluidShapeHandling
) implements ItemUseTickAction {
    public static final SyncedCodec<ConditionUseTick> TYPE = SyncedCodec.create(
            ItemUseCondition.CODEC.fieldOf("condition"),
            ItemUseCondition.STREAM_CODEC,
            ConditionUseTick::condition,
            ItemUseTickAction.CODEC.optionalFieldOf("on_true"),
            ItemUseTickAction.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ConditionUseTick::onTrue,
            ItemUseTickAction.CODEC.optionalFieldOf("on_false"),
            ItemUseTickAction.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ConditionUseTick::onFalse,
            ClipContextSerialization.BLOCK_CODEC.optionalFieldOf("block_shape_handling", ClipContext.Block.OUTLINE),
            ClipContextSerialization.BLOCK_STREAM_CODEC,
            ConditionUseTick::blockShapeHandling,
            ClipContextSerialization.FLUID_CODEC.optionalFieldOf("fluid_shape_handling", ClipContext.Fluid.NONE),
            ClipContextSerialization.FLUID_STREAM_CODEC,
            ConditionUseTick::fluidShapeHandling,
            ConditionUseTick::new
    );

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        Supplier<BlockHitResult> hitResultSupplier = Suppliers.memoize(() -> this.getHitResult(level, livingEntity));
        (this.condition.test(level, livingEntity, itemStack, hitResultSupplier) ? this.onTrue : this.onFalse)
                .ifPresent(action -> action.onUseTick(level, livingEntity, itemStack, ticksRemaining));
    }

    private BlockHitResult getHitResult(Level level, LivingEntity livingEntity) {
        return ItemUseTickAction.getHitResult(level, livingEntity, this.blockShapeHandling, this.fluidShapeHandling);
    }

    @Override
    public SyncedCodec<? extends ItemUseTickAction> type() {
        return TYPE;
    }
}
