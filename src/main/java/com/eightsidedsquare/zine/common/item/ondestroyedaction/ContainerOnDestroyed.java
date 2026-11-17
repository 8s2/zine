package com.eightsidedsquare.zine.common.item.ondestroyedaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.ItemContainerContents;

public final class ContainerOnDestroyed implements OnDestroyedItemAction {
    public static final ContainerOnDestroyed INSTANCE = new ContainerOnDestroyed();
    public static final SyncedCodec<ContainerOnDestroyed> TYPE = SyncedCodec.unit(INSTANCE);

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        if (!itemEntity.getItem().has(DataComponents.CONTAINER)) {
            return;
        }
        ItemContainerContents container = itemEntity.getItem().set(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (container != null) {
            ItemUtils.onContainerDestroyed(itemEntity, container.nonEmptyItemCopyStream());
        }
    }

    @Override
    public SyncedCodec<? extends OnDestroyedItemAction> type() {
        return TYPE;
    }

    private ContainerOnDestroyed() {
    }
}
