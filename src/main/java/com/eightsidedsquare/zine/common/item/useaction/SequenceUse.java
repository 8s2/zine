package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public record SequenceUse(List<UseItemAction> actions, InteractionResult fallback) implements UseItemAction {
    public static final SyncedCodec<SequenceUse> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    CodecUtil.nonEmptyListCodec(UseItemAction.CODEC).fieldOf("actions").forGetter(SequenceUse::actions),
                    InteractionResultSerialization.CODEC.optionalFieldOf("fallback", InteractionResult.PASS).forGetter(SequenceUse::fallback)
            ).apply(i, SequenceUse::new)),
            StreamCodec.composite(
                    UseItemAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    SequenceUse::actions,
                    InteractionResultSerialization.STREAM_CODEC,
                    SequenceUse::fallback,
                    SequenceUse::new
            )
    );

    public SequenceUse(List<UseItemAction> actions) {
        this(actions, InteractionResult.PASS);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        for (UseItemAction action : this.actions) {
            InteractionResult result = action.use(level, player, hand);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return this.fallback;
    }

    @Override
    public SyncedCodec<? extends UseItemAction> type() {
        return TYPE;
    }
}
