package com.eightsidedsquare.zine.client.block;

import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.world.level.block.state.properties.Property;

public final class BlockStateVariantMaps {

    public static <T1 extends Comparable<T1>> PropertyDispatch.C1<BlockStateModel, T1> custom(Property<T1> property) {
        return new PropertyDispatch.C1<>(property);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> PropertyDispatch.C2<BlockStateModel, T1, T2> custom(
            Property<T1> property1, Property<T2> property2
    ) {
        return new PropertyDispatch.C2<>(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> PropertyDispatch.C3<BlockStateModel, T1, T2, T3> custom(
            Property<T1> property1, Property<T2> property2, Property<T3> property3
    ) {
        return new PropertyDispatch.C3<>(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> PropertyDispatch.C4<BlockStateModel, T1, T2, T3, T4> custom(
            Property<T1> property1, Property<T2> property2, Property<T3> property3, Property<T4> property4
    ) {
        return new PropertyDispatch.C4<>(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> PropertyDispatch.C5<BlockStateModel, T1, T2, T3, T4, T5> custom(
            Property<T1> property1, Property<T2> property2, Property<T3> property3, Property<T4> property4, Property<T5> property5
    ) {
        return new PropertyDispatch.C5<>(property1, property2, property3, property4, property5);
    }

    private BlockStateVariantMaps() {
    }

}
