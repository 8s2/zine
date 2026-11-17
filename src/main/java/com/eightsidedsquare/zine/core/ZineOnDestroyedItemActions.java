package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.item.ondestroyedaction.ContainerOnDestroyed;
import com.eightsidedsquare.zine.common.item.ondestroyedaction.SequenceOnDestroyed;

public final class ZineOnDestroyedItemActions {
    static void init() {
        ZineMod.REGISTRY.onDestroyedItemAction("sequence", SequenceOnDestroyed.TYPE);
        ZineMod.REGISTRY.onDestroyedItemAction("container", ContainerOnDestroyed.TYPE);
    }

    private ZineOnDestroyedItemActions() {
    }
}
