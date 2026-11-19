package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useonaction.ConditionUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.ConstantUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.SequenceUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.UseActionUseOn;

public final class ZineItemUseOnActions {
    static void init() {
        ZineMod.REGISTRY.itemUseOnAction("condition", ConditionUseOn.TYPE);
        ZineMod.REGISTRY.itemUseOnAction("constant", ConstantUseOn.TYPE);
        ZineMod.REGISTRY.itemUseOnAction("sequence", SequenceUseOn.TYPE);
        ZineMod.REGISTRY.itemUseOnAction("use_action", UseActionUseOn.TYPE);
    }

    private ZineItemUseOnActions() {
    }
}
