package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.advancement.VanillaAdvancementModificationsImpl;
import com.eightsidedsquare.zine.common.block.ModifyBlockSoundGroupContextImpl;
import com.eightsidedsquare.zine.common.entity.spawn.DimensionSpawnCondition;
import com.eightsidedsquare.zine.common.entity.spawn.NoiseSpawnCondition;
import com.eightsidedsquare.zine.common.entity.spawn.RandomSpawnCondition;
import com.eightsidedsquare.zine.common.entity.spawn.SpawnReasonSpawnCondition;
import com.eightsidedsquare.zine.common.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ZineMod implements ModInitializer {

    public static final String MOD_ID = "zine";
    static final RegistryHelper REGISTRY = RegistryHelper.create(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ZineRegistries.init();
        ZineDataComponentTypes.init();
        ZineCustomStyleAttributes.init();

        VanillaAdvancementModificationsImpl.registerEvents();

        ModifyBlockSoundGroupContextImpl.registerEvents();

        REGISTRY.spawnCondition("dimension", DimensionSpawnCondition.CODEC);
        REGISTRY.spawnCondition("noise", NoiseSpawnCondition.CODEC);
        REGISTRY.spawnCondition("random", RandomSpawnCondition.CODEC);
        REGISTRY.spawnCondition("spawn_reason", SpawnReasonSpawnCondition.CODEC);
    }
}
