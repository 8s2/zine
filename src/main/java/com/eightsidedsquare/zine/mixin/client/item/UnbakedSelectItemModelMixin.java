package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SelectItemModel.Unbaked.class)
public abstract class UnbakedSelectItemModelMixin implements ZineUnbakedSelectItemModel {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Shadow @Final @Mutable
    private Optional<net.minecraft.client.renderer.item.ItemModel.Unbaked> fallback;

    @Shadow @Final @Mutable
    private SelectItemModel.UnbakedSwitch<?, ?> unbakedSwitch;

    @Override
    public void zine$setFallback(@Nullable net.minecraft.client.renderer.item.ItemModel.Unbaked fallback) {
        this.fallback = Optional.ofNullable(fallback);
    }

    @Override
    public void zine$setSwitch(SelectItemModel.UnbakedSwitch<?, ?> unbakedSwitch) {
        this.unbakedSwitch = unbakedSwitch;
    }

    @Override
    public <P extends SelectItemModelProperty<T>, T> void zine$addCases(SelectItemModelProperty.Type<P, T> type, List<SelectItemModel.SwitchCase<T>> switchCases) {
        this.zine$handleUnbakedSwitch(type, unbakedSwitch ->
                unbakedSwitch.zine$addCases(switchCases)
        );
    }

    @Override
    public <P extends SelectItemModelProperty<T>, T> void zine$addCase(SelectItemModelProperty.Type<P, T> type, List<T> values, net.minecraft.client.renderer.item.ItemModel.Unbaked model) {
        this.zine$handleUnbakedSwitch(type, unbakedSwitch ->
                unbakedSwitch.zine$addCase(new SelectItemModel.SwitchCase<>(values, model))
        );
    }

    @SuppressWarnings("unchecked")
    @Unique
    private <P extends SelectItemModelProperty<T>, T> void zine$handleUnbakedSwitch(SelectItemModelProperty.Type<P, T> type, Consumer<SelectItemModel.UnbakedSwitch<P, T>> consumer) {
        if(this.unbakedSwitch.property().type().equals(type)) {
            consumer.accept((SelectItemModel.UnbakedSwitch<P, T>) this.unbakedSwitch);
        }
    }
}
