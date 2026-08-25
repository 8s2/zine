package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.level.NoiseRouterNoise;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;

import java.util.List;

public class NoiseCheck implements SpawnCondition {
    public static final MapCodec<NoiseCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            NoiseRouterNoise.CODEC.fieldOf("noise").forGetter(NoiseCheck::getNoise),
            CodecUtil.nonEmptyListCodec(MinMaxBounds.Doubles.CODEC).fieldOf("ranges").forGetter(NoiseCheck::getRanges)
    ).apply(i, NoiseCheck::new));
    private NoiseRouterNoise noise;
    private List<MinMaxBounds.Doubles> ranges;

    public NoiseCheck(NoiseRouterNoise noise, List<MinMaxBounds.Doubles> ranges) {
        this.noise = noise;
        this.ranges = ranges;
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    public NoiseRouterNoise getNoise() {
        return this.noise;
    }

    public void setNoise(NoiseRouterNoise noise) {
        this.noise = noise;
    }

    public List<MinMaxBounds.Doubles> getRanges() {
        return this.ranges;
    }

    public void setRanges(List<MinMaxBounds.Doubles> ranges) {
        this.ranges = ranges;
    }

    @Override
    public boolean test(SpawnContext spawnContext) {
        BlockPos pos = spawnContext.pos();
        RandomState randomState = spawnContext.level().getLevel().getChunkSource().randomState();
        DensityFunction densityFunction = this.noise.get(randomState.router);
        float sample = randomState.getSampler(densityFunction)
                .sampleValue(SamplerContext.EMPTY_UNCACHED, pos.getX(), pos.getY(), pos.getZ());
        for(MinMaxBounds.Doubles range : this.ranges) {
            if(range.matches(sample)) {
                return true;
            }
        }
        return false;
    }
}
