package com.eightsidedsquare.zine.client.atlas.generator;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public record SpriteProperties(int width, int height, int frames, int frameTime, boolean interpolate) {

    public static final SpriteProperties DEFAULT = new SpriteProperties(16, 16);
    public static final Codec<SpriteProperties> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(SpriteProperties::width),
            ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(SpriteProperties::height),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("frames", 0).forGetter(SpriteProperties::frames),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("frame_time", 1).forGetter(SpriteProperties::frameTime),
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

    public FrameSize getDimensions() {
        return new FrameSize(this.width, this.height);
    }

    public AnimationMetadataSection getAnimationResourceMetadata() {
        return new AnimationMetadataSection(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                this.frameTime,
                this.interpolate
        );
    }

    public SpriteContents createContents(Identifier id, NativeImage nativeImage) {
        return new SpriteContents(id, this.getDimensions(), nativeImage);
    }

    private ResourceMetadata resourceMetadata(AnimationMetadataSection animationMetadata) {
        return new ResourceMetadata() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> Optional<T> getSection(MetadataSectionType<T> serializer) {
                if(serializer.equals(AnimationMetadataSection.TYPE)) {
                    return (Optional<T>) Optional.of(animationMetadata);
                }
                return Optional.empty();
            }
        };
    }

}
