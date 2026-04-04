package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zine.data.sound.SoundEntryConsumer;
import com.eightsidedsquare.zine.data.sound.SoundListProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

public class TestmodSoundListProvider extends SoundListProvider {

    public TestmodSoundListProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    protected void generate(SoundEntryConsumer consumer) {
    }
}
