package com.eightsidedsquare.zine.common.registry.holder;

import net.minecraft.core.Holder;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record BlockItemHolder(
        Block block,
        Item item,
        BlockItemId id) implements IdSupplier<BlockItemId>, ItemLike {
    public BlockItemHolder(BlockHolder blockHolder, ItemHolder itemHolder) {
        this(blockHolder.block(), itemHolder.item(), new BlockItemId(blockHolder.id(), itemHolder.id()));
    }

    public ResourceKey<Block> blockId() {
        return this.id.block();
    }

    public ResourceKey<Item> itemId() {
        return this.id.item();
    }

    public BlockState defaultBlockState() {
        return this.block.defaultBlockState();
    }

    public ItemStack getDefaultInstance() {
        return this.item.getDefaultInstance();
    }

    @Override
    public Item asItem() {
        return this.item;
    }

    @SuppressWarnings("deprecation")
    public Holder<Block> blockHolder() {
        return this.block.builtInRegistryHolder();
    }

    @SuppressWarnings("deprecation")
    public Holder<Item> itemHolder() {
        return this.item.builtInRegistryHolder();
    }
}
