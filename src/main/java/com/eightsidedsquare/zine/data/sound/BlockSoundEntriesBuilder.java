package com.eightsidedsquare.zine.data.sound;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockSoundEntriesBuilder {
    private final ResourceKey<BlockSoundSet> blockSoundSet;
    private final EnumMap<Type, SoundEntryBuilder> entryBuilders;

    private BlockSoundEntriesBuilder(ResourceKey<BlockSoundSet> blockSoundSet) {
        this.blockSoundSet = blockSoundSet;
        this.entryBuilders = new EnumMap<>(Type.class);
        for(Type type : Type.values()) {
            this.entryBuilders.put(type, SoundEntryBuilder
                    .of(type.get(this.blockSoundSet))
                    .subtitle(type.getSubtitle()));
        }
    }

    public static BlockSoundEntriesBuilder of(ResourceKey<BlockSoundSet> blockSoundSet) {
        return new BlockSoundEntriesBuilder(blockSoundSet);
    }

    private BlockSoundEntriesBuilder apply(Type type, Consumer<SoundEntryBuilder> consumer) {
        consumer.accept(this.entryBuilders.get(type));
        return this;
    }

    private <T> BlockSoundEntriesBuilder apply(Type type, EntryFunction<T> function, T t) {
        function.apply(this.entryBuilders.get(type), t);
        return this;
    }

    private <T1, T2> BlockSoundEntriesBuilder apply(Type type, BiEntryFunction<T1, T2> function, T1 t1, T2 t2) {
        function.apply(this.entryBuilders.get(type), t1, t2);
        return this;
    }

    private <T1, T2, T3> BlockSoundEntriesBuilder apply(Type type, TriEntryFunction<T1, T2, T3> function, T1 t1, T2 t2, T3 t3) {
        function.apply(this.entryBuilders.get(type), t1, t2, t3);
        return this;
    }

    public BlockSoundEntriesBuilder replace(Type type, boolean replace) {
        return this.apply(type, SoundEntryBuilder::replace, replace);
    }

    public BlockSoundEntriesBuilder subtitle(Type type, @Nullable String subtitle) {
        return this.apply(type, SoundEntryBuilder::subtitle, subtitle);
    }

    public BlockSoundEntriesBuilder subtitle(Type type) {
        return this.apply(type, SoundEntryBuilder::subtitle);
    }

    public BlockSoundEntriesBuilder with(Type type, SoundBuilder sound) {
        return this.apply(type, SoundEntryBuilder::with, sound);
    }

    public BlockSoundEntriesBuilder with(Type type, SoundBuilder sound, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::with, sound, consumer);
    }

    public BlockSoundEntriesBuilder with(Type type, String name) {
        return this.apply(type, SoundEntryBuilder::with, name);
    }

    public BlockSoundEntriesBuilder with(Type type, String name, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::with, name, consumer);
    }

    public BlockSoundEntriesBuilder with(Type type, SoundEvent source) {
        return this.apply(type, SoundEntryBuilder::with, source);
    }

    public BlockSoundEntriesBuilder with(Type type, SoundEvent source, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::with, source, consumer);
    }

    public BlockSoundEntriesBuilder with(Type type) {
        return this.apply(type, SoundEntryBuilder::with);
    }

    public BlockSoundEntriesBuilder with(Type type, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::with, consumer);
    }

    public BlockSoundEntriesBuilder with(Type type, Type source) {
        return this.apply(type, SoundEntryBuilder::with, source.get(this.blockSoundSet));
    }

    public BlockSoundEntriesBuilder with(Type type, Type source, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::with, source.get(this.blockSoundSet), consumer);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, String name, String suffix) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, name, suffix);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, String name, String suffix, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, name, suffix, consumer);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, SoundEvent source, String suffix) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, source, suffix);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, SoundEvent source, String suffix, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, source, suffix, consumer);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, String suffix) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, suffix);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, String suffix, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, suffix, consumer);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, Type source, String suffix) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, source.get(this.blockSoundSet), suffix);
    }

    public BlockSoundEntriesBuilder withSuffixed(Type type, Type source, String suffix, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withSuffixed, source.get(this.blockSoundSet), suffix, consumer);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, String name, int variants) {
        return this.apply(type, SoundEntryBuilder::withVariants, name, variants);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, String name, int variants, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withVariants, name, variants, consumer);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, SoundEvent source, int variants) {
        return this.apply(type, SoundEntryBuilder::withVariants, source, variants);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, SoundEvent source, int variants, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withVariants, source, variants, consumer);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, int variants) {
        return this.apply(type, SoundEntryBuilder::withVariants, variants);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, int variants, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withVariants, variants, consumer);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, Type source, int variants) {
        return this.apply(type, SoundEntryBuilder::withVariants, source.get(this.blockSoundSet), variants);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, Type source, int variants, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withVariants, source.get(this.blockSoundSet), variants, consumer);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, SoundEvent event) {
        return this.apply(type, SoundEntryBuilder::withEvent, event);
    }

    public BlockSoundEntriesBuilder withVariants(Type type, SoundEvent event, Consumer<SoundBuilder> consumer) {
        return this.apply(type, SoundEntryBuilder::withEvent, event, consumer);
    }

    public void offerTo(SoundEntryConsumer consumer) {
        for(SoundEntryBuilder soundEntryBuilder : this.entryBuilders.values()) {
            soundEntryBuilder.offerTo(consumer);
        }
    }

    public enum Type {
        BREAK("block.generic.break", BlockSoundSet::breakSound),
        PLACE("block.generic.place", BlockSoundSet::placeSound),
        HIT("block.generic.hit", BlockSoundSet::hitSound),
        STEP("block.generic.footsteps", BlockSoundSet::stepSound),
        FALL(BlockSoundSet::fallSound);

        @Nullable
        private final String subtitle;
        private final Function<BlockSoundSet, Optional<Holder<SoundEvent>>> soundEventGetter;

        Type(@Nullable String subtitle, Function<BlockSoundSet, Optional<Holder<SoundEvent>>> soundEventGetter) {
            this.subtitle = subtitle;
            this.soundEventGetter = soundEventGetter;
        }

        Type(Function<BlockSoundSet, Optional<Holder<SoundEvent>>> soundEventGetter) {
            this(null, soundEventGetter);
        }

        @Nullable
        public String getSubtitle() {
            return this.subtitle;
        }

        public SoundEvent get(ResourceKey<BlockSoundSet> blockSoundSet) {
            return null;
//            return this.soundEventGetter.apply(blockSoundSet);
        }
    }

    private interface EntryFunction<T> {
        void apply(SoundEntryBuilder soundEntryBuilder, T t);
    }

    private interface BiEntryFunction<T1, T2> {
        void apply(SoundEntryBuilder soundEntryBuilder, T1 t1, T2 t2);
    }

    private interface TriEntryFunction<T1, T2, T3>  {
        void apply(SoundEntryBuilder soundEntryBuilder, T1 t1, T2 t2, T3 t3);
    }
}
