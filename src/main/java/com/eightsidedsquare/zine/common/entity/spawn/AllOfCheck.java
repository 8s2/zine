package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.List;

public record AllOfCheck(List<SpawnCondition> conditions) implements SpawnCondition {
    public static final MapCodec<AllOfCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.nonEmptyList(SpawnCondition.CODEC.listOf()).fieldOf("conditions").forGetter(AllOfCheck::conditions)
    ).apply(i, AllOfCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext ctx) {
        for (SpawnCondition condition : this.conditions) {
            if(!condition.test(ctx)) {
                return false;
            }
        }
        return true;
    }
}