package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.destroyedaction.ContainerDestroyed;
import com.eightsidedsquare.zine.common.item.destroyedaction.SequenceDestroyed;

public final class ZineItemDestroyedActions {
    static void init() {
        ZineMod.REGISTRY.itemDestroyedAction("sequence", SequenceDestroyed.TYPE);
        ZineMod.REGISTRY.itemDestroyedAction("container", ContainerDestroyed.TYPE);
    }

    private ZineItemDestroyedActions() {
    }
}
