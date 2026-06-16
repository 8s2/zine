package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zinetest.common.block.NestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface TestmodBlocks {

    Block TOURMALINE_BLOCK = Testmod.REGISTRY.blockWithItem("tourmaline_block", BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    Block WOOD = Testmod.REGISTRY.blockWithItem("wood", BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_OAK_PLANKS));
    Block RAINBOW = Testmod.REGISTRY.blockWithItem("rainbow", BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE.red()));
    Block BIG_DIAMOND = Testmod.REGISTRY.blockWithItem("big_diamond", BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK));
    Block NEST = Testmod.REGISTRY.blockWithItem("nest", BlockBehaviour.Properties.of().sound(SoundType.AZALEA), NestBlock::new);

    static void init() {
    }

}
