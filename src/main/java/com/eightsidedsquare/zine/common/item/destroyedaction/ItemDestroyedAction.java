package com.eightsidedsquare.zine.common.item.destroyedaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.item.ItemEntity;

public interface ItemDestroyedAction extends SyncedCodec.Type<ItemDestroyedAction> {
    Codec<ItemDestroyedAction> CODEC = ZineBuiltinRegistries.ITEM_DESTROYED_ACTION_TYPE.byNameCodec()
            .dispatch(ItemDestroyedAction::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, ItemDestroyedAction> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.ITEM_DESTROYED_ACTION_TYPE)
            .dispatch(ItemDestroyedAction::type, SyncedCodec::streamCodec);

    void onDestroyed(ItemEntity itemEntity);
}
