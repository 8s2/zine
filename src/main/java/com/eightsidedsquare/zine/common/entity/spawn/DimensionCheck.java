package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class DimensionCheck implements SpawnCondition {

    public static final MapCodec<DimensionCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CodecUtil.nonEmptyListCodec(ResourceKey.codec(Registries.DIMENSION)).fieldOf("dimensions").forGetter(DimensionCheck::getDimensions)
    ).apply(i, DimensionCheck::new));

    private List<ResourceKey<Level>> dimensions;

    public DimensionCheck(List<ResourceKey<Level>> dimensions) {
        this.dimensions = dimensions;
    }

    public void setDimensions(List<ResourceKey<Level>> dimensions) {
        this.dimensions = dimensions;
    }

    public List<ResourceKey<Level>> getDimensions() {
        return this.dimensions;
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext spawnContext) {
        return this.dimensions.contains(spawnContext.level().getLevel().dimension());
    }
}
