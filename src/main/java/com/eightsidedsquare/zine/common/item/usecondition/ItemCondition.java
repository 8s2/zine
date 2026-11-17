package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public record ItemCondition(ItemPredicate item) implements ItemUseCondition {
    public static final SyncedCodec<ItemCondition> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    ItemPredicate.CODEC.fieldOf("item").forGetter(ItemCondition::item)
            ).apply(i, ItemCondition::new)),
            StreamCodec.composite(
                    StreamCodecUtil.ITEM_PREDICATE,
                    ItemCondition::item,
                    ItemCondition::new
            )
    );

    @Override
    public boolean test(Level level, @Nullable Player player, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        return this.item.test(stack);
    }

    @Override
    public SyncedCodec<? extends ItemUseCondition> type() {
        return TYPE;
    }
}
