package com.eightsidedsquare.zinetest.mixin.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Function;

@Mixin(Model.class)
public abstract class ModelMixin {

    @Shadow @Final @Mutable
    protected Function<Identifier, RenderType> renderType;
}
