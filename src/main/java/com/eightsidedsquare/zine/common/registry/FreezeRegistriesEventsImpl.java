package com.eightsidedsquare.zine.common.registry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Map;

@SuppressWarnings("unchecked")
public final class FreezeRegistriesEventsImpl {

    private static final Map<ResourceKey<? extends Registry<?>>, Event<FreezeRegistriesEvents.Callback<?>>> BEFORE = new Object2ObjectOpenHashMap<>();
    private static final Map<ResourceKey<? extends Registry<?>>, Event<FreezeRegistriesEvents.Callback<?>>> AFTER = new Object2ObjectOpenHashMap<>();

    public static <T> Event<FreezeRegistriesEvents.Callback<T>> getOrCreateEvent(boolean before, ResourceKey<? extends Registry<T>> registryKey) {
        return (Event<FreezeRegistriesEvents.Callback<T>>) (Object) (before ? BEFORE : AFTER)
                .computeIfAbsent(registryKey, key -> createEvent());
    }

    private static <T> Event<FreezeRegistriesEvents.Callback<?>> createEvent() {
        return EventFactory.createArrayBacked(
                FreezeRegistriesEvents.Callback.class,
                callbacks -> registry -> {
                    for (FreezeRegistriesEvents.Callback<?> callback : callbacks) {
                        ((FreezeRegistriesEvents.Callback<T>) callback).onFreeze((Registry<T>) registry);
                    }
                }
        );
    }

    public static <T> void apply(boolean before, Registry<T> registry) {
        Event<FreezeRegistriesEvents.Callback<T>> callback = (Event<FreezeRegistriesEvents.Callback<T>>) (Object)
                (before ? BEFORE : AFTER).get(registry.key());
        if(callback != null) {
            callback.invoker().onFreeze(registry);
        }
    }

    private FreezeRegistriesEventsImpl() {
    }
}
