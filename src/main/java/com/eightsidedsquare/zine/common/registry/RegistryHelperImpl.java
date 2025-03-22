package com.eightsidedsquare.zine.common.registry;

import net.minecraft.util.Identifier;

public class RegistryHelperImpl implements RegistryHelper {

    private final String namespace;

    RegistryHelperImpl(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public Identifier id(String name) {
        return Identifier.of(this.namespace, name);
    }
}
