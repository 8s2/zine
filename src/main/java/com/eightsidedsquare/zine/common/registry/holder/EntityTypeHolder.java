package com.eightsidedsquare.zine.common.registry.holder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public record EntityTypeHolder<T extends Entity>(EntityType<T> type, ResourceKey<EntityType<?>> id) implements IdSupplier<ResourceKey<EntityType<?>>> {
    @SuppressWarnings("deprecation")
    public Holder<EntityType<?>> holder() {
        return this.type.builtInRegistryHolder();
    }
}
