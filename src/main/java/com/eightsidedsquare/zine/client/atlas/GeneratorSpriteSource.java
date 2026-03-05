package com.eightsidedsquare.zine.client.atlas;

import com.eightsidedsquare.zine.client.atlas.generator.SpriteGenerator;
import com.eightsidedsquare.zine.client.atlas.generator.SpriteProperties;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

public class GeneratorSpriteSource implements SpriteSource {

    public static final MapCodec<GeneratorSpriteSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            SpriteGenerator.CODEC.fieldOf("sprite_generator").forGetter(GeneratorSpriteSource::getSpriteGenerator),
            Identifier.CODEC.fieldOf("output_id").forGetter(GeneratorSpriteSource::getOutputId),
            SpriteProperties.CODEC.optionalFieldOf("properties", SpriteProperties.DEFAULT).forGetter(GeneratorSpriteSource::getProperties)
    ).apply(instance, GeneratorSpriteSource::new));

    private SpriteGenerator spriteGenerator;
    private Identifier outputId;
    private SpriteProperties properties;

    public GeneratorSpriteSource(SpriteGenerator spriteGenerator, Identifier outputId, SpriteProperties properties) {
        this.spriteGenerator = spriteGenerator;
        this.outputId = outputId;
        this.properties = properties;
    }

    public GeneratorSpriteSource(SpriteGenerator spriteGenerator, Identifier outputId, int width, int height) {
        this(spriteGenerator, outputId, new SpriteProperties(width, height));
    }

    public GeneratorSpriteSource(SpriteGenerator spriteGenerator, Identifier outputId) {
        this(spriteGenerator, outputId, SpriteProperties.DEFAULT);
    }

    @Override
    public MapCodec<? extends SpriteSource> codec() {
        return CODEC;
    }

    public SpriteGenerator getSpriteGenerator() {
        return this.spriteGenerator;
    }

    public void setSpriteGenerator(SpriteGenerator spriteGenerator) {
        this.spriteGenerator = spriteGenerator;
    }

    public Identifier getOutputId() {
        return this.outputId;
    }

    public void setOutputId(Identifier outputId) {
        this.outputId = outputId;
    }

    public SpriteProperties getProperties() {
        return this.properties;
    }

    public void setProperties(SpriteProperties dimensions) {
        this.properties = dimensions;
    }

    @Override
    public void run(ResourceManager resourceManager, net.minecraft.client.renderer.texture.atlas.SpriteSource.Output regions) {
        Output output = this.spriteGenerator.generate(this.outputId, this.properties);
        regions.add(this.outputId, spriteOpener -> {
            int width = this.properties.width();
            int height = this.properties.height();
            int frames = this.properties.frames();
            NativeImage nativeImage = new NativeImage(width, height * frames, false);
            for(int frame = 0; frame < frames; frame++) {
                float w = frame / (float) frames;
                for(int y = 0; y < height; y++) {
                    float v = y / (float) height;
                    for(int x = 0; x < width; x++) {
                        float u = x / (float) width;
                        nativeImage.setPixel(x, y + frame * height, output.apply(u, v, w));
                    }
                }
            }
            return this.properties.createContents(this.outputId, nativeImage);
        });
    }

    @FunctionalInterface
    public interface Output {
        int apply(float u, float v, float w);
    }
}
