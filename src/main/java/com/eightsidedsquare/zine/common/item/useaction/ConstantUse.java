package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record ConstantUse(InteractionResult interactionResult) implements ItemUseAction {
    public static final SyncedCodec<ConstantUse> TYPE = SyncedCodec.create(
            InteractionResultSerialization.CODEC.fieldOf("interaction_result"),
            InteractionResultSerialization.STREAM_CODEC,
            ConstantUse::interactionResult,
            ConstantUse::new
    );

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return this.interactionResult;
    }

    @Override
    public SyncedCodec<? extends ItemUseAction> type() {
        return TYPE;
    }
}
