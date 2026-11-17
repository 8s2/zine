package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.List;

public record AnyOfCheck(List<SpawnCondition> conditions) implements SpawnCondition {
    public static final MapCodec<AnyOfCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.nonEmptyList(SpawnCondition.CODEC.listOf()).fieldOf("conditions").forGetter(AnyOfCheck::conditions)
    ).apply(i, AnyOfCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext ctx) {
        for (SpawnCondition condition : this.conditions) {
            if(condition.test(ctx)) {
                return true;
            }
        }
        return false;
    }
}