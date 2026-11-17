package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public record ConstantUseOn(InteractionResult interactionResult) implements UseOnItemAction {
    public static final SyncedCodec<ConstantUseOn> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    InteractionResultSerialization.CODEC.fieldOf("interaction_result").forGetter(ConstantUseOn::interactionResult)
            ).apply(i, ConstantUseOn::new)),
            StreamCodec.composite(
                    InteractionResultSerialization.STREAM_CODEC,
                    ConstantUseOn::interactionResult,
                    ConstantUseOn::new
            )
    );

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return this.interactionResult;
    }

    @Override
    public SyncedCodec<? extends UseOnItemAction> type() {
        return TYPE;
    }
}
