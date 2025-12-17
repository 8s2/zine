package com.eightsidedsquare.zine.client.block;

import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.state.property.Property;

public final class BlockStateVariantMaps {

    public static <T1 extends Comparable<T1>> BlockStateVariantMap.SingleProperty<BlockStateModel, T1> custom(Property<T1> property) {
        return new BlockStateVariantMap.SingleProperty<>(property);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> BlockStateVariantMap.DoubleProperty<BlockStateModel, T1, T2> custom(
            Property<T1> property1, Property<T2> property2
    ) {
        return new BlockStateVariantMap.DoubleProperty<>(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> BlockStateVariantMap.TripleProperty<BlockStateModel, T1, T2, T3> custom(
            Property<T1> property1, Property<T2> property2, Property<T3> property3
    ) {
        return new BlockStateVariantMap.TripleProperty<>(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> BlockStateVariantMap.QuadrupleProperty<BlockStateModel, T1, T2, T3, T4> custom(
            Property<T1> property1, Property<T2> property2, Property<T3> property3, Property<T4> property4
    ) {
        return new BlockStateVariantMap.QuadrupleProperty<>(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> BlockStateVariantMap.QuintupleProperty<BlockStateModel, T1, T2, T3, T4, T5> custom(
            Property<T1> property1, Property<T2> property2, Property<T3> property3, Property<T4> property4, Property<T5> property5
    ) {
        return new BlockStateVariantMap.QuintupleProperty<>(property1, property2, property3, property4, property5);
    }

    private BlockStateVariantMaps() {
    }

}
