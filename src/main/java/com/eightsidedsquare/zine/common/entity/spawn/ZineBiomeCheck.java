package com.eightsidedsquare.zine.common.entity.spawn;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Collection;

public interface ZineBiomeCheck {

    default void zine$setRequiredBiomes(HolderSet<Biome> requiredBiomes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredBiome(Holder<Biome> requiredBiome) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredBiome(HolderGetter<Biome> biomeLookup, ResourceKey<Biome> requiredBiome) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredBiomes(HolderSet<Biome> requiredBiomes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequiredBiomes(HolderGetter<Biome> biomeLookup, Collection<ResourceKey<Biome>> requiredBiomes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
