package com.eightsidedsquare.zine.common.registry.holder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public record BlockEntityTypeHolder<T extends BlockEntity>(BlockEntityType<T> type, ResourceKey<BlockEntityType<?>> id) implements IdSupplier<ResourceKey<BlockEntityType<?>>> {
    @SuppressWarnings("deprecation")
    public Holder<BlockEntityType<?>> holder() {
        return this.type.builtInRegistryHolder();
    }
}
