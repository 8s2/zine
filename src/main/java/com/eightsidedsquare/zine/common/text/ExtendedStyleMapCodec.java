package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.*;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ExtraCodecs;

import java.util.stream.Stream;

public class ExtendedStyleMapCodec extends MapCodec<Style> {

    private final MapCodec<Style> baseCodec;
    private final MapCodec<Integer> outlineColorCodec;

    public ExtendedStyleMapCodec(MapCodec<Style> baseCodec) {
        this.baseCodec = baseCodec;
        this.outlineColorCodec = ExtraCodecs.ARGB_COLOR_CODEC.optionalFieldOf("zine:outline_color", 0);
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        return Stream.concat(this.baseCodec.keys(ops), this.outlineColorCodec.keys(ops));
    }

    @Override
    public <T> DataResult<Style> decode(DynamicOps<T> ops, MapLike<T> input) {
        DataResult<Style> baseResult = this.baseCodec.decode(ops, input);
        if(!baseResult.hasResultOrPartial()) {
            return baseResult;
        }
        DataResult<Integer> outlineColorResult = this.outlineColorCodec.decode(ops, input);
        if(outlineColorResult.hasResultOrPartial()) {
            int outlineColor = outlineColorResult.getPartialOrThrow();
            if((outlineColor & 0xff000000) != 0) {
                return baseResult.getPartialOrThrow().isEmpty() ?
                        baseResult.map(style -> style.zine$withOutlineColor(outlineColor)) :
                        baseResult.ifSuccess(style -> style.zine$setOutlineColor(outlineColor));
            }
        }
        return baseResult;
    }

    @Override
    public <T> RecordBuilder<T> encode(Style input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
        return this.outlineColorCodec.encode(input.zine$getOutlineColor(), ops, this.baseCodec.encode(input, ops, prefix));
    }
}
