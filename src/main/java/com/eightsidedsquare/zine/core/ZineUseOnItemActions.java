package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useonaction.ConditionUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.ConstantUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.SequenceUseOn;
import com.eightsidedsquare.zine.common.item.useonaction.UseActionUseOn;

public final class ZineUseOnItemActions {
    static void init() {
        ZineMod.REGISTRY.useOnItemAction("condition", ConditionUseOn.TYPE);
        ZineMod.REGISTRY.useOnItemAction("constant", ConstantUseOn.TYPE);
        ZineMod.REGISTRY.useOnItemAction("sequence", SequenceUseOn.TYPE);
        ZineMod.REGISTRY.useOnItemAction("use_action", UseActionUseOn.TYPE);
    }

    private ZineUseOnItemActions() {
    }
}
