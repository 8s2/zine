package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;

public record SequenceUseOn(List<UseOnItemAction> actions, InteractionResult fallback) implements UseOnItemAction {
    public static final SyncedCodec<SequenceUseOn> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    CodecUtil.nonEmptyListCodec(UseOnItemAction.CODEC).fieldOf("actions").forGetter(SequenceUseOn::actions),
                    InteractionResultSerialization.CODEC.optionalFieldOf("fallback", InteractionResult.PASS).forGetter(SequenceUseOn::fallback)
            ).apply(i, SequenceUseOn::new)),
            StreamCodec.composite(
                    UseOnItemAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    SequenceUseOn::actions,
                    InteractionResultSerialization.STREAM_CODEC,
                    SequenceUseOn::fallback,
                    SequenceUseOn::new
            )
    );

    public SequenceUseOn(List<UseOnItemAction> actions) {
        this(actions, InteractionResult.PASS);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        for (UseOnItemAction action : this.actions) {
            InteractionResult result = action.useOn(context);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return this.fallback;
    }

    @Override
    public SyncedCodec<? extends UseOnItemAction> type() {
        return TYPE;
    }
}
