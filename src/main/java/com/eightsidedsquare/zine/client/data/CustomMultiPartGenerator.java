package com.eightsidedsquare.zine.client.data;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelDispatcher;
import net.minecraft.client.renderer.block.dispatch.multipart.Condition;
import net.minecraft.client.renderer.block.dispatch.multipart.Selector;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class CustomMultiPartGenerator implements BlockModelDefinitionGenerator {

    private final Block block;
    private final BlockStateModelDispatcher.MultiPartDefinition multipart = new BlockStateModelDispatcher.MultiPartDefinition(new ObjectArrayList<>());

    private CustomMultiPartGenerator(Block block) {
        this.block = block;
    }

    private CustomMultiPartGenerator(Block block, List<Selector> components) {
        this(block);
        for (Selector component : components) {
            component.condition().ifPresent(this::validate);
            this.multipart.selectors().add(component);
        }
    }

    public static CustomMultiPartGenerator create(Block block) {
        return new CustomMultiPartGenerator(block);
    }

    @Override
    public Block block() {
        return this.block;
    }

    private void validate(Condition condition) {
        condition.instantiate(this.block.getStateDefinition());
    }

    public CustomMultiPartGenerator with(Condition condition, BlockStateModel.Unbaked model) {
        this.validate(condition);
        this.multipart.selectors().add(new Selector(Optional.of(condition), model));
        return this;
    }

    public CustomMultiPartGenerator with(ConditionBuilder conditionBuilder, BlockStateModel.Unbaked model) {
        return this.with(conditionBuilder.build(), model);
    }

    public CustomMultiPartGenerator with(BlockStateModel.Unbaked model) {
        return this.with(new ConditionBuilder(), model);
    }

    public CustomMultiPartGenerator apply(UnaryOperator<BlockStateModel.Unbaked> operator) {
        List<Selector> components = new ObjectArrayList<>();
        for (Selector selector : this.multipart.selectors()) {
            components.add(new Selector(selector.condition(), operator.apply(selector.variant())));
        }
        return new CustomMultiPartGenerator(this.block, components);
    }

    @Override
    public BlockStateModelDispatcher create() {
        return new BlockStateModelDispatcher(Optional.empty(), Optional.of(this.multipart));
    }
}
