package com.eightsidedsquare.zine.data.sound;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

public class SoundBuilder {

    private static final Codec<SoundBuilder> SOUND_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(soundBuilder -> soundBuilder.name),
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("volume", 1f).forGetter(soundBuilder -> soundBuilder.volume),
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("pitch", 1f).forGetter(soundBuilder -> soundBuilder.pitch),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("weight", 1).forGetter(soundBuilder -> soundBuilder.weight),
            SoundBuilder.Type.CODEC.optionalFieldOf("type", SoundBuilder.Type.FILE).forGetter(soundBuilder -> soundBuilder.type),
            Codec.BOOL.optionalFieldOf("stream", false).forGetter(soundBuilder -> soundBuilder.stream),
            Codec.BOOL.optionalFieldOf("preload", false).forGetter(soundBuilder -> soundBuilder.preload),
            Codec.INT.optionalFieldOf("attenuation_distance", 16).forGetter(soundBuilder -> soundBuilder.attenuation)
    ).apply(instance, SoundBuilder::new));
    static final Codec<SoundBuilder> CODEC = Codec.either(Codec.STRING, SOUND_CODEC).xmap(
            either -> either.map(SoundBuilder::of, sound -> sound),
            sound -> sound.isDefaultSound() ? Either.left(sound.name) : Either.right(sound)
    );

    private final String name;
    private final Type type;
    private float volume = 1f;
    private float pitch = 1f;
    private int weight = 1;
    private boolean stream = false;
    private boolean preload = false;
    private int attenuation = 16;

    private SoundBuilder(String name, Type type) {
        this.type = type;
        this.name = this.type == Type.FILE ? name.replaceAll("\\.", "/") : name;
    }

    private SoundBuilder(String name, float volume, float pitch, int weight, Type type, boolean stream, boolean preload, int attenuation) {
        this(name, type);
        this.volume = volume;
        this.pitch = pitch;
        this.weight = weight;
        this.stream = stream;
        this.preload = preload;
        this.attenuation = attenuation;
    }

    public static SoundBuilder of(String name, Type type) {
        return new SoundBuilder(name, type);
    }

    public static SoundBuilder of(String name) {
        return of(name, Type.FILE);
    }

    public SoundBuilder volume(float volume) {
        this.volume = Math.max(Float.MIN_VALUE, volume);
        return this;
    }

    public SoundBuilder pitch(float pitch) {
        this.pitch = Math.max(Float.MIN_VALUE, pitch);
        return this;
    }

    public SoundBuilder weight(int weight) {
        this.weight = Math.max(1, weight);
        return this;
    }

    public SoundBuilder stream(boolean stream) {
        this.stream = stream;
        return this;
    }

    public SoundBuilder preload(boolean preload) {
        this.preload = preload;
        return this;
    }

    public SoundBuilder attenuation(int attenuation) {
        this.attenuation = attenuation;
        return this;
    }

    private boolean isDefaultSound() {
        return this.volume == 1f
                && this.pitch == 1f
                && this.weight == 1
                && this.type == SoundBuilder.Type.FILE
                && !this.stream
                && !this.preload
                && this.attenuation == 16;
    }

    public enum Type implements StringRepresentable {
        FILE("file"),
        SOUND_EVENT("event");

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}
