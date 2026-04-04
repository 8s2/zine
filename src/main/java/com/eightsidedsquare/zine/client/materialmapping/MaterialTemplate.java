package com.eightsidedsquare.zine.client.materialmapping;

import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

@FunctionalInterface
public interface MaterialTemplate {
    Material create(String namespace, String path, String prefix);

    default Material create(Identifier id) {
        return create(id.getNamespace(), id.getPath(), "");
    }

    default Material create(ResourceKey<?> key) {
        return create(key.identifier().getNamespace(), key.identifier().getPath(), key.registry().getPath() + "/");
    }

    static MaterialTemplate of(String template, boolean forceTranslucent) {
        return (namespace, path, prefix) -> new Material(
                Identifier.fromNamespaceAndPath(namespace, prefix + String.format(template, path)),
                forceTranslucent
        );
    }

    static MaterialTemplate of(String template) {
        return of(template, false);
    }


}
