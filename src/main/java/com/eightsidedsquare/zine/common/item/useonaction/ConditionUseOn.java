package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.item.usecondition.ItemUseCondition;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public record ConditionUseOn(ItemUseCondition condition, ItemUseOnAction onTrue, ItemUseOnAction onFalse) implements ItemUseOnAction {
    public static final SyncedCodec<ConditionUseOn> TYPE = SyncedCodec.create(
            ItemUseCondition.CODEC.fieldOf("condition"),
            ItemUseCondition.STREAM_CODEC,
            ConditionUseOn::condition,
            ItemUseOnAction.CODEC.fieldOf("on_true"),
            ItemUseOnAction.STREAM_CODEC,
            ConditionUseOn::onTrue,
            ItemUseOnAction.CODEC.fieldOf("on_false"),
            ItemUseOnAction.STREAM_CODEC,
            ConditionUseOn::onFalse,
            ConditionUseOn::new
    );

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (this.condition.test(context.getLevel(), context.getPlayer(), context.getItemInHand(), context::getHitResult)) {
            return this.onTrue.useOn(context);
        }
        return this.onFalse.useOn(context);
    }

    @Override
    public SyncedCodec<? extends ItemUseOnAction> type() {
        return TYPE;
    }
}
