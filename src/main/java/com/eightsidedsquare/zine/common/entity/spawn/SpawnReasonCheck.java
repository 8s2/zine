package com.eightsidedsquare.zine.common.entity.spawn;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.List;

public class SpawnReasonCheck implements SpawnCondition {

    public static final MapCodec<SpawnReasonCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CodecUtil.grammaticalListMapCodec(
                    "reason",
                    "reasons",
                    CodecUtil.SPAWN_REASON,
                    ExtraCodecs.nonEmptyList(CodecUtil.SPAWN_REASON.listOf())
            ).forGetter(SpawnReasonCheck::getReasons)
    ).apply(i, SpawnReasonCheck::new));

    private List<EntitySpawnReason> reasons;

    public SpawnReasonCheck(List<EntitySpawnReason> reasons) {
        this.reasons = reasons;
    }

    public void setReasons(List<EntitySpawnReason> reasons) {
        this.reasons = reasons;
    }

    public void addReason(EntitySpawnReason reason) {
        this.setReasons(ZineUtil.addOrUnfreeze(this.reasons, reason));
    }

    public List<EntitySpawnReason> getReasons() {
        return this.reasons;
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext spawnContext) {
        return this.reasons.contains(spawnContext.zine$getSpawnReason());
    }
}
