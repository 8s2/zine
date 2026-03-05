package com.eightsidedsquare.zine.client.block;

import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.resources.model.ModelBaker;

public interface CustomMeshUnbakedBlockStateModel extends CustomUnbakedBlockStateModel {

    int getMeshCount();

    BlockStateModel bake(MutableMesh builder, QuadEmitter emitter, Mesh[] meshes, ModelBaker baker);

    @Override
    default BlockStateModel bake(ModelBaker baker) {
        MutableMesh builder = Renderer.get().mutableMesh();
        QuadEmitter emitter = builder.emitter();
        Mesh[] meshes = new Mesh[this.getMeshCount()];
        return this.bake(builder, emitter, meshes, baker);
    }

    @Override
    default void resolveDependencies(Resolver resolver) {

    }
}
