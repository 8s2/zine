package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.common.item.ondestroyedaction.OnDestroyedItemAction;
import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.item.useonaction.UseOnItemAction;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 1))
    private MutableComponent zine$applyTextColorToHoverable(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        return this.zine$applyTextColor(text, formatting, original);
    }

    @WrapOperation(method = "getStyledHoverName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
    private MutableComponent zine$applyTextColorToName(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        return this.zine$applyTextColor(text, formatting, original);
    }

    @Unique
    private MutableComponent zine$applyTextColor(MutableComponent text, ChatFormatting formatting, Operation<MutableComponent> original) {
        if(this.has(ZineDataComponents.ITEM_NAME_COLOR)) {
            return text.withColor(this.getOrDefault(ZineDataComponents.ITEM_NAME_COLOR, -1));
        }else {
            return original.call(text, formatting);
        }
    }

    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult zine$useItemAction(InteractionResult original, Level level, Player player, InteractionHand hand) {
        if (original == InteractionResult.PASS) {
            UseItemAction useItemAction = this.get(ZineDataComponents.USE_ITEM_ACTION);
            if (useItemAction != null) {
                return useItemAction.use(level, player, hand);
            }
        }
        return original;
    }

    @ModifyExpressionValue(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult zine$useOnItemAction(InteractionResult original, UseOnContext context) {
        if (original == InteractionResult.PASS) {
            UseOnItemAction useOnItemAction = this.get(ZineDataComponents.USE_ON_ITEM_ACTION);
            if (useOnItemAction != null) {
                return useOnItemAction.useOn(context);
            }
        }
        return original;
    }

    @Inject(method = "onDestroyed", at = @At("HEAD"))
    private void zine$onDestroyedItemAction(ItemEntity itemEntity, CallbackInfo ci) {
        OnDestroyedItemAction onDestroyedItemAction = this.get(ZineDataComponents.ON_DESTROYED_ITEM_ACTION);
        if (onDestroyedItemAction != null) {
            onDestroyedItemAction.onDestroyed(itemEntity);
        }
    }
}
