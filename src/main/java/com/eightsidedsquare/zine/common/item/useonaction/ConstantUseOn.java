package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public record ConstantUseOn(InteractionResult interactionResult) implements ItemUseOnAction {
    public static final SyncedCodec<ConstantUseOn> TYPE = SyncedCodec.create(
            InteractionResultSerialization.CODEC.fieldOf("interaction_result"),
            InteractionResultSerialization.STREAM_CODEC,
            ConstantUseOn::interactionResult,
            ConstantUseOn::new
    );

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return this.interactionResult;
    }

    @Override
    public SyncedCodec<? extends ItemUseOnAction> type() {
        return TYPE;
    }
}
