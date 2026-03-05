package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineBlockPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.NbtPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Optional;

@Mixin(BlockPredicate.class)
public abstract class BlockPredicateMixin implements ZineBlockPredicate {

    @Shadow @Final @Mutable
    private Optional<HolderSet<Block>> blocks;

    @Shadow @Final @Mutable
    private Optional<StatePropertiesPredicate> properties;

    @Shadow @Final @Mutable
    private Optional<NbtPredicate> nbt;

    @Override
    public void zine$setBlocks(@Nullable HolderSet<Block> blocks) {
        this.blocks = Optional.ofNullable(blocks);
    }

    @Override
    public void zine$addBlock(Block block) {
        if(this.blocks.isPresent()) {
            this.blocks = Optional.of(ZineUtil.mergeValue(this.blocks.get(), BuiltInRegistries.BLOCK::wrapAsHolder, block));
            return;
        }
        this.blocks = Optional.of(HolderSet.direct(BuiltInRegistries.BLOCK::wrapAsHolder, block));
    }

    @Override
    public void zine$addBlocks(Collection<Block> blocks) {
        if(this.blocks.isPresent()) {
            this.blocks = Optional.of(ZineUtil.mergeValues(this.blocks.get(), BuiltInRegistries.BLOCK::wrapAsHolder, blocks));
            return;
        }
        this.blocks = Optional.of(HolderSet.direct(BuiltInRegistries.BLOCK::wrapAsHolder, blocks));
    }

    @Override
    public void zine$setState(@Nullable StatePropertiesPredicate state) {
        this.properties = Optional.ofNullable(state);
    }

    @Override
    public void zine$setNbt(@Nullable NbtPredicate nbt) {
        this.nbt = Optional.ofNullable(nbt);
    }
}
