package com.eightsidedsquare.zine.client.equipment;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

public final class EquipmentAssetEvents {
    public static final Event<Modify> MODIFY = EventFactory.createArrayBacked(Modify.class, callbacks -> (key, builder) -> {
        for (Modify callback : callbacks) {
            callback.modify(key, builder);
        }
    });

    @FunctionalInterface
    public interface Modify {
        void modify(ResourceKey<EquipmentAsset> key, EquipmentClientInfo.Builder builder);
    }

    private EquipmentAssetEvents() {
    }
}
