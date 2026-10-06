package com.eightsidedsquare.zine.common.registry.holder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record BlockHolder(Block block, ResourceKey<Block> id) implements IdSupplier<ResourceKey<Block>> {
    public BlockState defaultBlockState() {
        return this.block.defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    public Holder<Block> holder() {
        return this.block.builtInRegistryHolder();
    }
}
