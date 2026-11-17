package com.eightsidedsquare.zine.common.item.ondestroyedaction;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;

public record SequenceOnDestroyed(List<OnDestroyedItemAction> actions) implements OnDestroyedItemAction {
    public static final SyncedCodec<SequenceOnDestroyed> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    CodecUtil.nonEmptyListCodec(OnDestroyedItemAction.CODEC).fieldOf("actions").forGetter(SequenceOnDestroyed::actions)
            ).apply(i, SequenceOnDestroyed::new)),
            StreamCodec.composite(
                    OnDestroyedItemAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    SequenceOnDestroyed::actions,
                    SequenceOnDestroyed::new
            )
    );

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        for (OnDestroyedItemAction action : this.actions) {
            action.onDestroyed(itemEntity);
        }
    }

    @Override
    public SyncedCodec<? extends OnDestroyedItemAction> type() {
        return TYPE;
    }
}
