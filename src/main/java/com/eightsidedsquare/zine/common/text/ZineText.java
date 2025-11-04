package com.eightsidedsquare.zine.common.text;

import net.minecraft.text.Text;

import java.util.List;

public interface ZineText {
    default List<Text> zine$wrap(int width) {
        return List.of((Text) this);
    }
}
