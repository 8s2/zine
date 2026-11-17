package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useaction.ConditionUse;
import com.eightsidedsquare.zine.common.item.useaction.ConstantUse;
import com.eightsidedsquare.zine.common.item.useaction.SequenceUse;
import com.eightsidedsquare.zine.common.item.useaction.UseOnActionUse;

public final class ZineUseItemActions {
    static void init() {
        ZineMod.REGISTRY.useItemAction("condition", ConditionUse.TYPE);
        ZineMod.REGISTRY.useItemAction("constant", ConstantUse.TYPE);
        ZineMod.REGISTRY.useItemAction("sequence", SequenceUse.TYPE);
        ZineMod.REGISTRY.useItemAction("use_on_action", UseOnActionUse.TYPE);
    }

    private ZineUseItemActions() {
    }
}
