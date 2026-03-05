package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedCuboidItemModelWrapper;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.mojang.math.Transformation;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;

@Mixin(CuboidItemModelWrapper.Unbaked.class)
public abstract class UnbakedCuboidItemModelWrapperMixin implements ZineUnbakedCuboidItemModelWrapper {

    @Shadow @Final @Mutable
    private Identifier model;
    @Shadow @Final @Mutable
    private Optional<Transformation> transformation;
    @Shadow @Final @Mutable
    private List<ItemTintSource> tints;

    @Override
    public void zine$addTint(ItemTintSource tint) {
        this.tints = ZineUtil.addOrUnfreeze(this.tints, tint);
    }

    @Override
    public void zine$addTints(List<ItemTintSource> tints) {
        this.tints = ZineUtil.addAllOrUnfreeze(this.tints, tints);
    }

    @Override
    public void zine$setTints(List<ItemTintSource> tints) {
        this.tints = tints;
    }

    @Override
    public void zine$setModel(Identifier model) {
        this.model = model;
    }

    @Override
    public void zine$setTransformation(@Nullable Transformation transformation) {
        this.transformation = Optional.ofNullable(transformation);
    }
}
