package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedCompositeModel;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.client.renderer.item.CompositeModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(CompositeModel.Unbaked.class)
public abstract class UnbakedCompositeModelMixin implements ZineUnbakedCompositeModel {

    @Shadow @Final @Mutable
    private List<net.minecraft.client.renderer.item.ItemModel.Unbaked> models;

    @Override
    public void zine$addModel(net.minecraft.client.renderer.item.ItemModel.Unbaked model) {
        this.models = ZineUtil.addOrUnfreeze(this.models, model);
    }

    @Override
    public void zine$addModels(List<net.minecraft.client.renderer.item.ItemModel.Unbaked> models) {
        this.models = ZineUtil.addAllOrUnfreeze(this.models, models);
    }

    @Override
    public void zine$setModels(List<net.minecraft.client.renderer.item.ItemModel.Unbaked> models) {
        this.models = models;
    }
}
