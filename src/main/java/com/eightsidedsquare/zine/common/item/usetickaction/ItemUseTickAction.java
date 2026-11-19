package com.eightsidedsquare.zine.common.item.usetickaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public interface ItemUseTickAction extends SyncedCodec.Type<ItemUseTickAction> {
    Codec<ItemUseTickAction> CODEC = ZineBuiltinRegistries.ITEM_USE_TICK_ACTION_TYPE.byNameCodec()
            .dispatch(ItemUseTickAction::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, ItemUseTickAction> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.ITEM_USE_TICK_ACTION_TYPE)
            .dispatch(ItemUseTickAction::type, SyncedCodec::streamCodec);

    void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining);

    static BlockHitResult getHitResult(Level level, LivingEntity user, ClipContext.Block block, ClipContext.Fluid fluid) {
        Vec3 from = user.getEyePosition();
        double range = user instanceof Player player ? player.blockInteractionRange() : user.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
        Vec3 to = from.add(LivingEntity.calculateViewVector(user.getXRot(), user.getYRot()).scale(range));
        return level.clip(new ClipContext(from, to, block, fluid, user));
    }
}
