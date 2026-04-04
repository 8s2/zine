package com.eightsidedsquare.zine.common.advancement;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

public final class AdvancementEvents {
    private AdvancementEvents() {
    }

    public static Event<ModifyAdvancement> modifyAdvancementEvent(Identifier advancementId) {
        return AdvancementEventsImpl.getOrCreateModifyAdvancementEvent(advancementId);
    }

    public static void modifyAdvancement(Identifier advancementId, ModifyAdvancement callback) {
        modifyAdvancementEvent(advancementId).register(callback);
    }

    @FunctionalInterface
    public interface ModifyAdvancement {
        Advancement modifyAdvancement(Advancement advancement, HolderLookup.Provider wrapperLookup);
    }

}
