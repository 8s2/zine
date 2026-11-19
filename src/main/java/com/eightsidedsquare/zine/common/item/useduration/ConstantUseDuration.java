package com.eightsidedsquare.zine.common.item.useduration;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record ConstantUseDuration(int duration) implements ItemUseDuration {
    public static final SyncedCodec<ConstantUseDuration> TYPE = SyncedCodec.create(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("duration"),
            ByteBufCodecs.VAR_INT,
            ConstantUseDuration::duration,
            ConstantUseDuration::new
    );

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return this.duration;
    }

    @Override
    public SyncedCodec<? extends ItemUseDuration> type() {
        return TYPE;
    }
}
