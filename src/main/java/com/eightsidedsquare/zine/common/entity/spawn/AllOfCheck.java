package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.List;

public class AllOfCheck extends CompositeCheck {

    public static final MapCodec<AllOfCheck> CODEC = createCodec(AllOfCheck::new);

    public AllOfCheck(List<SpawnCondition> conditions) {
        super(conditions);
    }

    public AllOfCheck(SpawnCondition... conditions) {
        super(conditions);
    }

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
