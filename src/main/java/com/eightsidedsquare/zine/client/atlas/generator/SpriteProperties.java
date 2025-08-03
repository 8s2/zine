package com.eightsidedsquare.zine.client.atlas.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteDimensions;
import net.minecraft.resource.metadata.ResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public record SpriteProperties(int width, int height, int frames, int frameTime, boolean interpolate) {

    public static final SpriteProperties DEFAULT = new SpriteProperties(16, 16);
    public static final Codec<SpriteProperties> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("width").forGetter(SpriteProperties::width),
            Codecs.POSITIVE_INT.fieldOf("height").forGetter(SpriteProperties::height),
            Codecs.NON_NEGATIVE_INT.optionalFieldOf("frames", 0).forGetter(SpriteProperties::frames),
            Codecs.POSITIVE_INT.optionalFieldOf("frame_time", 1).forGetter(SpriteProperties::frameTime),
            Codec.BOOL.optionalFieldOf("interpolate", false).forGetter(SpriteProperties::interpolate)
    ).apply(instance, SpriteProperties::new));

    public SpriteProperties(int width, int height, int frames, int frameTime) {
        this(width, height, frames, frameTime, false);
    }

    public SpriteProperties(int width, int height, int frames) {
        this(width, height, frames, 1);
    }

    public SpriteProperties(int width, int height) {
        this(width, height, 0);
    }

    public SpriteDimensions getDimensions() {
        return new SpriteDimensions(this.width, this.height);
    }

    public AnimationResourceMetadata getAnimationResourceMetadata() {
        return new AnimationResourceMetadata(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                this.frameTime,
                this.interpolate
        );
    }

    public SpriteContents createContents(Identifier id, NativeImage nativeImage) {
        return new SpriteContents(id, this.getDimensions(), nativeImage, Optional.of(this.getAnimationResourceMetadata()), List.of());
    }

    private ResourceMetadata resourceMetadata(AnimationResourceMetadata animationMetadata) {
        return new ResourceMetadata() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> Optional<T> decode(ResourceMetadataSerializer<T> serializer) {
                if(serializer.equals(AnimationResourceMetadata.SERIALIZER)) {
                    return (Optional<T>) Optional.of(animationMetadata);
                }
                return Optional.empty();
            }
        };
    }

}
