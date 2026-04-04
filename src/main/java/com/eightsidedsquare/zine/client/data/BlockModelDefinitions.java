package com.eightsidedsquare.zine.client.data;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelDispatcher;
import net.minecraft.world.level.block.Block;

/**
 * Helper class for instantiating different types of {@link net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator} as their names tend to be verbose
 */
public final class BlockModelDefinitions {

    /**
     * Creates a {@link MultiVariantGenerator.Empty} for the given block
     */
    public static MultiVariantGenerator.Empty variants(Block block) {
        return MultiVariantGenerator.dispatch(block);
    }

    /**
     * Creates a {@link MultiVariantGenerator} for the given block
     * @param map the block state variant map of weighted variants to apply
     */
    public static MultiVariantGenerator variants(Block block, PropertyDispatch<MultiVariant> map) {
        return variants(block).with(map);
    }

    /**
     * Creates a {@link MultiVariantGenerator} for the given block
     * @param variant the default variant
     */
    public static MultiVariantGenerator variants(Block block, MultiVariant variant) {
        return MultiVariantGenerator.dispatch(block, variant);
    }

    /**
     * Creates a {@link MultiPartGenerator} for the given block
     */
    public static MultiPartGenerator multipart(Block block) {
        return MultiPartGenerator.multiPart(block);
    }

    /**
     * Creates a {@link CustomMultiVariantGenerator} for the given block
     * @apiNote Unlike {@link MultiVariantGenerator}, this accepts unbaked block state models
     */
    public static CustomMultiVariantGenerator.Empty customVariants(Block block) {
        return CustomMultiVariantGenerator.create(block);
    }

    /**
     * Creates a {@link CustomMultiVariantGenerator} for the given block
     * @param map the block state variant map of unbaked block state models to apply
     * @apiNote Unlike {@link MultiVariantGenerator}, this accepts unbaked block state models
     */
    public static CustomMultiVariantGenerator customVariants(Block block, PropertyDispatch<BlockStateModel.Unbaked> map) {
        return CustomMultiVariantGenerator.create(block, map);
    }

    /**
     * Creates a {@link CustomMultiVariantGenerator} for the given block
     * @param model the default unbaked block state model
     * @apiNote Unlike {@link MultiVariantGenerator}, this accepts unbaked block state models
     */
    public static CustomMultiVariantGenerator customVariants(Block block, BlockStateModel.Unbaked model) {
        return CustomMultiVariantGenerator.create(block, model);
    }

    /**
     * Creates a {@link CustomMultiPartGenerator} for the given block
     * @apiNote Unlike {@link MultiPartGenerator}, this accepts unbaked block state models
     */
    public static CustomMultiPartGenerator customMultipart(Block block) {
        return CustomMultiPartGenerator.create(block);
    }

    /**
     * Creates a {@link DirectGenerator} for the given block
     * @param dispatcher the block model dispatcher that will be created
     */
    public static DirectGenerator direct(Block block, BlockStateModelDispatcher dispatcher) {
        return DirectGenerator.create(block, dispatcher);
    }

    /**
     * Creates a {@link DirectGenerator} for the given block
     * @param variants block model variants for the block model definition that will be created
     */
    public static DirectGenerator direct(Block block, BlockStateModelDispatcher.SimpleModelSelectors variants) {
        return DirectGenerator.create(block, variants);
    }

    /**
     * Creates a {@link DirectGenerator} for the given block
     * @param multipart multipart block models for the block model definition that will be created
     */
    public static DirectGenerator direct(Block block, BlockStateModelDispatcher.MultiPartDefinition multipart) {
        return DirectGenerator.create(block, multipart);
    }

    private BlockModelDefinitions() {
    }

}
