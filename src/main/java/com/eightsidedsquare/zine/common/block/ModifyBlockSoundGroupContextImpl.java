package com.eightsidedsquare.zine.common.block;

import com.eightsidedsquare.zine.common.registry.FreezeRegistriesEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public final class ModifyBlockSoundGroupContextImpl implements ModifyBlockSoundGroupCallback.Context {

    public static void registerEvents() {
        FreezeRegistriesEvents.beforeFreeze(Registries.BLOCK)
                .register(registry ->
                        ModifyBlockSoundGroupCallback.EVENT.invoker().modify(new ModifyBlockSoundGroupContextImpl())
                );
    }

    @Override
    public void setSoundGroup(Block block, SoundType soundGroup) {
        block.soundType = soundGroup;
    }

    private ModifyBlockSoundGroupContextImpl() {
    }
}
