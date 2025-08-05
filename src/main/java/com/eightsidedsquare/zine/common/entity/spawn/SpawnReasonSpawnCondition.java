package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.spawn.SpawnCondition;
import net.minecraft.entity.spawn.SpawnContext;

import java.util.List;

public class SpawnReasonSpawnCondition implements SpawnCondition {

    public static final MapCodec<SpawnReasonSpawnCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.nonEmptyListCodec(CodecUtil.SPAWN_REASON).fieldOf("reasons").forGetter(SpawnReasonSpawnCondition::getReasons)
    ).apply(instance, SpawnReasonSpawnCondition::new));

    private List<SpawnReason> reasons;

    public SpawnReasonSpawnCondition(List<SpawnReason> reasons) {
        this.reasons = reasons;
    }

    public void setReasons(List<SpawnReason> reasons) {
        this.reasons = reasons;
    }

    public List<SpawnReason> getReasons() {
        return this.reasons;
    }

    @Override
    public MapCodec<? extends SpawnCondition> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext spawnContext) {
        return this.reasons.contains(spawnContext.zine$getSpawnReason());
    }
}
