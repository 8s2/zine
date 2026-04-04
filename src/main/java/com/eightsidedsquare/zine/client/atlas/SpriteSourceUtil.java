package com.eightsidedsquare.zine.client.atlas;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.Optional;

public final class SpriteSourceUtil {
    private SpriteSourceUtil() {
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public static TextureData open(ResourceManager resourceManager, Identifier texture) {
        Optional<Resource> optional = resourceManager.getResource(SpriteSource.TEXTURE_ID_CONVERTER.idToFile(texture));
        if(optional.isEmpty()) {
            LOGGER.error("Failed to load texture {}", texture);
            throw new IllegalArgumentException();
        }
        try {
            InputStream inputStream = optional.get().open();

            TextureData textureData;
            try (NativeImage nativeImage = NativeImage.read(inputStream)) {
                textureData = new TextureData(nativeImage.getPixels(), nativeImage.getWidth(), nativeImage.getHeight());
            } catch (Throwable throwable) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable suppressedThrowable) {
                        throwable.addSuppressed(suppressedThrowable);
                    }
                }
                throw throwable;
            }
            inputStream.close();
            return textureData;
        } catch (Exception e) {
            LOGGER.error("Couldn't load texture {}", texture, e);
            throw new IllegalArgumentException();
        }
    }

    public static NativeImage createNativeImage(int width, int height, ImagePos2Color function) {
        NativeImage nativeImage = new NativeImage(width, height, false);
        int size = width * height;
        for(int i = 0; i < size; i++) {
            int x = i % width;
            int y = i / width;
            float u = x / (float) width;
            float v = y / (float) height;
            nativeImage.setPixel(x, y, function.apply(i, x, y, u, v));
        }
        return nativeImage;
    }

    public static NativeImage createAnimatedNativeImage(int frames, int width, int height, AnimatedImagePos2Color function) {
        NativeImage nativeImage = new NativeImage(width, height * frames, false);
        int size = width * height;
        for(int i = 0; i < size; i++) {
            int x = i % width;
            int y = i / width;
            float u = x / (float) width;
            float v = y / (float) height;
            for(int frame = 0; frame < frames; frame++) {
                nativeImage.setPixel(x, y + frame * height, function.apply(frame, i, x, y, u, v));
            }
        }
        return nativeImage;
    }

    @FunctionalInterface
    public interface ImagePos2Color {
        int apply(int index, int x, int y, float u, float v);
    }

    @FunctionalInterface
    public interface AnimatedImagePos2Color {
        int apply(int frame, int index, int x, int y, float u, float v);
    }

}
