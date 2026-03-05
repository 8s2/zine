package com.eightsidedsquare.zine.common.item;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ZineItemProperties {

    default Item.Properties zine$nameColor(int color) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$equippable(Equippable component) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$equippable(Equippable.Builder builder) {
        return this.zine$equippable(builder.build());
    }

    default Item.Properties zine$container() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$glintOverride() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$consumable(Consumable component) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$consumable(net.minecraft.world.item.component.Consumable.Builder builder) {
        return this.zine$consumable(builder.build());
    }

    default Item.Properties zine$entityBucket() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$tool(Tool component) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$potion(float durationScale) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$potion() {
        return this.zine$potion(1);
    }

    default Item.Properties zine$breakSound(Holder<SoundEvent> soundEvent) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$weapon(int itemDamagePerAttack, float disableBlockingForSeconds) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$weapon(int itemDamagePerAttack) {
        return this.zine$weapon(itemDamagePerAttack, 0);
    }

    default Item.Properties zine$bannerPatterns(TagKey<BannerPattern> bannerPatternTagKey) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$glider() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$tooltipStyle(Identifier texture) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$damageResistant(TagKey<DamageType> damageTypeTagKey) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$lore(List<Component> lines) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Item.Properties zine$lore(Component... lines) {
        return this.zine$lore(List.of(lines));
    }

    @ApiStatus.Internal
    @Nullable
    default ArmorType zine$getArmorType() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
