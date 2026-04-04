package com.eightsidedsquare.zine.client.materialmapping;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadView;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;

import java.util.function.Consumer;

public record MappedMesh(Mesh mesh, Material.Baked particleMaterial, @BakedQuad.MaterialFlags int materialFlags) implements Mesh {

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
        layerState.setParticleMaterial(this.particleMaterial);
    }

    public boolean hasMaterialFlag(@BakedQuad.MaterialFlags int flag) {
        return (this.materialFlags & flag) != 0;
    }
}
