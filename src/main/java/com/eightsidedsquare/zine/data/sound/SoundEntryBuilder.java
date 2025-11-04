package com.eightsidedsquare.zine.data.sound;

import net.minecraft.sound.SoundEvent;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class SoundEntryBuilder {

    private final SoundEvent soundEvent;
    private final List<SoundBuilder> sounds = new ArrayList<>();
    private boolean replace = false;
    @Nullable
    private String subtitle = null;

    private SoundEntryBuilder(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    public static SoundEntryBuilder of(SoundEvent soundEvent) {
        return new SoundEntryBuilder(soundEvent);
    }

    public SoundEntryBuilder replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public SoundEntryBuilder subtitle(@Nullable String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public SoundEntryBuilder subtitle() {
        return this.subtitle(this.soundEvent.id().getPath());
    }

    public SoundEntryBuilder with(SoundBuilder sound) {
        this.sounds.add(sound);
        return this;
    }

    public SoundEntryBuilder with(SoundBuilder sound, Consumer<SoundBuilder> consumer) {
        consumer.accept(sound);
        this.sounds.add(sound);
        return this;
    }

    public SoundEntryBuilder with(String name) {
        return this.with(SoundBuilder.of(name));
    }

    public SoundEntryBuilder with(String name, Consumer<SoundBuilder> consumer) {
        return this.with(SoundBuilder.of(name), consumer);
    }

    public SoundEntryBuilder with(SoundEvent source) {
        return this.with(source.id().toString());
    }

    public SoundEntryBuilder with(SoundEvent source, Consumer<SoundBuilder> consumer) {
        return this.with(source.id().toString(), consumer);
    }

    public SoundEntryBuilder with() {
        return this.with(this.soundEvent);
    }

    public SoundEntryBuilder with(Consumer<SoundBuilder> consumer) {
        return this.with(this.soundEvent, consumer);
    }

    public SoundEntryBuilder withSuffixed(String name, String suffix) {
        return this.with(name + suffix);
    }

    public SoundEntryBuilder withSuffixed(String name, String suffix, Consumer<SoundBuilder> consumer) {
        return this.with(name + suffix, consumer);
    }

    public SoundEntryBuilder withSuffixed(SoundEvent source, String suffix) {
        return this.withSuffixed(source.id().toString(), suffix);
    }

    public SoundEntryBuilder withSuffixed(SoundEvent source, String suffix, Consumer<SoundBuilder> consumer) {
        return this.withSuffixed(source.id().toString(), suffix, consumer);
    }

    public SoundEntryBuilder withSuffixed(String suffix) {
        return this.withSuffixed(this.soundEvent, suffix);
    }

    public SoundEntryBuilder withSuffixed(String suffix, Consumer<SoundBuilder> consumer) {
        return this.withSuffixed(this.soundEvent, suffix, consumer);
    }

    private SoundEntryBuilder withVariants(IntConsumer consumer, int variants) {
        if(variants < 1) {
            return this;
        }
        for(int variant = 1; variant <= variants; variant++) {
            consumer.accept(variant);
        }
        return this;
    }

    public SoundEntryBuilder withVariants(String name, int variants) {
        return this.withVariants(variant -> this.withSuffixed(name, String.valueOf(variant)), variants);
    }

    public SoundEntryBuilder withVariants(String name, int variants, Consumer<SoundBuilder> consumer) {
        return this.withVariants(variant -> this.withSuffixed(name, String.valueOf(variant), consumer), variants);
    }

    public SoundEntryBuilder withVariants(SoundEvent source, int variants) {
        return this.withVariants(variant -> this.withSuffixed(source, String.valueOf(variant)), variants);
    }

    public SoundEntryBuilder withVariants(SoundEvent source, int variants, Consumer<SoundBuilder> consumer) {
        return this.withVariants(variant -> this.withSuffixed(source, String.valueOf(variant), consumer), variants);
    }

    public SoundEntryBuilder withVariants(int variants) {
        return this.withVariants(variant -> this.withSuffixed(String.valueOf(variant)), variants);
    }

    public SoundEntryBuilder withVariants(int variants, Consumer<SoundBuilder> consumer) {
        return this.withVariants(variant -> this.withSuffixed(String.valueOf(variant), consumer), variants);
    }

    public SoundEntryBuilder withEvent(SoundEvent event) {
        return this.with(SoundBuilder.of(event.id().toString(), SoundBuilder.Type.SOUND_EVENT));
    }

    public SoundEntryBuilder withEvent(SoundEvent event, Consumer<SoundBuilder> consumer) {
        return this.with(SoundBuilder.of(event.id().toString(), SoundBuilder.Type.SOUND_EVENT), consumer);
    }

    @Nullable
    private String getSubtitleName() {
        return this.subtitle == null ? null : "subtitles." + this.subtitle;
    }

    public void offerTo(SoundEntryConsumer soundEntryConsumer) {
        List<SoundBuilder> sounds = this.sounds.stream().toList();
        soundEntryConsumer.accept(this.soundEvent, new SoundEntryRecord(sounds, this.replace, this.getSubtitleName()));
    }
}
