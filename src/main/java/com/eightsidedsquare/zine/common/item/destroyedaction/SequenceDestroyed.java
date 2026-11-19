package com.eightsidedsquare.zine.common.item.destroyedaction;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;

public record SequenceDestroyed(List<ItemDestroyedAction> actions) implements ItemDestroyedAction {
    public static final SyncedCodec<SequenceDestroyed> TYPE = SyncedCodec.create(
            CodecUtil.nonEmptyListCodec(ItemDestroyedAction.CODEC).fieldOf("actions"),
            ItemDestroyedAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SequenceDestroyed::actions,
            SequenceDestroyed::new
    );

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        for (ItemDestroyedAction action : this.actions) {
            action.onDestroyed(itemEntity);
        }
    }

    @Override
    public SyncedCodec<? extends ItemDestroyedAction> type() {
        return TYPE;
    }
}
