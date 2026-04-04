package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineItemPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Optional;

@Mixin(ItemPredicate.class)
public abstract class ItemPredicateMixin implements ZineItemPredicate {

    @Shadow @Final @Mutable
    private Optional<HolderSet<Item>> items;

    @Shadow @Final @Mutable
    private MinMaxBounds.Ints count;

    @Shadow @Final @Mutable
    private DataComponentMatchers components;

    @Override
    public void zine$setItems(@Nullable HolderSet<Item> items) {
        this.items = Optional.ofNullable(items);
    }

    @Override
    public void zine$addItem(Item item) {
        if(this.items.isPresent()) {
            this.items = Optional.of(ZineUtil.mergeValue(this.items.get(), BuiltInRegistries.ITEM::wrapAsHolder, item));
            return;
        }
        this.items = Optional.of(HolderSet.direct(BuiltInRegistries.ITEM::wrapAsHolder, item));
    }

    @Override
    public void zine$addItems(Collection<Item> items) {
        if(this.items.isPresent()) {
            this.items = Optional.of(ZineUtil.mergeValues(this.items.get(), BuiltInRegistries.ITEM::wrapAsHolder, items));
            return;
        }
        this.items = Optional.of(HolderSet.direct(BuiltInRegistries.ITEM::wrapAsHolder, items));
    }

    @Override
    public void zine$setCount(MinMaxBounds.Ints count) {
        this.count = count;
    }

    @Override
    public void zine$setComponents(DataComponentMatchers components) {
        this.components = components;
    }
}
