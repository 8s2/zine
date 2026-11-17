package com.eightsidedsquare.zine.common.util;

import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ClipContext;

public final class ClipContextSerialization {
    public static final Codec<ClipContext.Block> BLOCK_CODEC = createBlockCodec();
    public static final StreamCodec<ByteBuf, ClipContext.Block> BLOCK_STREAM_CODEC = StreamCodecUtil.ofEnum(ClipContext.Block.values());
    public static final Codec<ClipContext.Fluid> FLUID_CODEC = createFluidCodec();
    public static final StreamCodec<ByteBuf, ClipContext.Fluid> FLUID_STREAM_CODEC = StreamCodecUtil.ofEnum(ClipContext.Fluid.values());

    private static Codec<ClipContext.Block> createBlockCodec() {
        ExtraCodecs.LateBoundIdMapper<String, ClipContext.Block> idMapper = new ExtraCodecs.LateBoundIdMapper<>();
        idMapper.put("collider", ClipContext.Block.COLLIDER);
        idMapper.put("outline", ClipContext.Block.OUTLINE);
        idMapper.put("visual", ClipContext.Block.VISUAL);
        idMapper.put("fall_damage_resetting", ClipContext.Block.FALLDAMAGE_RESETTING);
        return idMapper.codec(Codec.STRING);
    }

    private static Codec<ClipContext.Fluid> createFluidCodec() {
        ExtraCodecs.LateBoundIdMapper<String, ClipContext.Fluid> idMapper = new ExtraCodecs.LateBoundIdMapper<>();
        idMapper.put("none", ClipContext.Fluid.NONE);
        idMapper.put("source_only", ClipContext.Fluid.SOURCE_ONLY);
        idMapper.put("any", ClipContext.Fluid.ANY);
        idMapper.put("water", ClipContext.Fluid.WATER);
        return idMapper.codec(Codec.STRING);
    }

    private ClipContextSerialization() {
    }
}
