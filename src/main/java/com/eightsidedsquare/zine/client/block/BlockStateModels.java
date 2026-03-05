package com.eightsidedsquare.zine.client.block;

import net.fabricmc.fabric.api.client.model.loading.v1.CompositeBlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.WeightedVariants;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;

import java.util.List;

public final class BlockStateModels {

    /**
     * Creates a {@link SingleVariant.Unbaked} for the given model variant
     */
    public static SingleVariant.Unbaked single(Variant variant) {
        return new SingleVariant.Unbaked(variant);
    }

    /**
     * Creates a {@link SingleVariant.Unbaked} for the given model identifier
     */
    public static SingleVariant.Unbaked single(Identifier modelId) {
        return single(new Variant(modelId));
    }

    /**
     * Creates a {@link WeightedVariants.Unbaked} for the given unbaked block state model pool
     */
    public static WeightedVariants.Unbaked weighted(WeightedList<BlockStateModel.Unbaked> entries) {
        return new WeightedVariants.Unbaked(entries);
    }

    /**
     * Creates a {@link WeightedVariants.Unbaked} for the given unbaked block state model pool builder
     */
    public static WeightedVariants.Unbaked weighted(WeightedList.Builder<BlockStateModel.Unbaked> entriesBuilder) {
        return weighted(entriesBuilder.build());
    }

    /**
     * Creates a {@link WeightedVariants.Unbaked} for the given unbaked block state models
     */
    public static WeightedVariants.Unbaked weighted(BlockStateModel.Unbaked... models) {
        WeightedList.Builder<BlockStateModel.Unbaked> entriesBuilder = WeightedList.builder();
        for (BlockStateModel.Unbaked model : models) {
            entriesBuilder.add(model);
        }
        return weighted(entriesBuilder);
    }

    /**
     * Creates a {@link CompositeBlockStateModel.Unbaked} for the given unbaked block state models
     */
    public static CompositeBlockStateModel.Unbaked composite(List<BlockStateModel.Unbaked> models) {
        return CompositeBlockStateModel.Unbaked.of(models);
    }

    /**
     * Creates a {@link CompositeBlockStateModel.Unbaked} for the given unbaked block state models
     */
    public static CompositeBlockStateModel.Unbaked composite(BlockStateModel.Unbaked... models) {
        return composite(List.of(models));
    }

    /**
     * Creates a {@link ConnectedBlockStateModel.Unbaked}
     * @param baseTexture texture id base for the model's connected pattern textures
     * @param fancy {@code true} for adding edges based on blocks in front of a face
     */
    public static ConnectedBlockStateModel.Unbaked connected(Identifier baseTexture, boolean fancy) {
        return new ConnectedBlockStateModel.Unbaked(baseTexture, fancy);
    }

    /**
     * Creates a {@link TessellatingBlockStateModel.Unbaked}
     * @param texture id of the texture
     * @param particleTexture id of the particle texture
     * @param size the number of blocks that the texture tessellates across
     */
    public static TessellatingBlockStateModel.Unbaked tessellating(Identifier texture, Identifier particleTexture, int size) {
        return new TessellatingBlockStateModel.Unbaked(texture, particleTexture, size);
    }

    /**
     * Creates a {@link TessellatingBlockStateModel.Unbaked}
     * @param texture id of the texture
     * @param size the number of blocks that the texture tessellates across
     */
    public static TessellatingBlockStateModel.Unbaked tessellating(Identifier texture, int size) {
        return new TessellatingBlockStateModel.Unbaked(texture, size);
    }

    private BlockStateModels() {
    }

}
