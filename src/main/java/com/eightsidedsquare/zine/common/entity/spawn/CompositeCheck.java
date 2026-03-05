package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.variant.SpawnCondition;

import java.util.List;
import java.util.function.Function;

public abstract class CompositeCheck implements SpawnCondition {

    protected List<SpawnCondition> conditions;

    public CompositeCheck(List<SpawnCondition> conditions) {
        this.conditions = conditions;
    }

    public CompositeCheck(SpawnCondition... conditions) {
        this(List.of(conditions));
    }

    public List<SpawnCondition> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<SpawnCondition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(SpawnCondition condition) {
        this.setConditions(ZineUtil.addOrUnfreeze(this.conditions, condition));
    }

    public static <T extends CompositeCheck> MapCodec<T> createCodec(Function<List<SpawnCondition>, T> factory) {
        return RecordCodecBuilder.mapCodec(i -> i.group(
                ExtraCodecs.nonEmptyList(SpawnCondition.CODEC.listOf()).fieldOf("conditions").forGetter(T::getConditions)
        ).apply(i, factory));
    }

}
