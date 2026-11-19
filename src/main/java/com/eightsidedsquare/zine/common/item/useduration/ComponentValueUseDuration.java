package com.eightsidedsquare.zine.common.item.useduration;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record ComponentValueUseDuration(DataComponentType<?> componentType, ItemUseDuration fallback) implements ItemUseDuration {
    public static final SyncedCodec<ComponentValueUseDuration> TYPE = SyncedCodec.create(
            DataComponentType.CODEC.fieldOf("component_type"),
            DataComponentType.STREAM_CODEC,
            ComponentValueUseDuration::componentType,
            ItemUseDuration.CODEC.fieldOf("fallback"),
            ItemUseDuration.STREAM_CODEC,
            ComponentValueUseDuration::fallback,
            ComponentValueUseDuration::new
    );

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return itemStack.get(this.componentType) instanceof Number number ? number.intValue() : this.fallback.getUseDuration(itemStack, user);
    }

    @Override
    public SyncedCodec<? extends ItemUseDuration> type() {
        return TYPE;
    }
}
