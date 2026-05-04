package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zinetest.core.references.TestmodItemIds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class TestmodItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public TestmodItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.builder(ItemTags.TRIM_MATERIALS)
                .add(TestmodItemIds.TOURMALINE)
                .add(BlockItemIds.OBSIDIAN.item());
    }
}
