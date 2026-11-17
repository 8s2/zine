package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public record AllOfCondition(List<ItemUseCondition> conditions) implements ItemUseCondition {
    public static final SyncedCodec<AllOfCondition> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    CodecUtil.nonEmptyListCodec(ItemUseCondition.CODEC).fieldOf("conditions").forGetter(AllOfCondition::conditions)
            ).apply(i, AllOfCondition::new)),
            StreamCodec.composite(
                    ItemUseCondition.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    AllOfCondition::conditions,
                    AllOfCondition::new
            )
    );

    @Override
    public boolean test(Level level, @Nullable Player player, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        for (ItemUseCondition condition : this.conditions) {
            if (!condition.test(level, player, stack, hitResultSupplier)) {
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
