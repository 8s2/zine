package com.eightsidedsquare.zine.client.atlas;

import com.eightsidedsquare.zine.common.util.codec.CodecUtil;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class RemapAtlasSource implements AtlasSource {

    public static final MapCodec<RemapAtlasSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.nonEmptyListCodec(TextureSet.CODEC).fieldOf("texture_sets").forGetter(RemapAtlasSource::getTextureSets),
            CodecUtil.nonEmptyListCodec(Mapping.CODEC).fieldOf("mappings").forGetter(RemapAtlasSource::getMappings)
    ).apply(instance, RemapAtlasSource::new));

    private List<TextureSet> textureSets;
    private List<Mapping> mappings;

    public RemapAtlasSource(List<TextureSet> textureSets, List<Mapping> mappings) {
        this.textureSets = new ArrayList<>(textureSets);
        this.mappings = new ArrayList<>(mappings);
    }

    @Override
    public MapCodec<? extends AtlasSource> getCodec() {
        return CODEC;
    }

    public List<TextureSet> getTextureSets() {
        return this.textureSets;
    }

    public void setTextureSets(List<TextureSet> textureSets) {
        this.textureSets = textureSets;
    }

    public void addTextureSet(TextureSet textureSet) {
        this.textureSets.add(textureSet);
    }

    public List<Mapping> getMappings() {
        return this.mappings;
    }

    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    public void addMapping(Mapping mapping) {
        this.mappings.add(mapping);
    }

    @Override
    public void load(ResourceManager resourceManager, SpriteRegions regions) {
        List<Sampler> samplers = new ArrayList<>();
        for(TextureSet textureSet : this.textureSets) {
            samplers.add(textureSet.createSampler(resourceManager));
        }
        for(Mapping mapping : this.mappings) {
            TextureData mappingData = AtlasSourceUtil.open(resourceManager, mapping.texture);
            int[] pixelData = mappingData.data();
            int width = mappingData.width();
            int height = mappingData.height();
            for(int i = 0; i < this.textureSets.size(); i++) {
                TextureSet textureSet = this.textureSets.get(i);
                Sampler sampler = samplers.get(i);
                Identifier texture = mapping.getTextureName(textureSet.name);
                regions.add(texture, spriteOpener -> {
                    NativeImage nativeImage = new NativeImage(width, height, false);
                    for(int j = 0; j < pixelData.length; j++) {
                        int x = j % width;
                        int y = j / width;
                        float u = x / (float) width;
                        float v = y / (float) height;
                        nativeImage.setColorArgb(x, y, sampler.sample(pixelData[j], u, v));
                    }
                    return new SpriteContents(texture, mappingData.getDimensions(), nativeImage);
                });
            }
        }
    }

    public static Mapping mapping(Identifier texture, @Nullable String prefix, @Nullable String suffix) {
        return new Mapping(texture, Optional.ofNullable(prefix), Optional.ofNullable(suffix));
    }

    public static Texture texture(Identifier texture) {
        return new Texture(texture, Optional.empty());
    }

    public static Texture texture(Identifier texture, Identifier offsetTexture, int min, int max) {
        return new Texture(texture, Optional.of(new Offset(offsetTexture, min, max)));
    }

    public static TextureSet textureSet(String name, Map<Integer, Texture> textures) {
        return new TextureSet(name, textures);
    }

    public record Mapping(Identifier texture, Optional<String> prefix, Optional<String> suffix) {
        public static final Codec<Mapping> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Mapping::texture),
                Codec.STRING.optionalFieldOf("prefix").forGetter(Mapping::prefix),
                Codec.STRING.optionalFieldOf("suffix").forGetter(Mapping::suffix)
        ).apply(instance, Mapping::new));

        public Identifier getTextureName(String name) {
            if(this.prefix.isPresent()) {
                name = this.prefix.get() + name;
            }
            if(this.suffix.isPresent()) {
                name += this.suffix.get();
            }
            return Identifier.of(this.texture.getNamespace(), name);
        }
    }

    public static class TextureSet {

        public static final Codec<TextureSet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(TextureSet::getName),
                Codec.unboundedMap(CodecUtil.INT_STRING, Texture.VALUE_CODEC).fieldOf("textures").forGetter(TextureSet::getTextures)
        ).apply(instance, TextureSet::new));
        private String name;
        private Int2ObjectMap<Texture> textures;

        public TextureSet(String name, Map<Integer, Texture> textures) {
            this.name = name;
            this.textures = new Int2ObjectArrayMap<>(textures);
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Int2ObjectMap<Texture> getTextures() {
            return this.textures;
        }

        public void setTextures(Int2ObjectMap<Texture> textures) {
            this.textures = textures;
        }

        public void addTexture(Texture texture, int index) {
            this.textures.put(index, texture);
        }

        public Sampler createSampler(ResourceManager resourceManager) {
            Int2ObjectMap<Sampler> samplers = new Int2ObjectArrayMap<>();
            this.textures.forEach((index, texture) -> samplers.put(index, texture.createSampler(resourceManager)));
            return (uvw, u, v) -> {
                Sampler sampler = samplers.get(uvw & 0xFF);
                return sampler == null ? uvw : sampler.sample(uvw, u, v);
            };
        }
    }

    public record Texture(Identifier texture, Optional<Offset> offset) {
        public static final Codec<Texture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Texture::texture),
                Offset.CODEC.optionalFieldOf("offset").forGetter(Texture::offset)
        ).apply(instance, Texture::new));
        public static final Codec<Texture> VALUE_CODEC = Codec.either(Identifier.CODEC, CODEC).xmap(
                either -> either.map(RemapAtlasSource::texture, Function.identity()),
                texture -> texture.offset.isPresent() ? Either.right(texture) : Either.left(texture.texture)
        );

        public Sampler createSampler(ResourceManager resourceManager) {
            TextureData textureData = AtlasSourceUtil.open(resourceManager, this.texture);
            int[] pixelData = textureData.data();
            int width = textureData.width();
            int height = textureData.height();
            Sampler sampler = (uvw, u, v) -> pixelData[((uvw >> 16 & 0xFF) / width) + ((uvw >> 8 & 0xFF) / height) * width];
            return this.offset.map(offset -> offset.offsetSampler(sampler, resourceManager)).orElse(sampler);
        }
    }

    public record Offset(Identifier texture, int min, int max) {
        public static final Codec<Offset> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Offset::texture),
                Codec.INT.fieldOf("min").forGetter(Offset::min),
                Codec.INT.fieldOf("max").forGetter(Offset::max)
        ).apply(instance, Offset::new));

        public Sampler offsetSampler(Sampler sampler, ResourceManager resourceManager) {
            TextureData textureData = AtlasSourceUtil.open(resourceManager, this.texture);
            int[] pixelData = textureData.data();
            int width = textureData.width();
            int height = textureData.height();
            return (uvw, u, v) -> {
                int sU = (uvw >> 16 & 0xFF);
                int sV = (uvw >> 8 & 0xFF);
                int offset = pixelData[(sU / width) + (sV / height) * width];
                int offsetU = MathHelper.lerp((offset >> 16 & 0xFF) / 255f, this.min, this.max);
                int offsetV = MathHelper.lerp((offset >> 8 & 0xFF) / 255f, this.min, this.max);
                sU = MathHelper.clamp(sU + offsetU, 0, 255);
                sV = MathHelper.clamp(sV + offsetV, 0, 255);
                return sampler.sample((uvw & 0xff0000ff) | (sU << 16) | (sV << 8), u, v);
            };
        }
    }

    @FunctionalInterface
    public interface Sampler {
        int sample(int uvw, float u, float v);
    }
}
