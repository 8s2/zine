package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class StartUsingUse implements ItemUseAction {
    public static final StartUsingUse INSTANCE = new StartUsingUse();
    public static final SyncedCodec<StartUsingUse> TYPE = SyncedCodec.unit(INSTANCE);

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public SyncedCodec<? extends ItemUseAction> type() {
        return TYPE;
    }
}
