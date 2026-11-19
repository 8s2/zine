package com.eightsidedsquare.zine.common.item.useonaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public interface ItemUseOnAction extends SyncedCodec.Type<ItemUseOnAction> {
    Codec<ItemUseOnAction> CODEC = ZineBuiltinRegistries.ITEM_USE_ON_ACTION_TYPE.byNameCodec()
            .dispatch(ItemUseOnAction::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, ItemUseOnAction> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.ITEM_USE_ON_ACTION_TYPE)
            .dispatch(ItemUseOnAction::type, SyncedCodec::streamCodec);

    InteractionResult useOn(UseOnContext context);
}
