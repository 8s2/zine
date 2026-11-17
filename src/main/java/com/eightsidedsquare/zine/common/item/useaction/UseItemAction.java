package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.eightsidedsquare.zine.core.ZineBuiltinRegistries;
import com.eightsidedsquare.zine.core.ZineRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public interface UseItemAction extends SyncedCodec.Type<UseItemAction> {
    Codec<UseItemAction> CODEC = ZineBuiltinRegistries.USE_ITEM_ACTION.byNameCodec()
            .dispatch(UseItemAction::type, SyncedCodec::codec);
    StreamCodec<RegistryFriendlyByteBuf, UseItemAction> STREAM_CODEC = ByteBufCodecs.registry(ZineRegistries.USE_ITEM_ACTION)
            .dispatch(UseItemAction::type, SyncedCodec::streamCodec);

    InteractionResult use(Level level, Player player, InteractionHand hand);

    static BlockHitResult getHitResult(Level level, Player player, ClipContext.Block block, ClipContext.Fluid fluid) {
        Vec3 from = player.getEyePosition();
        Vec3 to = from.add(Player.calculateViewVector(player.getXRot(), player.getYRot()).scale(player.blockInteractionRange()));
        return level.clip(new ClipContext(from, to, block, fluid, player));
    }
}
