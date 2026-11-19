package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public final class SecondaryUseCondition implements ItemUseCondition {
    public static final SecondaryUseCondition INSTANCE = new SecondaryUseCondition();
    public static final SyncedCodec<SecondaryUseCondition> TYPE = SyncedCodec.unit(INSTANCE);

    @Override
    public boolean test(Level level, @Nullable LivingEntity livingEntity, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier) {
        return livingEntity instanceof Player player && player.isSecondaryUseActive();
    }

    @Override
    public SyncedCodec<? extends ItemUseCondition> type() {
        return TYPE;
    }

    private SecondaryUseCondition() {
    }
}
