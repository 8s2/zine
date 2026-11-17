package com.eightsidedsquare.zine.common.block;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface ModifyBlockSoundSetCallback {
    Event<ModifyBlockSoundSetCallback> EVENT = EventFactory.createArrayBacked(
            ModifyBlockSoundSetCallback.class,
            callbacks -> ctx -> {
                for (ModifyBlockSoundSetCallback callback : callbacks) {
                    callback.modify(ctx);
                }
            }
    );

    void modify(Context ctx);

    interface Context {
        void setSoundSet(Block block, @Nullable ResourceKey<BlockSoundSet> blockSoundSet);

        default void setSoundSet(@Nullable ResourceKey<BlockSoundSet> blockSoundSet, Block... blocks) {
            for (Block block : blocks) {
                this.setSoundSet(block, blockSoundSet);
            }
        }

        default Registry<Block> registry() {
            return BuiltInRegistries.BLOCK;
        }
    }
}
