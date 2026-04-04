package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.item.ZineUnbakedSpecialModelWrapper;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(SpecialModelWrapper.Unbaked.class)
public abstract class UnbakedSpecialModelWrapperMixin implements ZineUnbakedSpecialModelWrapper {

    @Shadow @Final @Mutable
    private Identifier base;
    @Shadow @Final @Mutable
    private Optional<Transformation> transformation;
    @Shadow @Final @Mutable
    private SpecialModelRenderer.Unbaked<?> specialModel;

    @Override
    public void zine$setBase(Identifier base) {
        this.base = base;
    }

    @Override
    public void zine$setModel(SpecialModelRenderer.Unbaked<?> model) {
        this.specialModel = model;
    }

    @Override
    public void zine$setTransformation(@Nullable Transformation transformation) {
        this.transformation = Optional.ofNullable(transformation);
    }
}
