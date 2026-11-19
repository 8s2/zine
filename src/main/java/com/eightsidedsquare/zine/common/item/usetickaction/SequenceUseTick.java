package com.eightsidedsquare.zine.common.item.usetickaction;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public record SequenceUseTick(List<ItemUseTickAction> actions) implements ItemUseTickAction {
    public static SyncedCodec<SequenceUseTick> TYPE = SyncedCodec.create(
            CodecUtil.nonEmptyListCodec(ItemUseTickAction.CODEC).fieldOf("actions"),
            ItemUseTickAction.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SequenceUseTick::actions,
            SequenceUseTick::new
    );

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        for (ItemUseTickAction action : this.actions) {
            action.onUseTick(level, livingEntity, itemStack, ticksRemaining);
        }
    }

    @Override
    public SyncedCodec<? extends ItemUseTickAction> type() {
        return TYPE;
    }
}
