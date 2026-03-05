package com.eightsidedsquare.zine.client.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.PropertyValueList;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelDispatcher;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

public class CustomMultiVariantGenerator implements BlockModelDefinitionGenerator {

    private final Block block;
    private final Map<PropertyValueList, BlockStateModel.Unbaked> variants;
    private final Set<Property<?>> definedProperties;

    private CustomMultiVariantGenerator(Block block, Map<PropertyValueList, BlockStateModel.Unbaked> variants, Set<Property<?>> definedProperties) {
        this.block = block;
        this.variants = variants;
        this.definedProperties = definedProperties;
    }

    public CustomMultiVariantGenerator coordinate(PropertyDispatch<UnaryOperator<BlockStateModel.Unbaked>> operatorMap) {
        Set<Property<?>> properties = MultiVariantGenerator.validateAndExpandProperties(this.definedProperties, this.block, operatorMap);
        ImmutableMap.Builder<PropertyValueList, BlockStateModel.Unbaked> builder = ImmutableMap.builder();
        for (Map.Entry<PropertyValueList, BlockStateModel.Unbaked> entry : this.variants.entrySet()) {
            for (Map.Entry<PropertyValueList, UnaryOperator<BlockStateModel.Unbaked>> operatorEntry : operatorMap.getEntries().entrySet()) {
                builder.put(entry.getKey().extend(operatorEntry.getKey()), operatorEntry.getValue().apply(entry.getValue()));
            }
        }
        return new CustomMultiVariantGenerator(this.block, builder.build(), properties);
    }

    public CustomMultiVariantGenerator apply(UnaryOperator<BlockStateModel.Unbaked> operator) {
        ImmutableMap.Builder<PropertyValueList, BlockStateModel.Unbaked> builder = ImmutableMap.builder();
        for (Map.Entry<PropertyValueList, BlockStateModel.Unbaked> entry : this.variants.entrySet()) {
            builder.put(entry.getKey(), operator.apply(entry.getValue()));
        }
        return new CustomMultiVariantGenerator(this.block, builder.build(), this.definedProperties);
    }

    public static CustomMultiVariantGenerator.Empty create(Block block) {
        return new CustomMultiVariantGenerator.Empty(block);
    }

    public static CustomMultiVariantGenerator create(Block block, PropertyDispatch<BlockStateModel.Unbaked> map) {
        Set<Property<?>> properties = MultiVariantGenerator.validateAndExpandProperties(Set.of(), block, map);
        return new CustomMultiVariantGenerator(block, map.getEntries(), properties);
    }

    public static CustomMultiVariantGenerator create(Block block, BlockStateModel.Unbaked model) {
        return new CustomMultiVariantGenerator(block, Map.of(PropertyValueList.EMPTY, model), Set.of());
    }

    @Override
    public Block block() {
        return this.block;
    }

    @Override
    public BlockStateModelDispatcher create() {
        Map<String, BlockStateModel.Unbaked> models = new HashMap<>();
        for (Map.Entry<PropertyValueList, BlockStateModel.Unbaked> entry : this.variants.entrySet()) {
            models.put(entry.getKey().getKey(), entry.getValue());
        }
        return new BlockStateModelDispatcher(Optional.of(new BlockStateModelDispatcher.SimpleModelSelectors(models)), Optional.empty());
    }

    public static class Empty {
        private final Block block;

        Empty(Block block) {
            this.block = block;
        }

        public CustomMultiVariantGenerator with(PropertyDispatch<BlockStateModel.Unbaked> map) {
            return CustomMultiVariantGenerator.create(this.block, map);
        }

        public CustomMultiVariantGenerator with(BlockStateModel.Unbaked model) {
            return CustomMultiVariantGenerator.create(this.block, model);
        }

    }
}
