package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.item.useonaction.ItemUseOnAction;
import com.eightsidedsquare.zine.common.util.ClipContextSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public record UseOnActionUse(
        ItemUseOnAction useOnAction,
        ClipContext.Block blockShapeHandling,
        ClipContext.Fluid fluidShapeHandling
) implements ItemUseAction {
    public static final SyncedCodec<UseOnActionUse> TYPE = SyncedCodec.create(
            ItemUseOnAction.CODEC.fieldOf("use_on_action"),
            ItemUseOnAction.STREAM_CODEC,
            UseOnActionUse::useOnAction,
            ClipContextSerialization.BLOCK_CODEC.optionalFieldOf("block_shape_handling", ClipContext.Block.OUTLINE),
            ClipContextSerialization.BLOCK_STREAM_CODEC,
            UseOnActionUse::blockShapeHandling,
            ClipContextSerialization.FLUID_CODEC.optionalFieldOf("fluid_shape_handling", ClipContext.Fluid.NONE),
            ClipContextSerialization.FLUID_STREAM_CODEC,
            UseOnActionUse::fluidShapeHandling,
            UseOnActionUse::new
    );

    public UseOnActionUse(ItemUseOnAction useOnAction) {
        this(useOnAction, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        BlockHitResult hitResult = ItemUseAction.getHitResult(level, player, this.blockShapeHandling, this.fluidShapeHandling);
        return this.useOnAction.useOn(new UseOnContext(player, hand, hitResult));
    }

    @Override
    public SyncedCodec<? extends ItemUseAction> type() {
        return TYPE;
    }
}
