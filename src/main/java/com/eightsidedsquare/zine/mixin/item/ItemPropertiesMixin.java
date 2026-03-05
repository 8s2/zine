package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.common.item.ZineItemProperties;
import com.eightsidedsquare.zine.core.ZineDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
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

    @Override
    public Item.Properties zine$nameColor(int color) {
        return this.component(ZineDataComponents.ITEM_NAME_COLOR, color);
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
        return this.component(DataComponents.PROVIDES_BANNER_PATTERNS, bannerPatternTagKey);
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
        return this.component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(damageTypeTagKey));
    }

    @Override
    public Item.Properties zine$lore(List<Component> lines) {
        return this.component(DataComponents.LORE, new ItemLore(lines));
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
