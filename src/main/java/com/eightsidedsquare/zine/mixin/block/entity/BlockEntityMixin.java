package com.eightsidedsquare.zine.mixin.block.entity;

import com.eightsidedsquare.zine.common.block.entity.ZineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements ZineBlockEntity {
}
