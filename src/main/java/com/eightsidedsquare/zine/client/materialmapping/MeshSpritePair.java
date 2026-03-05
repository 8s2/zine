package com.eightsidedsquare.zine.client.materialmapping;

import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadView;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.function.Consumer;

public record MeshSpritePair(Mesh mesh, TextureAtlasSprite sprite) implements Mesh {

    public MeshSpritePair(MappableModel mappableModel, MaterialMapping.Baked sprites, String spriteName, TextureAtlasSprite missingSprite) {
        Renderer renderer = Renderer.get();
        MutableMesh builder = renderer.mutableMesh();
        QuadEmitter emitter = builder.emitter();
        mappableModel.outputTo(emitter, sprites);
        this(
                builder.immutableCopy(),
                sprites.mappings().isEmpty() ? missingSprite :
                        sprites.mappings().getOrDefault(spriteName, sprites.mappings().values().iterator().next())
        );
    }

    @Override
    public int size() {
        return this.mesh.size();
    }

    @Override
    public void forEach(Consumer<? super QuadView> action) {
        this.mesh.forEach(action);
    }

    @Override
    public void outputTo(QuadEmitter emitter) {
        this.mesh.outputTo(emitter);
    }

    public void outputTo(ItemStackRenderState.LayerRenderState layerState) {
        this.outputTo(layerState.emitter());
        layerState.setParticleIcon(this.sprite);
    }

}
