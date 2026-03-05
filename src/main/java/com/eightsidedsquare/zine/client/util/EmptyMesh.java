package com.eightsidedsquare.zine.client.util;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadView;

import java.util.function.Consumer;

public final class EmptyMesh implements Mesh {

    public static EmptyMesh INSTANCE = new EmptyMesh();

    private EmptyMesh() {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void forEach(Consumer<? super QuadView> action) {

    }

    @Override
    public void outputTo(QuadEmitter emitter) {

    }
}