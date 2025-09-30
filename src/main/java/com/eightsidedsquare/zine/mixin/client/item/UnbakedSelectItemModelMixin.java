package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedSelectItemModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.SelectProperty;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SelectItemModel.Unbaked.class)
public abstract class UnbakedSelectItemModelMixin implements ZineUnbakedSelectItemModel {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Shadow @Final @Mutable
    private Optional<ItemModel.Unbaked> fallback;

    @Shadow @Final @Mutable
    private SelectItemModel.UnbakedSwitch<?, ?> unbakedSwitch;

    @Override
    public void zine$setFallback(ItemModel.@Nullable Unbaked fallback) {
        this.fallback = Optional.ofNullable(fallback);
    }

    @Override
    public void zine$setSwitch(SelectItemModel.UnbakedSwitch<?, ?> unbakedSwitch) {
        this.unbakedSwitch = unbakedSwitch;
    }

    @Override
    public <P extends SelectProperty<T>, T> void zine$addCases(SelectProperty.Type<P, T> type, List<SelectItemModel.SwitchCase<T>> switchCases) {
        this.zine$handleUnbakedSwitch(type, unbakedSwitch ->
                unbakedSwitch.zine$addCases(switchCases)
        );
    }

    @Override
    public <P extends SelectProperty<T>, T> void zine$addCase(SelectProperty.Type<P, T> type, List<T> values, ItemModel.Unbaked model) {
        this.zine$handleUnbakedSwitch(type, unbakedSwitch ->
                unbakedSwitch.zine$addCase(new SelectItemModel.SwitchCase<>(values, model))
        );
    }

    @SuppressWarnings("unchecked")
    @Unique
    private <P extends SelectProperty<T>, T> void zine$handleUnbakedSwitch(SelectProperty.Type<P, T> type, Consumer<SelectItemModel.UnbakedSwitch<P, T>> consumer) {
        if(this.unbakedSwitch.property().getType().equals(type)) {
            consumer.accept((SelectItemModel.UnbakedSwitch<P, T>) this.unbakedSwitch);
        }
    }
}
