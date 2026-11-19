package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public record SequenceUse(List<ItemUseAction> actions, InteractionResult fallback) implements ItemUseAction {
    public static final SyncedCodec<SequenceUse> TYPE = SyncedCodec.create(
            CodecUtil.nonEmptyListCodec(ItemUseAction.CODEC).fieldOf("actions"),
            ItemUseAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SequenceUse::actions,
            InteractionResultSerialization.CODEC.optionalFieldOf("fallback", InteractionResult.PASS),
            InteractionResultSerialization.STREAM_CODEC,
            SequenceUse::fallback,
            SequenceUse::new
    );

    public SequenceUse(List<ItemUseAction> actions) {
        this(actions, InteractionResult.PASS);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        for (ItemUseAction action : this.actions) {
            InteractionResult result = action.use(level, player, hand);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return this.fallback;
    }

    @Override
    public SyncedCodec<? extends ItemUseAction> type() {
        return TYPE;
    }
}
