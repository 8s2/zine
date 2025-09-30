package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineSelectItemModelUnbakedSwitch;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.SelectProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(SelectItemModel.UnbakedSwitch.class)
public abstract class SelectItemModelUnbakedSwitchMixin<P extends SelectProperty<T>, T> implements ZineSelectItemModelUnbakedSwitch<P, T> {

    @Shadow @Final @Mutable
    private P property;

    @Shadow @Final @Mutable private List<SelectItemModel.SwitchCase<T>> cases;

    @Override
    public void zine$setProperty(P property) {
        this.property = property;
    }

    @Override
    public void zine$setCases(List<SelectItemModel.SwitchCase<T>> cases) {
        this.cases = cases;
    }

    @Override
    public void zine$addCases(List<SelectItemModel.SwitchCase<T>> cases) {
        this.cases = ZineUtil.addAllOrUnfreeze(this.cases, cases);
    }

    @Override
    public void zine$addCase(SelectItemModel.SwitchCase<T> switchCase) {
        this.cases = ZineUtil.addOrUnfreeze(this.cases, switchCase);
    }
}
