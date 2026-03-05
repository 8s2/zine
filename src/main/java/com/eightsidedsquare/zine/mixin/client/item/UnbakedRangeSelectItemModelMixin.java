package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedRangeSelectItemModel;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;

@Mixin(RangeSelectItemModel.Unbaked.class)
public abstract class UnbakedRangeSelectItemModelMixin implements ZineUnbakedRangeSelectItemModel {

    @Shadow @Final @Mutable
    private RangeSelectItemModelProperty property;
    @Shadow @Final @Mutable
    private float scale;
    @Shadow @Final @Mutable
    private List<RangeSelectItemModel.Entry> entries;
    @Shadow @Final @Mutable
    private Optional<net.minecraft.client.renderer.item.ItemModel.Unbaked> fallback;

    @Override
    public void zine$setProperty(RangeSelectItemModelProperty property) {
        this.property = property;
    }

    @Override
    public void zine$setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public void zine$setFallback(@Nullable net.minecraft.client.renderer.item.ItemModel.Unbaked fallback) {
        this.fallback = Optional.ofNullable(fallback);
    }

    @Override
    public void zine$addEntry(RangeSelectItemModel.Entry entry) {
        this.entries = ZineUtil.addOrUnfreeze(this.entries, entry);
    }

    @Override
    public void zine$addEntries(List<RangeSelectItemModel.Entry> entries) {
        this.entries = ZineUtil.addAllOrUnfreeze(this.entries, entries);
    }
}
