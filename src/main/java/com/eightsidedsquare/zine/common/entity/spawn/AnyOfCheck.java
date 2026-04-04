package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.List;

public class AnyOfCheck extends CompositeCheck {

    public static final MapCodec<AnyOfCheck> CODEC = createCodec(AnyOfCheck::new);

    public AnyOfCheck(List<SpawnCondition> conditions) {
        super(conditions);
    }

    public AnyOfCheck(SpawnCondition... conditions) {
        super(conditions);
    }

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