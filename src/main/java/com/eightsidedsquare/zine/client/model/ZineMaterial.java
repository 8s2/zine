package com.eightsidedsquare.zine.client.model;

import com.mojang.blaze3d.platform.Transparency;
import net.minecraft.client.resources.model.geometry.BakedQuad;

public interface ZineMaterial {
    interface Baked {
        default @BakedQuad.MaterialFlags int zine$getMaterialFlags() {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }

        default Transparency zine$getTransparency() {
            throw new UnsupportedOperationException("Implemented via mixin.");
        }
    }
}
