package com.eightsidedsquare.zine.common.network;

import com.eightsidedsquare.zine.core.ZineMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntityType;

public record ClientboundBlockEntitySyncPayload(BlockPos pos, BlockEntityType<?> blockEntityType, byte[] data) implements CustomPacketPayload {
    public static final Type<ClientboundBlockEntitySyncPayload> TYPE = new Type<>(ZineMod.id("block_entity_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundBlockEntitySyncPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ClientboundBlockEntitySyncPayload::pos,
            ByteBufCodecs.registry(Registries.BLOCK_ENTITY_TYPE),
            ClientboundBlockEntitySyncPayload::blockEntityType,
            ByteBufCodecs.BYTE_ARRAY,
            ClientboundBlockEntitySyncPayload::data,
            ClientboundBlockEntitySyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
