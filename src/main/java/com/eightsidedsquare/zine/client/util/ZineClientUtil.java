package com.eightsidedsquare.zine.client.util;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;

public final class ZineClientUtil {

    public static Mesh bake(MutableMesh builder) {
        Mesh mesh = builder.immutableCopy();
        builder.clear();
        return mesh.size() == 0 ? EmptyMesh.INSTANCE : mesh;
    }

    private ZineClientUtil() {
    }
}
