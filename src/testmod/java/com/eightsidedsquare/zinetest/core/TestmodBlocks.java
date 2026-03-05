package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zinetest.common.block.NestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface TestmodBlocks {

    Block TOURMALINE_BLOCK = TestmodInit.REGISTRY.blockWithItem("tourmaline_block", BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    Block GOO = TestmodInit.REGISTRY.blockWithItem("goo", BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK));
    Block WOOD = TestmodInit.REGISTRY.blockWithItem("wood", BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_OAK_PLANKS));
    Block RAINBOW = TestmodInit.REGISTRY.blockWithItem("rainbow", BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CONCRETE));
    Block BIG_DIAMOND = TestmodInit.REGISTRY.blockWithItem("big_diamond", BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK));
    Block NEST = TestmodInit.REGISTRY.blockWithItem("nest", BlockBehaviour.Properties.of().sound(SoundType.AZALEA), NestBlock::new);

    static void init() {
    }

}
