package com.eightsidedsquare.zine.common.advancement;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class AdvancementEventsImpl {
    private AdvancementEventsImpl() {
    }
    
    private static final Map<Identifier, Event<AdvancementEvents.ModifyAdvancement>> MODIFY_ADVANCEMENT_EVENT_MAP = new Object2ObjectOpenHashMap<>();

    public static Event<AdvancementEvents.ModifyAdvancement> getOrCreateModifyAdvancementEvent(Identifier advancementId) {
        return MODIFY_ADVANCEMENT_EVENT_MAP.computeIfAbsent(advancementId, id -> createModifyAdvancementEvent());
    }

    @Nullable
    public static Event<AdvancementEvents.ModifyAdvancement> getModifyAdvancementEvent(Identifier advancementId) {
        return MODIFY_ADVANCEMENT_EVENT_MAP.get(advancementId);
    }

    private static Event<AdvancementEvents.ModifyAdvancement> createModifyAdvancementEvent() {
        return EventFactory.createArrayBacked(AdvancementEvents.ModifyAdvancement.class, callbacks -> (advancement, wrapperLookup) -> {
            for (AdvancementEvents.ModifyAdvancement callback : callbacks) {
                advancement = callback.modifyAdvancement(advancement, wrapperLookup);
            }
            return advancement;
        });
    }
}
