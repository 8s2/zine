package com.eightsidedsquare.zine.common.registry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;

import java.util.List;

public class RegistryQueueImpl<T> implements RegistryQueue<T> {

    private final Registry<T> registry;
    private final RegistryHelper registryHelper;
    private final List<Entry<T>> entries = new ObjectArrayList<>();

    RegistryQueueImpl(Registry<T> registry, RegistryHelper registryHelper) {
        this.registry = registry;
        this.registryHelper = registryHelper;
    }

    @Override
    public T add(String name, T value) {
        this.entries.add(new Entry<>(name, value));
        return value;
    }

    @Override
    public Holder.Reference<T> reference(String name, T value) {
        Holder.Reference<T> reference = Holder.Reference.createStandAlone(this.registry, this.registryHelper.key(this.registry.key(), name));
        reference.bindValue(this.add(name, value));
        return reference;
    }

    @Override
    public void registerAll() {
        for(Entry<T> entry : this.entries) {
            this.registryHelper.register(this.registry, entry.name, entry.value);
        }
        this.entries.clear();
    }

    private record Entry<T>(String name, T value) {
    }
}
