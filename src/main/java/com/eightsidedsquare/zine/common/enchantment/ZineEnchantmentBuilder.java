package com.eightsidedsquare.zine.common.enchantment;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

public interface ZineEnchantmentBuilder {

    default Enchantment.EnchantmentDefinition zine$getDefinition() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Enchantment.Builder zine$definition(Enchantment.EnchantmentDefinition definition) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default HolderSet<Enchantment> zine$getExclusiveSet() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default net.minecraft.core.component.DataComponentMap.Builder zine$getEffectMap() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Enchantment.Builder zine$effectMap(net.minecraft.core.component.DataComponentMap.Builder effectMap) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    @Nullable
    default <T> T zine$getEffect(DataComponentType<T> type) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
