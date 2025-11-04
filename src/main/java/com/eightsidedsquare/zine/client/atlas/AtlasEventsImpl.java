package com.eightsidedsquare.zine.client.atlas;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public final class AtlasEventsImpl {
    private AtlasEventsImpl() {
    }

    private static final Map<Identifier, Event<AtlasEvents.ModifySources>> MODIFY_SOURCES_EVENT_MAP = new Object2ObjectOpenHashMap<>();

    public static Event<AtlasEvents.ModifySources> getOrCreateModifySourcesEvent(Identifier atlasId) {
        return MODIFY_SOURCES_EVENT_MAP.computeIfAbsent(atlasId, id -> createModifySourcesEvent());
    }

    @Nullable
    public static Event<AtlasEvents.ModifySources> getModifySourcesEvent(Identifier atlasId) {
        return MODIFY_SOURCES_EVENT_MAP.get(atlasId);
    }

    private static Event<AtlasEvents.ModifySources> createModifySourcesEvent() {
        return EventFactory.createArrayBacked(AtlasEvents.ModifySources.class, callbacks -> sources -> {
            for (AtlasEvents.ModifySources callback : callbacks) {
                callback.modifySources(sources);
            }
        });
    }

}
