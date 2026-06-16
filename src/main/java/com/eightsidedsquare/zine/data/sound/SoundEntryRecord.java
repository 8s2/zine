package com.eightsidedsquare.zine.data.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.Nullable;

import java.util.List;

record SoundEntryRecord(List<SoundBuilder> sounds, boolean replace, @Nullable String subtitle) {
    public static final Codec<SoundEntryRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SoundBuilder.CODEC.listOf().fieldOf("sounds").forGetter(SoundEntryRecord::sounds),
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(SoundEntryRecord::replace),
            Codec.STRING.optionalFieldOf("subtitle", null).forGetter(SoundEntryRecord::subtitle)
    ).apply(instance, SoundEntryRecord::new));
}
