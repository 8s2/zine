package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public record UseActionUseOn(UseItemAction useAction, InteractionResult fallback) implements UseOnItemAction {
    public static final SyncedCodec<UseActionUseOn> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    UseItemAction.CODEC.fieldOf("use_action").forGetter(UseActionUseOn::useAction),
                    InteractionResultSerialization.CODEC.optionalFieldOf("fallback", InteractionResult.PASS)
                            .forGetter(UseActionUseOn::fallback)
            ).apply(i, UseActionUseOn::new)),
            StreamCodec.composite(
                    UseItemAction.STREAM_CODEC,
                    UseActionUseOn::useAction,
                    InteractionResultSerialization.STREAM_CODEC,
                    UseActionUseOn::fallback,
                    UseActionUseOn::new
            )
    );

    public UseActionUseOn(UseItemAction useAction) {
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
    public SyncedCodec<? extends UseOnItemAction> type() {
        return TYPE;
    }
}
