package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.advancement.VanillaAdvancementModificationsImpl;
import com.eightsidedsquare.zine.common.block.ModifyBlockSoundGroupContextImpl;
import com.eightsidedsquare.zine.common.entity.spawn.*;
import com.eightsidedsquare.zine.common.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class ZineMod implements ModInitializer {

    public static final String MOD_ID = "zine";
    static final RegistryHelper REGISTRY = RegistryHelper.create(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ZineDataComponents.init();

        VanillaAdvancementModificationsImpl.registerEvents();

        ModifyBlockSoundGroupContextImpl.registerEvents();

        REGISTRY.spawnCondition("all_of", AllOfCheck.CODEC);
        REGISTRY.spawnCondition("any_of", AnyOfCheck.CODEC);
        REGISTRY.spawnCondition("attribute", AttributeCheck.CODEC);
        REGISTRY.spawnCondition("dimension", DimensionCheck.CODEC);
        REGISTRY.spawnCondition("inverted", InvertedCheck.CODEC);
        REGISTRY.spawnCondition("noise", NoiseCheck.CODEC);
        REGISTRY.spawnCondition("random", RandomCheck.CODEC);
        REGISTRY.spawnCondition("spawn_reason", SpawnReasonCheck.CODEC);
    }
}
