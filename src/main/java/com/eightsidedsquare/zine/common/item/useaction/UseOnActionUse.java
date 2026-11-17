package com.eightsidedsquare.zine.common.item.useaction;

import com.eightsidedsquare.zine.common.item.useonaction.UseOnItemAction;
import com.eightsidedsquare.zine.common.util.ClipContextSerialization;
import com.eightsidedsquare.zine.common.util.codec.SyncedCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public record UseOnActionUse(
        UseOnItemAction useOnAction,
        ClipContext.Block blockShapeHandling,
        ClipContext.Fluid fluidShapeHandling
) implements UseItemAction {
    public static final SyncedCodec<UseOnActionUse> TYPE = new SyncedCodec<>(
            RecordCodecBuilder.mapCodec(i -> i.group(
                    UseOnItemAction.CODEC.fieldOf("use_on_action").forGetter(UseOnActionUse::useOnAction),
                    ClipContextSerialization.BLOCK_CODEC.optionalFieldOf("block_shape_handling", ClipContext.Block.OUTLINE)
                            .forGetter(UseOnActionUse::blockShapeHandling),
                    ClipContextSerialization.FLUID_CODEC.optionalFieldOf("fluid_shape_handling", ClipContext.Fluid.NONE)
                            .forGetter(UseOnActionUse::fluidShapeHandling)
            ).apply(i, UseOnActionUse::new)),
            StreamCodec.composite(
                    UseOnItemAction.STREAM_CODEC,
                    UseOnActionUse::useOnAction,
                    ClipContextSerialization.BLOCK_STREAM_CODEC,
                    UseOnActionUse::blockShapeHandling,
                    ClipContextSerialization.FLUID_STREAM_CODEC,
                    UseOnActionUse::fluidShapeHandling,
                    UseOnActionUse::new
            )
    );

    public UseOnActionUse(UseOnItemAction useOnAction) {
        this(useOnAction, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        BlockHitResult hitResult = UseItemAction.getHitResult(level, player, this.blockShapeHandling, this.fluidShapeHandling);
        return this.useOnAction.useOn(new UseOnContext(player, hand, hitResult));
    }

    @Override
    public SyncedCodec<? extends UseItemAction> type() {
        return TYPE;
    }
}
