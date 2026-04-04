package com.eightsidedsquare.zine.client.data;

import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelDispatcher;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class DirectGenerator implements BlockModelDefinitionGenerator {

    private final Block block;
    private final BlockStateModelDispatcher dispatcher;

    private DirectGenerator(Block block, BlockStateModelDispatcher dispatcher) {
        this.block = block;
        this.dispatcher = dispatcher;
    }

    public static DirectGenerator create(Block block, BlockStateModelDispatcher definition) {
        return new DirectGenerator(block, definition);
    }

    public static DirectGenerator create(Block block, BlockStateModelDispatcher.SimpleModelSelectors variants) {
        return create(block, new BlockStateModelDispatcher(Optional.of(variants), Optional.empty()));
    }

    public static DirectGenerator create(Block block, BlockStateModelDispatcher.MultiPartDefinition multipart) {
        return create(block, new BlockStateModelDispatcher(Optional.empty(), Optional.of(multipart)));
    }

    @Override
    public Block block() {
        return this.block;
    }

    @Override
    public BlockStateModelDispatcher create() {
        return this.dispatcher;
    }
}
