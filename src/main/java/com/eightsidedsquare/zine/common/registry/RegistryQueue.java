package com.eightsidedsquare.zine.common.registry;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;

/**
 * A registry queue allows queueing of values to register for registering them when appropriate.
 * This is primarily for working through issues with registry order, as in most cases this is not needed.
 * Use {@link RegistryHelper#createQueue(Registry)} to create a registry queue.
 * @param <T> the type of the registry for this queue
 */
public interface RegistryQueue<T> {

    /**
     * Adds a value to the registry queue.
     * @param name the name of the value
     * @param value the value to register
     * @return the enqueued value
     */
    T add(String name, T value);

    /**
     * Adds a value to the registry queue.
     * @param name the name of the value
     * @param value the value to register
     * @return the enqueued value wrapped in a registry entry
     */
    Holder.Reference<T> reference(String name, T value);

    /**
     * Registers all queued values.
     * It's best to call this with {@link ModInitializer#onInitialize()} or a method called by the initialize method.
     */
    void registerAll();

}
