package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zine.client.block.BlockStateModels;
import com.eightsidedsquare.zine.client.data.BlockModelDefinitions;
import com.eightsidedsquare.zinetest.client.NestBlockStateModel;
import com.eightsidedsquare.zinetest.core.TestmodBlocks;
import com.eightsidedsquare.zinetest.core.TestmodInit;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;

public class TestmodModelGen extends FabricModelProvider {

    @SuppressWarnings("UnstableApiUsage")
    public TestmodModelGen(FabricPackOutput output) {
        super(new FabricPackOutput(output.getModContainer(), output.getOutputFolder(), false));
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.blockStateOutput.accept(
                BlockModelDefinitions.customVariants(
                        TestmodBlocks.WOOD,
                        BlockStateModels.connected(
                                ModelLocationUtils.getModelLocation(TestmodBlocks.WOOD),
                                true
                        )
                )
        );
        generator.blockStateOutput.accept(
                BlockModelDefinitions.customVariants(
                        TestmodBlocks.RAINBOW,
                        BlockStateModels.tessellating(
                                ModelLocationUtils.getModelLocation(TestmodBlocks.RAINBOW),
                                4
                        )
                )
        );
        generator.blockStateOutput.accept(
                BlockModelDefinitions.customVariants(
                        TestmodBlocks.BIG_DIAMOND,
                        BlockStateModels.tessellating(
                                ModelLocationUtils.getModelLocation(TestmodBlocks.BIG_DIAMOND),
                                7
                        )
                )
        );
        generator.blockStateOutput.accept(
                BlockModelDefinitions.customVariants(
                        TestmodBlocks.NEST,
                        new NestBlockStateModel.Unbaked(TestmodInit.id("nest"))
                )
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(TestmodBlocks.NEST.asItem(), ModelTemplates.FLAT_ITEM);
    }
}
