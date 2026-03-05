package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public class InvertedCheck implements SpawnCondition {

    public static final MapCodec<InvertedCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            SpawnCondition.CODEC.fieldOf("condition").forGetter(InvertedCheck::getCondition)
    ).apply(i, InvertedCheck::new));
    private SpawnCondition condition;

    public InvertedCheck(SpawnCondition condition) {
        this.condition = condition;
    }

    public SpawnCondition getCondition() {
        return this.condition;
    }

    public void setCondition(SpawnCondition condition) {
        this.condition = condition;
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext ctx) {
        return !this.condition.test(ctx);
    }
}
