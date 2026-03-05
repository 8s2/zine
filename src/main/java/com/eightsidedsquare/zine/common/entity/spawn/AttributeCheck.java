package com.eightsidedsquare.zine.common.entity.spawn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.function.Function;

public class AttributeCheck implements SpawnCondition {

    @SuppressWarnings("unchecked")
    private static final Codec<EnvironmentAttribute<? extends Number>> ATTRIBUTE_CODEC = EnvironmentAttributes.CODEC.comapFlatMap(
            attribute -> attribute.defaultValue() instanceof Number ?
                    DataResult.success((EnvironmentAttribute<? extends Number>) attribute) :
                    DataResult.error(() -> "Attribute must be a number"),
            Function.identity()
    );
    public static final MapCodec<AttributeCheck> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ATTRIBUTE_CODEC.fieldOf("attribute").forGetter(AttributeCheck::getAttribute),
            MinMaxBounds.Doubles.CODEC.fieldOf("range").forGetter(AttributeCheck::getRange)
    ).apply(i, AttributeCheck::new));
    private EnvironmentAttribute<? extends Number> attribute;
    private MinMaxBounds.Doubles range;

    public AttributeCheck(EnvironmentAttribute<? extends Number> attribute, MinMaxBounds.Doubles range) {
        this.attribute = attribute;
        this.range = range;
    }

    public EnvironmentAttribute<? extends Number> getAttribute() {
        return this.attribute;
    }

    public void setAttribute(EnvironmentAttribute<? extends Number> attribute) {
        this.attribute = attribute;
    }

    public MinMaxBounds.Doubles getRange() {
        return this.range;
    }

    public void setRange(MinMaxBounds.Doubles range) {
        this.range = range;
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext ctx) {
        return this.range.matches(ctx.environmentAttributes().getValue(this.attribute, ctx.pos()).doubleValue());
    }
}
