package com.eightsidedsquare.zine.common.text;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface ZineComponent {
    default List<Component> zine$wrap(int width) {
        return List.of((Component) this);
    }
}
