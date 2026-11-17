package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record ConstantUse(InteractionResult interactionResult) implements UseItemAction {
    public static final SyncedCodec<ConstantUse> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    InteractionResultSerialization.CODEC.fieldOf("interaction_result").forGetter(ConstantUse::interactionResult)
            ).apply(i, ConstantUse::new)),
            StreamCodec.composite(
                    InteractionResultSerialization.STREAM_CODEC,
                    ConstantUse::interactionResult,
                    ConstantUse::new
            )
    );

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return this.interactionResult;
    }

    @Override
    public SyncedCodec<? extends UseItemAction> type() {
        return TYPE;
    }
}
