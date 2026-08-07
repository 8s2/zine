package com.eightsidedsquare.zine.mixin.server;

import com.eightsidedsquare.zine.common.network.ClientboundBlockEntitySyncPayload;
import com.eightsidedsquare.zine.common.util.codec.DataHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.FriendlyByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChunkHolder.class)
public abstract class ChunkHolderMixin {
    @Shadow
    protected abstract void broadcast(List<ServerPlayer> players, Packet<?> packet);

    @Inject(method = "broadcastBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;getUpdatePacket()Lnet/minecraft/network/protocol/Packet;"), cancellable = true)
    private void zine$syncBlockEntityFromDataHelper(List<ServerPlayer> players, Level level, BlockPos blockPos, CallbackInfo ci, @Local(name = "blockEntity") BlockEntity blockEntity) {
        DataHelper<? extends BlockEntity> dataHelper = blockEntity.zine$dataHelper();
        if(dataHelper != null) {
            RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(FriendlyByteBufs.create(), level.registryAccess());
            dataHelper.writeUnchecked(buf, blockEntity);
            this.broadcast(
                    players,
                    new ClientboundCustomPayloadPacket(
                            new ClientboundBlockEntitySyncPayload(blockPos.immutable(), blockEntity.getType(), buf)
                    )
            );
            ci.cancel();
        }
    }
}
