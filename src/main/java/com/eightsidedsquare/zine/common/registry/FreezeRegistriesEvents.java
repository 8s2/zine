package com.eightsidedsquare.zine.common.registry;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class FreezeRegistriesEvents {

    public static <T> Event<Callback<T>> beforeFreeze(ResourceKey<? extends Registry<T>> registryKey) {
        return FreezeRegistriesEventsImpl.getOrCreateEvent(true, registryKey);
    }

    public static <T> Event<Callback<T>> beforeFreeze(Registry<T> registry) {
        return beforeFreeze(registry.key());
    }

    public static <T> Event<Callback<T>> afterFreeze(ResourceKey<? extends Registry<T>> registryKey) {
        return FreezeRegistriesEventsImpl.getOrCreateEvent(false, registryKey);
    }

    public static <T> Event<Callback<T>> afterFreeze(Registry<T> registry) {
        return afterFreeze(registry.key());
    }

    @FunctionalInterface
    public interface Callback<T> {

        void onFreeze(Registry<T> registry);

    }

    private FreezeRegistriesEvents() {
    }

}
