package com.eightsidedsquare.zine.common.block;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

@FunctionalInterface
public interface ModifyBlockSoundGroupCallback {

    Event<ModifyBlockSoundGroupCallback> EVENT = EventFactory.createArrayBacked(
            ModifyBlockSoundGroupCallback.class,
            callbacks -> ctx -> {
                for (ModifyBlockSoundGroupCallback callback : callbacks) {
                    callback.modify(ctx);
                }
            }
    );

    void modify(Context ctx);

    interface Context {

        void setSoundGroup(Block block, SoundType soundGroup);

        default void setSoundGroup(SoundType soundGroup, Block... blocks) {
            for (Block block : blocks) {
                this.setSoundGroup(block, soundGroup);
            }
        }

        default Registry<Block> registry() {
            return BuiltInRegistries.BLOCK;
        }

    }

}
