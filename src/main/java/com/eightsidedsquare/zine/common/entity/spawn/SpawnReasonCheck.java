package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.List;

public record SpawnReasonCheck(List<EntitySpawnReason> reasons) implements SpawnCondition {
    public static final MapCodec<SpawnReasonCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CodecUtil.nonEmptyListCodec(CodecUtil.SPAWN_REASON).fieldOf("reasons").forGetter(SpawnReasonCheck::reasons)
    ).apply(i, SpawnReasonCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext spawnContext) {
        return this.reasons.contains(spawnContext.zine$getSpawnReason());
    }
}
