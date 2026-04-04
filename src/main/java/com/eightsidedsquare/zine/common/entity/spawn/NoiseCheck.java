package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.world.NoiseRouterNoise;
import com.eightsidedsquare.zine.common.world.density_function.MutableNoisePos;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.levelgen.NoiseRouter;

import java.util.List;

public class NoiseCheck implements SpawnCondition {

    public static final MapCodec<NoiseCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            NoiseRouterNoise.CODEC.fieldOf("noise").forGetter(NoiseCheck::getNoise),
            CodecUtil.nonEmptyListCodec(MinMaxBounds.Doubles.CODEC).fieldOf("ranges").forGetter(NoiseCheck::getRanges)
    ).apply(i, NoiseCheck::new));
    private static final MutableNoisePos NOISE_POS = new MutableNoisePos(0, 0, 0);
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
        NOISE_POS.set(pos.getX(), pos.getY(), pos.getZ());
        ServerLevel world = spawnContext.level().getLevel();
        NoiseRouter noiseRouter = world.getChunkSource().randomState().router();
        double sample = this.noise.densityFunctionGetter.apply(noiseRouter).compute(NOISE_POS);
        for(MinMaxBounds.Doubles range : this.ranges) {
            if(range.matches(sample)) {
                return true;
            }
        }
        return false;
    }
}
