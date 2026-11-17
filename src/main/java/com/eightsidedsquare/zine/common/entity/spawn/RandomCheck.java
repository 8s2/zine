package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record RandomCheck(float chance) implements SpawnCondition {
    public static final MapCodec<RandomCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.floatRange(0, 1).fieldOf("chance").forGetter(RandomCheck::chance)
    ).apply(i, RandomCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext ctx) {
        return ctx.level().getRandom().nextFloat() < this.chance;
    }
}
