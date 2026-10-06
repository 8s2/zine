package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zine.common.registry.holder.BlockItemHolder;
import com.eightsidedsquare.zinetest.common.block.NestBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface TestmodBlockItems {
    BlockItemHolder TOURMALINE_BLOCK = Testmod.REGISTRY.blockItem("tourmaline_block", BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    BlockItemHolder WOOD = Testmod.REGISTRY.blockItem("wood", BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_OAK_PLANKS));
    BlockItemHolder RAINBOW = Testmod.REGISTRY.blockItem("rainbow", BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE.red()));
    BlockItemHolder BIG_DIAMOND = Testmod.REGISTRY.blockItem("big_diamond", BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK));
    BlockItemHolder NEST = Testmod.REGISTRY.blockItem("nest", NestBlock::new, BlockBehaviour.Properties.of().sound(SoundType.AZALEA));

    static void init() {
    }
}
