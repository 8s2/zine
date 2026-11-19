package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.useaction.*;

public final class ZineItemUseActions {
    static void init() {
        ZineMod.REGISTRY.itemUseAction("condition", ConditionUse.TYPE);
        ZineMod.REGISTRY.itemUseAction("constant", ConstantUse.TYPE);
        ZineMod.REGISTRY.itemUseAction("sequence", SequenceUse.TYPE);
        ZineMod.REGISTRY.itemUseAction("start_using", StartUsingUse.TYPE);
        ZineMod.REGISTRY.itemUseAction("use_on_action", UseOnActionUse.TYPE);
    }

    private ZineItemUseActions() {
    }
}
