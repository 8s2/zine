package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedConditionalItemModel;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ConditionalItemModel.Unbaked.class)
public abstract class UnbakedConditionItemModelMixin implements ZineUnbakedConditionalItemModel {

    @Shadow @Final @Mutable
    private ConditionalItemModelProperty property;
    @Shadow @Final @Mutable
    private ItemModel.Unbaked onTrue;
    @Shadow @Final @Mutable
    private ItemModel.Unbaked onFalse;

    @Override
    public void zine$setProperty(ConditionalItemModelProperty property) {
        this.property = property;
    }

    @Override
    public void zine$setTrueModel(ItemModel.Unbaked model) {
        this.onTrue = model;
    }

    @Override
    public void zine$setFalseModel(ItemModel.Unbaked model) {
        this.onFalse = model;
    }
}
