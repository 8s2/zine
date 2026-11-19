package com.eightsidedsquare.zine.common.item.useduration;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ItemUseDuration extends SyncedCodec.Type<ItemUseDuration> {
    Codec<ItemUseDuration> CODEC = ZineBuiltinRegistries.ITEM_USE_DURATION_TYPE.byNameCodec()
            .dispatch(ItemUseDuration::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, ItemUseDuration> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.ITEM_USE_DURATION_TYPE)
            .dispatch(ItemUseDuration::type, SyncedCodec::streamCodec);

    int getUseDuration(ItemStack itemStack, LivingEntity user);
}
