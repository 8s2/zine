package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zine.common.registry.holder.BlockEntityTypeHolder;
import com.eightsidedsquare.zinetest.common.block.entity.NestBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public interface TestmodBlockEntities {
    BlockEntityTypeHolder<NestBlockEntity> NEST = Testmod.REGISTRY.blockEntity(
            "nest",
            FabricBlockEntityTypeBuilder.create(NestBlockEntity::new, TestmodBlockItems.NEST.block())
    );

    static void init() {
    }
}
