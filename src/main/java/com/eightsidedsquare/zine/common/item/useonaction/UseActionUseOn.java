package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.item.useaction.ItemUseAction;
import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public record UseActionUseOn(ItemUseAction useAction, InteractionResult fallback) implements ItemUseOnAction {
    public static final SyncedCodec<UseActionUseOn> TYPE = SyncedCodec.create(
            ItemUseAction.CODEC.fieldOf("use_action"),
            ItemUseAction.STREAM_CODEC,
            UseActionUseOn::useAction,
            InteractionResultSerialization.CODEC.optionalFieldOf("fallback", InteractionResult.PASS),
            InteractionResultSerialization.STREAM_CODEC,
            UseActionUseOn::fallback,
            UseActionUseOn::new
    );

    public UseActionUseOn(ItemUseAction useAction) {
        this(useAction, InteractionResult.PASS);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null) {
            return this.useAction.use(context.getLevel(), context.getPlayer(), context.getHand());
        }
        return this.fallback;
    }

    @Override
    public SyncedCodec<? extends ItemUseOnAction> type() {
        return TYPE;
    }
}
