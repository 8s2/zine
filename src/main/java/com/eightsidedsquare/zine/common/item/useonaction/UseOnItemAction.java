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

public interface UseOnItemAction extends SyncedCodec.Type<UseOnItemAction> {
    Codec<UseOnItemAction> CODEC = ZineBuiltinRegistries.USE_ON_ITEM_ACTION.byNameCodec()
            .dispatch(UseOnItemAction::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, UseOnItemAction> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.USE_ON_ITEM_ACTION)
            .dispatch(UseOnItemAction::type, SyncedCodec::streamCodec);

    InteractionResult useOn(UseOnContext context);
}
