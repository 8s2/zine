package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.enchantment.ZineEnchantmentBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Enchantment.Builder.class)
public abstract class EnchantmentBuilderMixin implements ZineEnchantmentBuilder {

    @Shadow @Final @Mutable
    private Enchantment.EnchantmentDefinition definition;

    @Shadow
    private HolderSet<Enchantment> exclusiveSet;

    @Shadow @Final @Mutable
    private net.minecraft.core.component.DataComponentMap.Builder effectMapBuilder;

    @Override
    public Enchantment.EnchantmentDefinition zine$getDefinition() {
        return this.definition;
    }

    @Override
    public Enchantment.Builder zine$definition(Enchantment.EnchantmentDefinition definition) {
        this.definition = definition;
        return (Enchantment.Builder) (Object) this;
    }

    @Override
    public HolderSet<Enchantment> zine$getExclusiveSet() {
        return this.exclusiveSet;
    }

    @Override
    public net.minecraft.core.component.DataComponentMap.Builder zine$getEffectMap() {
        return this.effectMapBuilder;
    }

    @Override
    public Enchantment.Builder zine$effectMap(net.minecraft.core.component.DataComponentMap.Builder effectMap) {
        this.effectMapBuilder = effectMap;
        return (Enchantment.Builder) (Object) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable T zine$getEffect(DataComponentType<T> type) {
        return (T) this.effectMapBuilder.map.get(type);
    }

}
