package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.common.item.ZineItem;
import com.eightsidedsquare.zine.common.item.ondestroyedaction.OnDestroyedItemAction;
import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltip;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.UseItemAction;
import com.eightsidedsquare.zine.common.item.useonaction.UseOnItemAction;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    private void zine$useItemAction(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "stack") ItemStack stack) {
        UseItemAction useItemAction = stack.get(ZineDataComponents.USE_ITEM_ACTION);
        if (useItemAction != null) {
            InteractionResult result = useItemAction.use(level, player, hand);
            if (result != InteractionResult.PASS) {
                cir.setReturnValue(result);
            }
        }
    }

    @Definition(id = "getItemInHand", method = "Lnet/minecraft/world/item/context/UseOnContext;getItemInHand()Lnet/minecraft/world/item/ItemStack;")
    @Expression("? = ?.getItemInHand()")
    @Inject(method = "useOn", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER), cancellable = true)
    private void zine$useOnItemAction(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "stack") ItemStack stack) {
        UseOnItemAction useOnItemAction = stack.get(ZineDataComponents.USE_ON_ITEM_ACTION);
        if (useOnItemAction != null) {
            InteractionResult result = useOnItemAction.useOn(context);
            if (result != InteractionResult.PASS) {
                cir.setReturnValue(result);
            }
        }
    }

    @Inject(method = "onDestroyed", at = @At("HEAD"))
    private void zine$onDestroyedItemAction(ItemEntity itemEntity, CallbackInfo ci) {
        OnDestroyedItemAction onDestroyedItemAction = itemEntity.getItem().get(ZineDataComponents.ON_DESTROYED_ITEM_ACTION);
        if (onDestroyedItemAction != null) {
            onDestroyedItemAction.onDestroyed(itemEntity);
        }
    }
}
