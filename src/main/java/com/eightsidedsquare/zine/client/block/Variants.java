package com.eightsidedsquare.zine.client.block;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;

public final class Variants {

    public static Variant variant(Identifier modelId, Variant.SimpleModelState modelState) {
        return new Variant(modelId, modelState);
    }

    public static Variant variant(Identifier modelId) {
        return new Variant(modelId);
    }

    public static Variant variant(Block block) {
        return variant(ModelLocationUtils.getModelLocation(block));
    }

    public static Variant variant(Block block, String suffix) {
        return variant(ModelLocationUtils.getModelLocation(block, suffix));
    }

    public static MultiVariant multi(WeightedList<Variant> variants) {
        return new MultiVariant(variants);
    }

    public static MultiVariant multi(WeightedList.Builder<Variant> variantsBuilder) {
        return multi(variantsBuilder.build());
    }

    public static MultiVariant multi(Variant... variants) {
        WeightedList.Builder<Variant> variantsBuilder = WeightedList.builder();
        for (Variant variant : variants) {
            variantsBuilder.add(variant);
        }
        return multi(variantsBuilder);
    }

    public static MultiVariant multi(Identifier... modelIds) {
        WeightedList.Builder<Variant> variantsBuilder = WeightedList.builder();
        for (Identifier modelId : modelIds) {
            variantsBuilder.add(variant(modelId));
        }
        return multi(variantsBuilder);
    }

    public static MultiVariant multi(Block block) {
        return multi(ModelLocationUtils.getModelLocation(block));
    }

    public static MultiVariant multi(Block block, String suffix) {
        return multi(ModelLocationUtils.getModelLocation(block, suffix));
    }
    
    private Variants() {
    }
    
}
