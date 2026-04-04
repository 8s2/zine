package com.eightsidedsquare.zine.common.state;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.*;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class StateMapBuilder<V> {

    List<Coordinate<?>> coordinates = new ArrayList<>();
    int permutations = 1;

    StateMapBuilder() {
    }

    <T extends Comparable<T>> StateMapBuilder<V> with(Property<T> property) {
        int size = property.getPossibleValues().size();
        this.coordinates.add(new Coordinate<>(property, size, this.permutations));
        this.permutations *= size;
        return this;
    }

    StateMapBuilder<V> withAll(Property<?>... properties) {
        for(Property<?> property : properties) {
            this.with(property);
        }
        return this;
    }

    public static <T extends Comparable<T>, V> StateMap<V> create(Property<T> property, Function<T, V> function) {
        return new StateMapBuilder<V>()
                .with(property)
                .build(coordinate -> function.apply((T) coordinate[0]));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  BiFunction<T1, T2, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1]
                ));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            T3 extends Comparable<T3>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  Property<T3> property3,
                                  Function3<T1, T2, T3, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2, property3)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1],
                        (T3) coordinate[2]
                ));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            T3 extends Comparable<T3>,
            T4 extends Comparable<T4>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  Property<T3> property3,
                                  Property<T4> property4,
                                  Function4<T1, T2, T3, T4, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2, property3, property4)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1],
                        (T3) coordinate[2],
                        (T4) coordinate[3]
                ));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            T3 extends Comparable<T3>,
            T4 extends Comparable<T4>,
            T5 extends Comparable<T5>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  Property<T3> property3,
                                  Property<T4> property4,
                                  Property<T5> property5,
                                  Function5<T1, T2, T3, T4, T5, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2, property3, property4, property5)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1],
                        (T3) coordinate[2],
                        (T4) coordinate[3],
                        (T5) coordinate[4]
                ));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            T3 extends Comparable<T3>,
            T4 extends Comparable<T4>,
            T5 extends Comparable<T5>,
            T6 extends Comparable<T6>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  Property<T3> property3,
                                  Property<T4> property4,
                                  Property<T5> property5,
                                  Property<T6> property6,
                                  Function6<T1, T2, T3, T4, T5, T6, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2, property3, property4, property5, property6)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1],
                        (T3) coordinate[2],
                        (T4) coordinate[3],
                        (T5) coordinate[4],
                        (T6) coordinate[5]
                ));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            T3 extends Comparable<T3>,
            T4 extends Comparable<T4>,
            T5 extends Comparable<T5>,
            T6 extends Comparable<T6>,
            T7 extends Comparable<T7>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  Property<T3> property3,
                                  Property<T4> property4,
                                  Property<T5> property5,
                                  Property<T6> property6,
                                  Property<T7> property7,
                                  Function7<T1, T2, T3, T4, T5, T6, T7, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2, property3, property4, property5, property6, property7)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1],
                        (T3) coordinate[2],
                        (T4) coordinate[3],
                        (T5) coordinate[4],
                        (T6) coordinate[5],
                        (T7) coordinate[6]
                ));
    }

    public static <
            T1 extends Comparable<T1>,
            T2 extends Comparable<T2>,
            T3 extends Comparable<T3>,
            T4 extends Comparable<T4>,
            T5 extends Comparable<T5>,
            T6 extends Comparable<T6>,
            T7 extends Comparable<T7>,
            T8 extends Comparable<T8>,
            V> StateMap<V> create(Property<T1> property1,
                                  Property<T2> property2,
                                  Property<T3> property3,
                                  Property<T4> property4,
                                  Property<T5> property5,
                                  Property<T6> property6,
                                  Property<T7> property7,
                                  Property<T8> property8,
                                  Function8<T1, T2, T3, T4, T5, T6, T7, T8, V> function) {
        return new StateMapBuilder<V>()
                .withAll(property1, property2, property3, property4, property5, property6, property7, property8)
                .build(coordinate -> function.apply(
                        (T1) coordinate[0],
                        (T2) coordinate[1],
                        (T3) coordinate[2],
                        (T4) coordinate[3],
                        (T5) coordinate[4],
                        (T6) coordinate[5],
                        (T7) coordinate[6],
                        (T8) coordinate[7]
                ));
    }

    private StateMap<V> build(Function<Object[], V> function) {
        int size = this.coordinates.size();
        Object[] coordinate = new Object[size];
        ImmutableList.Builder<V> builder = ImmutableList.builder();
        for(int i = 0; i < this.permutations; i++) {
            for(int j = 0; j < size; j++) {
                coordinate[j] = this.coordinates.get(j).get(i);
            }
            builder.add(function.apply(coordinate));
        }
        ImmutableList.Builder<Property<?>> properties = ImmutableList.builder();
        this.coordinates.forEach(c -> properties.add(c.property));
        return new StateMapImpl<>(properties.build(), builder.build());
    }

    record Coordinate<T extends Comparable<T>>(Property<T> property, int size, int base) {
        T get(int i) {
            return this.property.getPossibleValues().get((i / this.base) % this.size);
        }
    }

}
