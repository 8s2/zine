package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public record ItemCondition(ItemPredicate item) implements ItemUseCondition {
    public static final SyncedCodec<ItemCondition> TYPE = SyncedCodec.create(
            ItemPredicate.CODEC.fieldOf("item"),
            StreamCodecUtil.ITEM_PREDICATE,
            ItemCondition::item,
            ItemCondition::new
    );

    @Override
    public boolean test(Level level, @Nullable LivingEntity livingEntity, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        return this.item.test(stack);
    }

    @Override
    public SyncedCodec<? extends ItemUseCondition> type() {
        return TYPE;
    }
}
