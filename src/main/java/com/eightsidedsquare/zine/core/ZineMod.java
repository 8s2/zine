package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.block.ModifyBlockSoundGroupContextImpl;
import com.eightsidedsquare.zine.common.network.ClientboundBlockEntitySyncPayload;
import com.eightsidedsquare.zine.common.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;

public class ZineMod implements ModInitializer {
    public static final String MOD_ID = "zine";
    static final RegistryHelper REGISTRY = RegistryHelper.create(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ZineBuiltinRegistries.init();
        ZineTooltipImages.init();
        ZineDataComponents.init();
        ZineSpawnConditions.init();

        ModifyBlockSoundGroupContextImpl.registerEvents();

        PayloadTypeRegistry.clientboundPlay()
                .register(ClientboundBlockEntitySyncPayload.TYPE, ClientboundBlockEntitySyncPayload.STREAM_CODEC);
    }
}
