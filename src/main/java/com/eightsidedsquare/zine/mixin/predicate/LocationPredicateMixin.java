package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineLocationPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

@Mixin(LocationPredicate.class)
public abstract class LocationPredicateMixin implements ZineLocationPredicate {

    @Shadow @Final @Mutable
    private Optional<LocationPredicate.PositionPredicate> position;

    @Shadow @Final @Mutable
    private Optional<HolderSet<Biome>> biomes;

    @Shadow @Final @Mutable
    private Optional<HolderSet<Structure>> structures;

    @Shadow @Final @Mutable
    private Optional<ResourceKey<Level>> dimension;

    @Shadow @Final @Mutable
    private Optional<Boolean> smokey;

    @Shadow @Final @Mutable
    private Optional<LightPredicate> light;

    @Shadow @Final @Mutable
    private Optional<BlockPredicate> block;

    @Shadow @Final @Mutable
    private Optional<FluidPredicate> fluid;

    @Shadow @Final @Mutable
    private Optional<Boolean> canSeeSky;

    @Override
    public void zine$setPosition(MinMaxBounds.Doubles x, MinMaxBounds.Doubles y, MinMaxBounds.Doubles z) {
        this.position = LocationPredicate.PositionPredicate.of(x, y, z);
    }

    @Override
    public void zine$clearPosition() {
        this.position = Optional.empty();
    }

    @Override
    public void zine$setBiomes(@Nullable HolderSet<Biome> biomes) {
        this.biomes = Optional.ofNullable(biomes);
    }

    @Override
    public void zine$addBiome(Holder<Biome> biome) {
        if(this.biomes.isPresent()) {
            this.biomes = Optional.of(ZineUtil.mergeValue(this.biomes.get(), Function.identity(), biome));
            return;
        }
        this.biomes = Optional.of(HolderSet.direct(biome));
    }

    @Override
    public void zine$addBiome(HolderGetter<Biome> biomeLookup, ResourceKey<Biome> biome) {
        if(this.biomes.isPresent()) {
            this.biomes = Optional.of(ZineUtil.mergeValue(this.biomes.get(), biomeLookup::getOrThrow, biome));
            return;
        }
        this.biomes = Optional.of(HolderSet.direct(biomeLookup::getOrThrow, biome));
    }

    @Override
    public void zine$addBiomes(HolderSet<Biome> biomes) {
        if(this.biomes.isPresent()) {
            this.biomes = Optional.of(ZineUtil.mergeValues(this.biomes.get(), biomes));
            return;
        }
        this.biomes = Optional.of(biomes);
    }

    @Override
    public void zine$addBiomes(HolderGetter<Biome> biomeLookup, Collection<ResourceKey<Biome>> biomes) {
        if(this.biomes.isPresent()) {
            this.biomes = Optional.of(ZineUtil.mergeValues(this.biomes.get(), biomeLookup::getOrThrow, biomes));
            return;
        }
        this.biomes = Optional.of(HolderSet.direct(biomeLookup::getOrThrow, biomes));
    }

    @Override
    public void zine$setStructures(@Nullable HolderSet<Structure> structures) {
        this.structures = Optional.ofNullable(structures);
    }

    @Override
    public void zine$addStructure(Holder<Structure> structure) {
        if(this.structures.isPresent()) {
            this.structures = Optional.of(ZineUtil.mergeValue(this.structures.get(), Function.identity(), structure));
            return;
        }
        this.structures = Optional.of(HolderSet.direct(structure));
    }

    @Override
    public void zine$addStructure(HolderGetter<Structure> structureLookup, ResourceKey<Structure> structure) {
        if(this.structures.isPresent()) {
            this.structures = Optional.of(ZineUtil.mergeValue(this.structures.get(), structureLookup::getOrThrow, structure));
            return;
        }
        this.structures = Optional.of(HolderSet.direct(structureLookup::getOrThrow, structure));
    }

    @Override
    public void zine$addStructures(HolderGetter<Structure> structureLookup, Collection<ResourceKey<Structure>> structures) {
        if(this.structures.isPresent()) {
            this.structures = Optional.of(ZineUtil.mergeValues(this.structures.get(), structureLookup::getOrThrow, structures));
            return;
        }
        this.structures = Optional.of(HolderSet.direct(structureLookup::getOrThrow, structures));
    }

    @Override
    public void zine$addStructures(HolderSet<Structure> structures) {
        if(this.structures.isPresent()) {
            this.structures = Optional.of(ZineUtil.mergeValues(this.structures.get(), structures));
            return;
        }
        this.structures = Optional.of(structures);
    }

    @Override
    public void zine$setDimension(@Nullable ResourceKey<Level> dimension) {
        this.dimension = Optional.ofNullable(dimension);
    }

    @Override
    public void zine$setSmokey(@Nullable Boolean smokey) {
        this.smokey = Optional.ofNullable(smokey);
    }

    @Override
    public void zine$setLight(@Nullable LightPredicate light) {
        this.light = Optional.ofNullable(light);
    }

    @Override
    public void zine$setBlock(@Nullable BlockPredicate block) {
        this.block = Optional.ofNullable(block);
    }

    @Override
    public void zine$setFluid(@Nullable FluidPredicate fluid) {
        this.fluid = Optional.ofNullable(fluid);
    }

    @Override
    public void zine$setCanSeeSky(@Nullable Boolean canSeeSky) {
        this.canSeeSky = Optional.ofNullable(canSeeSky);
    }
}
