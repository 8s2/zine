package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.*;
import net.minecraft.text.Style;

import java.util.stream.Stream;

public class ExtendedStyleMapCodec extends MapCodec<Style> {

    private final MapCodec<Style> baseCodec;
    private final MapCodec<CustomStyleAttributeContainer> customCodec;

    public ExtendedStyleMapCodec(MapCodec<Style> baseCodec) {
        this.baseCodec = baseCodec;
        this.customCodec = CustomStyleAttributeContainer.CODEC.optionalFieldOf("zine:custom", CustomStyleAttributeContainer.create());
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        return Stream.concat(this.baseCodec.keys(ops), this.customCodec.keys(ops));
    }

    @Override
    public <T> DataResult<Style> decode(DynamicOps<T> ops, MapLike<T> input) {
        DataResult<Style> baseResult = this.baseCodec.decode(ops, input);
        if(!baseResult.hasResultOrPartial()) {
            return baseResult;
        }
        DataResult<CustomStyleAttributeContainer> customResult = this.customCodec.decode(ops, input);
        if(customResult.hasResultOrPartial()) {
            CustomStyleAttributeContainer attributes = customResult.getPartialOrThrow();
            if(!attributes.isEmpty()) {
                return baseResult.getPartialOrThrow().isEmpty() ?
                        baseResult.map(style -> style.zine$withCustomAttributes(attributes)) :
                        baseResult.ifSuccess(style -> style.zine$setCustomAttributeContainer(attributes));
            }
        }
        return baseResult;
    }

    @Override
    public <T> RecordBuilder<T> encode(Style input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
        return this.customCodec.encode(input.zine$getCustomAttributeContainer(), ops, this.baseCodec.encode(input, ops, prefix));
    }
}
