package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zinetest.common.block.entity.NestBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface TestmodBlockEntities {

    BlockEntityType<NestBlockEntity> NEST = TestmodInit.REGISTRY.blockEntity(
            "nest",
            FabricBlockEntityTypeBuilder.create(NestBlockEntity::new, TestmodBlocks.NEST)
    );

    static void init() {
    }
}
