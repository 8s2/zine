package com.eightsidedsquare.zine.data.sound;

import net.minecraft.sounds.SoundEvent;

public interface SoundEntryConsumer {
    void accept(SoundEvent soundEvent, SoundEntryRecord soundEntry);
}
