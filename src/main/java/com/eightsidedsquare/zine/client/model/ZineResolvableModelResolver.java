package com.eightsidedsquare.zine.client.model;

import net.minecraft.resources.Identifier;

public interface ZineResolvableModelResolver {

    default void zine$markMappableModelDependency(Identifier id) {

    }

}
