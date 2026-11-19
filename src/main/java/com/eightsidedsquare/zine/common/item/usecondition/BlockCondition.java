package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public record BlockCondition(AdventureModePredicate block) implements ItemUseCondition {
    public static final SyncedCodec<BlockCondition> TYPE = SyncedCodec.create(
            AdventureModePredicate.CODEC.fieldOf("block"),
            AdventureModePredicate.STREAM_CODEC,
            BlockCondition::block,
            BlockCondition::new
    );

    @Override
    public boolean test(Level level, @Nullable LivingEntity livingEntity, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        return this.block.test(new BlockInWorld(level, hitResultSupplier.get().getBlockPos(), false));
    }

    @Override
    public SyncedCodec<? extends ItemUseCondition> type() {
        return TYPE;
    }
}
