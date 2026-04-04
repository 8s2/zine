package com.eightsidedsquare.zine.mixin.entity.spawn;

import com.eightsidedsquare.zine.common.entity.spawn.ZineBiomeCheck;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.function.Function;

@Mixin(BiomeCheck.class)
public abstract class BiomeCheckMixin implements ZineBiomeCheck {

    @Shadow @Final @Mutable
    private HolderSet<Biome> requiredBiomes;

    @Override
    public void zine$setRequiredBiomes(HolderSet<Biome> requiredBiomes) {
        this.requiredBiomes = requiredBiomes;
    }

    @Override
    public void zine$addRequiredBiome(Holder<Biome> requiredBiome) {
        this.requiredBiomes = ZineUtil.mergeValue(this.requiredBiomes, Function.identity(), requiredBiome);
    }

    @Override
    public void zine$addRequiredBiome(HolderGetter<Biome> biomeLookup, ResourceKey<Biome> requiredBiome) {
        this.requiredBiomes = ZineUtil.mergeValue(this.requiredBiomes, biomeLookup::getOrThrow, requiredBiome);
    }

    @Override
    public void zine$addRequiredBiomes(HolderSet<Biome> requiredBiomes) {
        this.requiredBiomes = ZineUtil.mergeValues(this.requiredBiomes, requiredBiomes);
    }

    @Override
    public void zine$addRequiredBiomes(HolderGetter<Biome> biomeLookup, Collection<ResourceKey<Biome>> requiredBiomes) {
        this.requiredBiomes = ZineUtil.mergeValues(this.requiredBiomes, biomeLookup::getOrThrow, requiredBiomes);
    }
}
