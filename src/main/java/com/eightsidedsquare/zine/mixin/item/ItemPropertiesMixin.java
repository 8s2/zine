package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.common.item.ZineItemProperties;
import com.eightsidedsquare.zine.common.item.destroyedaction.ItemDestroyedAction;
import com.eightsidedsquare.zine.common.item.tooltip.TooltipImage;
import com.eightsidedsquare.zine.common.item.useaction.ItemUseAction;
import com.eightsidedsquare.zine.common.item.useduration.ItemUseDuration;
import com.eightsidedsquare.zine.common.item.useonaction.ItemUseOnAction;
import com.eightsidedsquare.zine.common.item.usetickaction.ItemUseTickAction;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.Properties.class)
public abstract class ItemPropertiesMixin implements ZineItemProperties {
    @Unique
    @Nullable
    private ArmorType armorType;

    @Shadow public abstract <T> Item.Properties component(DataComponentType<T> type, T value);

    @Shadow
    public abstract <T> Item.Properties delayedComponent(DataComponentType<T> type, DataComponentInitializers.SingleComponentInitializer<T> initializer);

    @Override
    public Item.Properties zine$nameColor(int color) {
        return this.component(ZineDataComponents.ITEM_NAME_COLOR, color);
    }

    @Override
    public Item.Properties zine$useAction(ItemUseAction useAction) {
        return this.component(ZineDataComponents.USE_ACTION, useAction);
    }

    @Override
    public Item.Properties zine$useAction(DataComponentInitializers.SingleComponentInitializer<ItemUseAction> useAction) {
        return this.delayedComponent(ZineDataComponents.USE_ACTION, useAction);
    }

    @Override
    public Item.Properties zine$useOnAction(ItemUseOnAction useOnAction) {
        return this.component(ZineDataComponents.USE_ON_ACTION, useOnAction);
    }

    @Override
    public Item.Properties zine$useOnAction(DataComponentInitializers.SingleComponentInitializer<ItemUseOnAction> useOnAction) {
        return this.delayedComponent(ZineDataComponents.USE_ON_ACTION, useOnAction);
    }

    @Override
    public Item.Properties zine$destroyedAction(ItemDestroyedAction destroyedAction) {
        return this.component(ZineDataComponents.DESTROYED_ACTION, destroyedAction);
    }

    @Override
    public Item.Properties zine$destroyedAction(DataComponentInitializers.SingleComponentInitializer<ItemDestroyedAction> destroyedAction) {
        return this.delayedComponent(ZineDataComponents.DESTROYED_ACTION, destroyedAction);
    }

    @Override
    public Item.Properties zine$useTickAction(ItemUseTickAction useTickAction) {
        return this.component(ZineDataComponents.USE_TICK_ACTION, useTickAction);
    }

    @Override
    public Item.Properties zine$useTickAction(DataComponentInitializers.SingleComponentInitializer<ItemUseTickAction> useTickAction) {
        return this.delayedComponent(ZineDataComponents.USE_TICK_ACTION, useTickAction);
    }

    @Override
    public Item.Properties zine$useDuration(ItemUseDuration useDuration) {
        return this.component(ZineDataComponents.USE_DURATION, useDuration);
    }

    @Override
    public Item.Properties zine$useDuration(DataComponentInitializers.SingleComponentInitializer<ItemUseDuration> useDuration) {
        return this.delayedComponent(ZineDataComponents.USE_DURATION, useDuration);
    }

    @Override
    public Item.Properties zine$useAnimation(ItemUseAnimation animation) {
        return this.component(ZineDataComponents.USE_ANIMATION, animation);
    }

    @Override
    public Item.Properties zine$usedOnRelease() {
        return this.component(ZineDataComponents.USED_ON_RELEASE, Unit.INSTANCE);
    }

    @Override
    public Item.Properties zine$equippable(Equippable component) {
        return this.component(DataComponents.EQUIPPABLE, component);
    }

    @Override
    public Item.Properties zine$container() {
        return this.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    }

    @Override
    public Item.Properties zine$glintOverride() {
        return this.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }

    @Override
    public Item.Properties zine$consumable(Consumable component) {
        return this.component(DataComponents.CONSUMABLE, component);
    }

    @Override
    public Item.Properties zine$entityBucket() {
        return this.component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
    }

    @Override
    public Item.Properties zine$tool(Tool component) {
        return this.component(DataComponents.TOOL, component);
    }

    @Override
    public Item.Properties zine$potion(float durationScale) {
        if(durationScale != 1) {
            this.component(DataComponents.POTION_DURATION_SCALE, durationScale);
        }
        return this.component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    @Override
    public Item.Properties zine$breakSound(Holder<SoundEvent> soundEvent) {
        return this.component(DataComponents.BREAK_SOUND, soundEvent);
    }

    @Override
    public Item.Properties zine$weapon(int itemDamagePerAttack, float disableBlockingForSeconds) {
        return this.component(DataComponents.WEAPON, new Weapon(itemDamagePerAttack, disableBlockingForSeconds));
    }

    @Override
    public Item.Properties zine$bannerPatterns(TagKey<BannerPattern> bannerPatternTagKey) {
        return this.delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(bannerPatternTagKey));
    }

    @Override
    public Item.Properties zine$glider() {
        return this.component(DataComponents.GLIDER, Unit.INSTANCE);
    }

    @Override
    public Item.Properties zine$tooltipStyle(Identifier texture) {
        return this.component(DataComponents.TOOLTIP_STYLE, texture);
    }

    @Override
    public Item.Properties zine$damageResistant(TagKey<DamageType> damageTypeTagKey) {
        return this.delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(damageTypeTagKey)));
    }

    @Override
    public Item.Properties zine$lore(List<Component> lines) {
        return this.component(DataComponents.LORE, new ItemLore(lines));
    }

    @Override
    public Item.Properties zine$tooltipImage(TooltipImage image) {
        return this.component(ZineDataComponents.TOOLTIP_IMAGE, image);
    }

    @Override
    public @Nullable ArmorType zine$getArmorType() {
        return this.armorType;
    }

    @Inject(method = "humanoidArmor", at = @At("HEAD"))
    private void zine$storeArmorType(ArmorMaterial material, ArmorType type, CallbackInfoReturnable<Item.Properties> cir) {
        this.armorType = type;
    }
}
