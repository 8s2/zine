package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zinetest.common.block.NestBlock;
import com.eightsidedsquare.zinetest.core.references.TestmodBlockItemIds;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface TestmodBlocks {

    Block TOURMALINE_BLOCK = Testmod.REGISTRY.block(TestmodBlockItemIds.TOURMALINE_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    Block WOOD = Testmod.REGISTRY.block(TestmodBlockItemIds.WOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_OAK_PLANKS));
    Block RAINBOW = Testmod.REGISTRY.block(TestmodBlockItemIds.RAINBOW, BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE.red()));
    Block BIG_DIAMOND = Testmod.REGISTRY.block(TestmodBlockItemIds.BIG_DIAMOND, BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK));
    Block NEST = Testmod.REGISTRY.block(TestmodBlockItemIds.NEST, NestBlock::new, BlockBehaviour.Properties.of().sound(SoundType.AZALEA));

    static void init() {
    }

}
