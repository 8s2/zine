package com.eightsidedsquare.zine.client.item;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.joml.Vector3f;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ZineItemStackRenderState {

    Supplier<Vector3f[]> CUBE_VERTICES = Suppliers.memoize(() -> {
        Vector3f[] vertices = new Vector3f[8];
        int i = 0;
        for(int x = 0; x <= 1; x++) {
            for(int y = 0; y <= 1; y++) {
                for(int z = 0; z <= 1; z++) {
                    vertices[i++] = new Vector3f(x, y, z);
                }
            }
        }
        return vertices;
    });

    default ItemStackRenderState.LayerRenderState[] zine$getLayers() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default ItemStackRenderState.LayerRenderState zine$getLastLayer() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    interface ZineLayerRenderState {

        default void zine$setMatrixTransformation(boolean beforeDisplayTransforms, Consumer<PoseStack> matrixTransformation) {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }

        default void zine$setMatrixTransformation(Consumer<PoseStack> matrixTransformation) {
            this.zine$setMatrixTransformation(false, matrixTransformation);
        }

    }

}
