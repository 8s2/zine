package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public record ConditionUseOn(ItemUseCondition condition, UseOnItemAction onTrue, UseOnItemAction onFalse) implements UseOnItemAction {
    public static final SyncedCodec<ConditionUseOn> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    ItemUseCondition.CODEC.fieldOf("condition").forGetter(ConditionUseOn::condition),
                    UseOnItemAction.CODEC.fieldOf("on_true").forGetter(ConditionUseOn::onTrue),
                    UseOnItemAction.CODEC.fieldOf("on_false").forGetter(ConditionUseOn::onFalse)
            ).apply(i, ConditionUseOn::new)),
            StreamCodec.composite(
                    ItemUseCondition.STREAM_CODEC,
                    ConditionUseOn::condition,
                    UseOnItemAction.STREAM_CODEC,
                    ConditionUseOn::onTrue,
                    UseOnItemAction.STREAM_CODEC,
                    ConditionUseOn::onFalse,
                    ConditionUseOn::new
            )
    );

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (this.condition.test(context.getLevel(), context.getPlayer(), context.getItemInHand(), context::getHitResult)) {
            return this.onTrue.useOn(context);
        }
        return this.onFalse.useOn(context);
    }

    @Override
    public SyncedCodec<? extends UseOnItemAction> type() {
        return TYPE;
    }
}
