package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.spawn.SpawnCondition;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.util.dynamic.Codecs;

public class RandomSpawnCondition implements SpawnCondition {

    public static final MapCodec<RandomSpawnCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codecs.rangedInclusiveFloat(0, 1).fieldOf("chance").forGetter(RandomSpawnCondition::getChance)
    ).apply(instance, RandomSpawnCondition::new));

    private float chance;

    public RandomSpawnCondition(float chance) {
        this.chance = chance;
    }

    @Override
    public MapCodec<? extends SpawnCondition> getCodec() {
        return CODEC;
    }

    public float getChance() {
        return this.chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    @Override
    public boolean test(SpawnContext ctx) {
        return ctx.world().getRandom().nextFloat() < this.chance;
    }
}
