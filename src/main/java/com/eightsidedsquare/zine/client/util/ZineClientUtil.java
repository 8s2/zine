package com.eightsidedsquare.zine.client.util;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;

public final class ZineClientUtil {
    public static Mesh bake(MutableMesh builder) {
        Mesh mesh = builder.immutableCopy();
        builder.clear();
        return mesh.size() == 0 ? EmptyMesh.INSTANCE : mesh;
    }

    @BakedQuad.MaterialFlags
    public static int getMaterialFlags(Material.Baked baked) {
        int flags = 0;
        flags |= baked.forceTranslucent() || baked.sprite().contents().computeTransparency(0, 0, 1, 1).hasTranslucent() ? 1 : 0;
        return flags | (baked.sprite().contents().isAnimated() ? 2 : 0);
    }

    private ZineClientUtil() {
    }
}
