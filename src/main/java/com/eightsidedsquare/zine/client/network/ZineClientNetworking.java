package com.eightsidedsquare.zine.client.network;

import com.eightsidedsquare.zine.common.network.ClientboundBlockEntitySyncPayload;
import com.eightsidedsquare.zine.common.util.codec.DataHelper;
import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FriendlyByteBufs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;

public final class ZineClientNetworking {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ClientboundBlockEntitySyncPayload.TYPE, (payload, ctx) -> {
            Level level = ctx.player().level();
            level.getBlockEntity(payload.pos(), payload.blockEntityType()).ifPresent(blockEntity -> {
                DataHelper<? extends BlockEntity> dataHelper = blockEntity.zine$dataHelper();
                if(dataHelper != null) {
                    ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(blockEntity.problemPath(), LOGGER);
                    try {
                        RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(FriendlyByteBufs.create(), level.registryAccess());
                        buf.writeBytes(payload.data());
                        read(buf, dataHelper, blockEntity);
                    } catch (Throwable e) {
                        try {
                            reporter.close();
                        } catch (Throwable e2) {
                            e.addSuppressed(e2);
                        }

                        throw e;
                    }
                    reporter.close();
                }
            });
        });
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> void read(RegistryFriendlyByteBuf buf, DataHelper<T> dataHelper, BlockEntity blockEntity) {
        dataHelper.read(buf, (T) blockEntity);
    }

    private ZineClientNetworking() {
    }
}
