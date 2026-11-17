package com.eightsidedsquare.zine.common.item.ondestroyedaction;

import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.item.ItemEntity;

public interface OnDestroyedItemAction extends SyncedCodec.Type<OnDestroyedItemAction> {
    Codec<OnDestroyedItemAction> CODEC = ZineBuiltinRegistries.ON_DESTROYED_ITEM_ACTION.byNameCodec()
            .dispatch(OnDestroyedItemAction::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, OnDestroyedItemAction> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.ON_DESTROYED_ITEM_ACTION)
            .dispatch(OnDestroyedItemAction::type, SyncedCodec::streamCodec);

    void onDestroyed(ItemEntity itemEntity);
}
