package com.eightsidedsquare.zine.common.item.destroyedaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.ItemContainerContents;

public final class ContainerDestroyed implements ItemDestroyedAction {
    public static final ContainerDestroyed INSTANCE = new ContainerDestroyed();
    public static final SyncedCodec<ContainerDestroyed> TYPE = SyncedCodec.unit(INSTANCE);

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
    public SyncedCodec<? extends ItemDestroyedAction> type() {
        return TYPE;
    }

    private ContainerDestroyed() {
    }
}
