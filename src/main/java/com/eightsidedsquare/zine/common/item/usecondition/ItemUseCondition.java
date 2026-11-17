package com.eightsidedsquare.zine.common.item.usecondition;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public interface ItemUseCondition extends SyncedCodec.Type<ItemUseCondition> {
    Codec<ItemUseCondition> CODEC = ZineBuiltinRegistries.ITEM_USE_CONDITION.byNameCodec()
            .dispatch(ItemUseCondition::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, ItemUseCondition> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.ITEM_USE_CONDITION)
            .dispatch(ItemUseCondition::type, SyncedCodec::streamCodec);

    boolean test(Level level, @Nullable Player player, ItemStack stack, Supplier<BlockHitResult> hitResultSupplier);
}
