package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zine.client.block.BlockStateModels;
import com.eightsidedsquare.zine.client.data.BlockModelDefinitions;
import com.eightsidedsquare.zinetest.client.NestBlockStateModel;
import com.eightsidedsquare.zinetest.client.UnbakedNestItemModel;
import com.eightsidedsquare.zinetest.core.TestmodBlocks;
import com.eightsidedsquare.zinetest.core.Testmod;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.resources.model.cuboid.ItemModelGenerator;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;

public class TestmodModelProvider extends FabricModelProvider {

    @SuppressWarnings("UnstableApiUsage")
    public TestmodModelProvider(FabricPackOutput output) {
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
                        new NestBlockStateModel.Unbaked(Testmod.id("nest"))
                )
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.itemModelOutput.accept(TestmodBlocks.NEST.asItem(), new UnbakedNestItemModel(
                ItemModelGenerator.GENERATED_ITEM_MODEL_ID,
                Identifier.withDefaultNamespace("item/generated"),
                Optional.empty(),
                List.of()
        ));
    }
}
