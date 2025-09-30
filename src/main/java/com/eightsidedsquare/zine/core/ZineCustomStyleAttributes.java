package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.text.CustomStyleAttribute;
import com.mojang.serialization.Codec;

public interface ZineCustomStyleAttributes {

    CustomStyleAttribute<Integer> OUTLINE = ZineMod.REGISTRY.customStyleAttribute("outline", Codec.INT, false);

    static void init() {
    }

}
