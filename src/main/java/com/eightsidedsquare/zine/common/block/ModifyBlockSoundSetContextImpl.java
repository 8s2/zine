package com.eightsidedsquare.zine.common.block;

import com.eightsidedsquare.zine.common.registry.FreezeRegistriesEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public final class ModifyBlockSoundSetContextImpl implements ModifyBlockSoundSetCallback.Context {
    public static void registerEvents() {
        FreezeRegistriesEvents.beforeFreeze(Registries.BLOCK)
                .register(_ ->
                        ModifyBlockSoundSetCallback.EVENT.invoker().modify(new ModifyBlockSoundSetContextImpl())
                );
    }

    @Override
    public void setSoundSet(Block block, @Nullable ResourceKey<BlockSoundSet> blockSoundSet) {
        block.blockSoundSet = Optional.ofNullable(blockSoundSet);
    }

    private ModifyBlockSoundSetContextImpl() {
    }
}
