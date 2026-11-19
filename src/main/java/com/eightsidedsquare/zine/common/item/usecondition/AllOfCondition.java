package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public record AllOfCondition(List<ItemUseCondition> conditions) implements ItemUseCondition {
    public static final SyncedCodec<AllOfCondition> TYPE = SyncedCodec.create(
            CodecUtil.nonEmptyListCodec(ItemUseCondition.CODEC).fieldOf("conditions"),
            ItemUseCondition.STREAM_CODEC.apply(ByteBufCodecs.list()),
            AllOfCondition::conditions,
            AllOfCondition::new
    );

    @Override
    public boolean test(Level level, @Nullable LivingEntity livingEntity, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        for (ItemUseCondition condition : this.conditions) {
            if (!condition.test(level, livingEntity, stack, hitResultSupplier)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SyncedCodec<? extends ItemUseCondition> type() {
        return TYPE;
    }
}
