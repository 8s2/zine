package com.eightsidedsquare.zine.common.state;

import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;

public class StateMapImpl<V> implements StateMap<V> {

    private final List<Property<?>> properties;
    private final List<V> values;

    StateMapImpl(List<Property<?>> properties, List<V> values) {
        this.properties = properties;
        this.values = values;
    }

    @Override
    public V get(StateHolder<?, ?> state) {
        int i = 0;
        for(int j = this.properties.size() - 1; j >= 0; j--) {
            Property<?> property = this.properties.get(j);
            i = i * property.getPossibleValues().size() + this.ordinal(property, state);
        }
        return this.values.get(i);
    }

    private <T extends Comparable<T>> int ordinal(Property<T> property, StateHolder<?, ?> state) {
        return property.getInternalIndex(state.getValue(property));
    }
}
