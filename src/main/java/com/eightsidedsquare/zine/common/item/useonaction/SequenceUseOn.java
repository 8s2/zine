package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.util.InteractionResultSerialization;
import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;

public record SequenceUseOn(List<ItemUseOnAction> actions, InteractionResult fallback) implements ItemUseOnAction {
    public static final SyncedCodec<SequenceUseOn> TYPE = SyncedCodec.create(
            CodecUtil.nonEmptyListCodec(ItemUseOnAction.CODEC).fieldOf("actions"),
            ItemUseOnAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SequenceUseOn::actions,
            InteractionResultSerialization.CODEC.optionalFieldOf("fallback", InteractionResult.PASS),
            InteractionResultSerialization.STREAM_CODEC,
            SequenceUseOn::fallback,
            SequenceUseOn::new
    );

    public SequenceUseOn(List<ItemUseOnAction> actions) {
        this(actions, InteractionResult.PASS);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        for (ItemUseOnAction action : this.actions) {
            InteractionResult result = action.useOn(context);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return this.fallback;
    }

    @Override
    public SyncedCodec<? extends ItemUseOnAction> type() {
        return TYPE;
    }
}
