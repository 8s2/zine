package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public record InverseUseCondition(ItemUseCondition condition) implements ItemUseCondition {
    public static final SyncedCodec<InverseUseCondition> TYPE = SyncedCodec.create(
            ItemUseCondition.CODEC.fieldOf("condition"),
            ItemUseCondition.STREAM_CODEC,
            InverseUseCondition::condition,
            InverseUseCondition::new
    );

    @Override
    public boolean test(Level level, @Nullable LivingEntity livingEntity, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        return !this.condition.test(level, livingEntity, stack, hitResultSupplier);
    }

    @Override
    public SyncedCodec<? extends ItemUseCondition> type() {
        return TYPE;
    }
}
