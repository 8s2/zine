package com.eightsidedsquare.zinetest.core.references;

import com.eightsidedsquare.zinetest.core.Testmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface TestmodBlockEntityTypeIds {
    ResourceKey<BlockEntityType<?>> NEST = create("nest");

    private static ResourceKey<BlockEntityType<?>> create(String name) {
        return Testmod.REGISTRY.key(Registries.BLOCK_ENTITY_TYPE, name);
    }
}
