package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.spawn.SpawnCondition;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

import java.util.List;

public class DimensionSpawnCondition implements SpawnCondition {

    public static final MapCodec<DimensionSpawnCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.nonEmptyListCodec(RegistryKey.createCodec(RegistryKeys.WORLD)).fieldOf("dimensions").forGetter(DimensionSpawnCondition::getDimensions)
    ).apply(instance, DimensionSpawnCondition::new));

    private List<RegistryKey<World>> dimensions;

    public DimensionSpawnCondition(List<RegistryKey<World>> dimensions) {
        this.dimensions = dimensions;
    }

    public void setDimensions(List<RegistryKey<World>> dimensions) {
        this.dimensions = dimensions;
    }

    public List<RegistryKey<World>> getDimensions() {
        return this.dimensions;
    }

    @Override
    public MapCodec<? extends SpawnCondition> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext spawnContext) {
        return this.dimensions.contains(spawnContext.world().toServerWorld().getRegistryKey());
    }
}
