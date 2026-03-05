package com.eightsidedsquare.zine.client.model;

import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingStorage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

public interface ZineMaterialBaker {

    default Material.Baked zine$get(Material material) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default Material.Baked zine$get(Identifier sprite, boolean forceTranslucent) {
        return this.zine$get(new Material(sprite, forceTranslucent));
    }

    default Material.Baked zine$get(Identifier sprite) {
        return this.zine$get(new Material(sprite));
    }

    default Material.Baked zine$getMissing() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default TextureAtlasSprite zine$getSprite(Material material) {
        return this.zine$get(material).sprite();
    }

    default TextureAtlasSprite zine$getSprite(Identifier sprite, boolean forceTranslucent) {
        return this.zine$get(sprite, forceTranslucent).sprite();
    }

    default TextureAtlasSprite zine$getSprite(Identifier sprite) {
        return this.zine$get(sprite).sprite();
    }

    default TextureAtlasSprite zine$getMissingSprite() {
        return this.zine$getMissing().sprite();
    }

    default MaterialMappingStorage zine$getMappings() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    @ApiStatus.Internal
    default void zine$setMappings(MaterialMappingStorage mappings) {

    }

}
