package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.model.ZineResolvableModelResolver;
import net.minecraft.client.resources.model.ResolvableModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResolvableModel.Resolver.class)
public interface ResolvableModelResolverMixin extends ZineResolvableModelResolver {
}
