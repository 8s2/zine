package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.advancements.criterion.LightPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ZineLocationPredicate {

    default void zine$setPosition(MinMaxBounds.Doubles x, MinMaxBounds.Doubles y, MinMaxBounds.Doubles z) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$clearPosition() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBiomes(@Nullable HolderSet<Biome> biomes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addBiome(Holder<Biome> biome) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addBiome(HolderGetter<Biome> biomeLookup, ResourceKey<Biome> biome) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addBiomes(HolderSet<Biome> biomes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addBiomes(HolderGetter<Biome> biomeLookup, Collection<ResourceKey<Biome>> biomes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setStructures(@Nullable HolderSet<Structure> structures) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addStructure(Holder<Structure> structure) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addStructure(HolderGetter<Structure> structureLookup, ResourceKey<Structure> structure) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addStructures(HolderSet<Structure> structures) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addStructures(HolderGetter<Structure> structureLookup, Collection<ResourceKey<Structure>> structures) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDimension(@Nullable ResourceKey<Level> dimension) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSmokey(@Nullable Boolean smokey) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setLight(@Nullable LightPredicate light) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBlock(@Nullable BlockPredicate block) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFluid(@Nullable FluidPredicate fluid) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setCanSeeSky(@Nullable Boolean canSeeSky) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
