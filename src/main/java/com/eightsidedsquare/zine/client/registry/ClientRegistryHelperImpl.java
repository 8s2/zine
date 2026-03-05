package com.eightsidedsquare.zine.client.registry;

import net.minecraft.resources.Identifier;

public class ClientRegistryHelperImpl implements ClientRegistryHelper {

    private final String namespace;

    ClientRegistryHelperImpl(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(this.namespace, name);
    }
}
