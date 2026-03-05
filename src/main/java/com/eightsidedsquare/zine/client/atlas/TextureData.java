package com.eightsidedsquare.zine.client.atlas;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.metadata.animation.FrameSize;

@Environment(EnvType.CLIENT)
public record TextureData(int[] data, int width, int height) {

    public FrameSize getDimensions() {
        return new FrameSize(this.width, this.height);
    }

}
