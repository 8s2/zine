package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.common.item.ZineItem;
import com.eightsidedsquare.zine.common.item.destroyedaction.ItemDestroyedAction;
import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltip;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.ItemUseAction;
import com.eightsidedsquare.zine.common.item.useduration.ItemUseDuration;
import com.eightsidedsquare.zine.common.item.useonaction.ItemUseOnAction;
import com.eightsidedsquare.zine.common.item.usetickaction.ItemUseTickAction;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin implements ZineItem {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ModifyReturnValue(method = "getTooltipImage", at = @At("TAIL"))
    private Optional<TooltipComponent> zine$getTooltipImage(Optional<TooltipComponent> original, ItemStack itemStack) {
        TooltipImage tooltipImage = itemStack.get(ZineDataComponents.TOOLTIP_IMAGE);
        if (tooltipImage != null) {
            TooltipDisplay display = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
            if (display.shows(ZineDataComponents.TOOLTIP_IMAGE) && tooltipImage.canShow(display)) {
                TooltipComponent tooltip = tooltipImage.getTooltip(itemStack, display);
                if (tooltip != null) {
                    return original
                            .<TooltipComponent>map(tooltipComponent -> new CompositeTooltip(
                                            ImmutableList.<TooltipComponent>builder()
                                                    .add(tooltipComponent)
                                                    .add(tooltip)
                                                    .build()
                                    )
                            ).or(() -> Optional.of(tooltip));
                }
            }
        }
        return original;
    }

    @Definition(id = "getItemInHand", method = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;")
    @Expression("? = ?.getItemInHand(?)")
    @Inject(method = "use", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER), cancellable = true)
    private void zine$itemUseAction(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "stack") ItemStack stack) {
        ItemUseAction action = stack.get(ZineDataComponents.USE_ACTION);
        if (action != null) {
            InteractionResult result = action.use(level, player, hand);
            if (result != InteractionResult.PASS) {
                cir.setReturnValue(result);
            }
        }
    }

    @Definition(id = "getItemInHand", method = "Lnet/minecraft/world/item/context/UseOnContext;getItemInHand()Lnet/minecraft/world/item/ItemStack;")
    @Expression("? = ?.getItemInHand()")
    @Inject(method = "useOn", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER), cancellable = true)
    private void zine$itemUseOnAction(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "stack") ItemStack stack) {
        ItemUseOnAction action = stack.get(ZineDataComponents.USE_ON_ACTION);
        if (action != null) {
            InteractionResult result = action.useOn(context);
            if (result != InteractionResult.PASS) {
                cir.setReturnValue(result);
            }
        }
    }

    @Inject(method = "onDestroyed", at = @At("HEAD"))
    private void zine$itemDestroyedAction(ItemEntity itemEntity, CallbackInfo ci) {
        ItemDestroyedAction action = itemEntity.getItem().get(ZineDataComponents.DESTROYED_ACTION);
        if (action != null) {
            action.onDestroyed(itemEntity);
        }
    }

    @Inject(method = "onUseTick", at = @At("HEAD"))
    private void zine$itemUseTickAction(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining, CallbackInfo ci) {
        ItemUseTickAction action = itemStack.get(ZineDataComponents.USE_TICK_ACTION);
        if (action != null) {
            action.onUseTick(level, livingEntity, itemStack, ticksRemaining);
        }
    }

    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    private void zine$itemUseDuration(ItemStack itemStack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        ItemUseDuration duration = itemStack.get(ZineDataComponents.USE_DURATION);
        if (duration != null) {
            cir.setReturnValue(duration.getUseDuration(itemStack, user));
        }
    }

    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void zine$itemUseAnimation(ItemStack itemStack, CallbackInfoReturnable<ItemUseAnimation> cir) {
        ItemUseAnimation animation = itemStack.get(ZineDataComponents.USE_ANIMATION);
        if (animation != null) {
            cir.setReturnValue(animation);
        }
    }

    @ModifyReturnValue(method = "useOnRelease", at = @At("RETURN"))
    private boolean zine$usedOnRelease(boolean original, ItemStack itemStack) {
        return original || itemStack.has(ZineDataComponents.USED_ON_RELEASE);
    }
}
