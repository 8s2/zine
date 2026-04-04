package com.eightsidedsquare.zine.common.state;

import net.minecraft.world.level.block.state.StateHolder;

public interface StateMap<V> {

    V get(StateHolder<?, ?> state);

}

