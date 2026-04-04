package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zine.client.materialmapping.MaterialMapping;
import com.eightsidedsquare.zine.data.materialmapping.MaterialMappingProvider;
import com.eightsidedsquare.zinetest.client.TestmodClient;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class TestmodMaterialMappingProvider extends MaterialMappingProvider {

    public TestmodMaterialMappingProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void generate() {
        this.output.accept(
                ChickenVariants.TEMPERATE,
                MaterialMapping.UnbakedSet.builder()
                        .add(
                                TestmodClient.NEST_MODEL,
                                MaterialMapping.Unbaked.builder()
                                        .add("egg", TextureMapping.getItemTexture(Items.EGG))
                                        .add("nest", TextureMapping.getBlockTexture(Blocks.OAK_LOG))
                                        .itemLayers(TextureMapping.getItemTexture(Items.EGG))
                        )

        );
        this.output.accept(
                ChickenVariants.WARM,
                MaterialMapping.UnbakedSet.builder()
                        .add(
                                TestmodClient.NEST_MODEL,
                                MaterialMapping.Unbaked.builder()
                                        .add("egg", TextureMapping.getItemTexture(Items.BROWN_EGG))
                                        .add("nest", TextureMapping.getBlockTexture(Blocks.ACACIA_LOG))
                                        .itemLayers(TextureMapping.getItemTexture(Items.BROWN_EGG))
                        )
        );
        this.output.accept(
                ChickenVariants.COLD,
                MaterialMapping.UnbakedSet.builder()
                        .add(
                                TestmodClient.NEST_MODEL,
                                MaterialMapping.Unbaked.builder()
                                        .add("egg", TextureMapping.getItemTexture(Items.BLUE_EGG))
                                        .add("nest", TextureMapping.getBlockTexture(Blocks.SPRUCE_LOG))
                                        .itemLayers(TextureMapping.getItemTexture(Items.BLUE_EGG))
                        )
        );
    }
}
