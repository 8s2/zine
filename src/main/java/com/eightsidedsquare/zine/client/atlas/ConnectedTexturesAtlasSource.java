package com.eightsidedsquare.zine.client.atlas;

import com.eightsidedsquare.zine.client.util.ConnectedPattern;
import com.eightsidedsquare.zine.client.util.ConnectedShape;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteDimensions;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.EnumMap;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ConnectedTexturesAtlasSource implements AtlasSource {

    public static final MapCodec<ConnectedTexturesAtlasSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("base_name").forGetter(ConnectedTexturesAtlasSource::getBaseName),
            Identifier.CODEC.optionalFieldOf("all").forGetter(source -> Optional.of(source.allTexture)),
            Identifier.CODEC.optionalFieldOf("corners").forGetter(source -> Optional.of(source.cornersTexture)),
            Identifier.CODEC.optionalFieldOf("horizontal").forGetter(source -> Optional.of(source.horizontalTexture)),
            Identifier.CODEC.optionalFieldOf("none").forGetter(source -> Optional.of(source.noneTexture)),
            Identifier.CODEC.optionalFieldOf("vertical").forGetter(source -> Optional.of(source.verticalTexture))
    ).apply(instance, ConnectedTexturesAtlasSource::new));

    private static final Logger LOGGER = LogUtils.getLogger();

    private Identifier baseName;
    private Identifier allTexture;
    private Identifier cornersTexture;
    private Identifier horizontalTexture;
    private Identifier noneTexture;
    private Identifier verticalTexture;

    public ConnectedTexturesAtlasSource(Identifier baseName,
                                        Identifier allTexture,
                                        Identifier cornersTexture,
                                        Identifier horizontalTexture,
                                        Identifier noneTexture,
                                        Identifier verticalTexture) {
        this.baseName = baseName;
        this.allTexture = allTexture;
        this.cornersTexture = cornersTexture;
        this.horizontalTexture = horizontalTexture;
        this.noneTexture = noneTexture;
        this.verticalTexture = verticalTexture;
    }

    public ConnectedTexturesAtlasSource(Identifier baseName,
                                        Optional<Identifier> allTexture,
                                        Optional<Identifier> cornersTexture,
                                        Optional<Identifier> horizontalTexture,
                                        Optional<Identifier> noneTexture,
                                        Optional<Identifier> verticalTexture) {
        this(
                baseName,
                allTexture.orElseGet(() -> baseName.withSuffixedPath("_all")),
                cornersTexture.orElseGet(() -> baseName.withSuffixedPath("_corners")),
                horizontalTexture.orElseGet(() -> baseName.withSuffixedPath("_horizontal")),
                noneTexture.orElseGet(() -> baseName.withSuffixedPath("_none")),
                verticalTexture.orElseGet(() -> baseName.withSuffixedPath("_vertical"))
        );
    }

    public ConnectedTexturesAtlasSource(Identifier baseName) {
        this(
                baseName,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
    }

    @Override
    public MapCodec<? extends AtlasSource> getCodec() {
        return CODEC;
    }

    public Identifier getBaseName() {
        return this.baseName;
    }

    public void setBaseName(Identifier baseName) {
        this.baseName = baseName;
    }

    public Identifier getAllTexture() {
        return this.allTexture;
    }

    public void setAllTexture(Identifier allTexture) {
        this.allTexture = allTexture;
    }

    public Identifier getCornersTexture() {
        return this.cornersTexture;
    }

    public void setCornersTexture(Identifier cornersTexture) {
        this.cornersTexture = cornersTexture;
    }

    public Identifier getHorizontalTexture() {
        return this.horizontalTexture;
    }

    public void setHorizontalTexture(Identifier horizontalTexture) {
        this.horizontalTexture = horizontalTexture;
    }

    public Identifier getNoneTexture() {
        return this.noneTexture;
    }

    public void setNoneTexture(Identifier noneTexture) {
        this.noneTexture = noneTexture;
    }

    public Identifier getVerticalTexture() {
        return this.verticalTexture;
    }

    public void setVerticalTexture(Identifier verticalTexture) {
        this.verticalTexture = verticalTexture;
    }

    public Identifier getTexture(ConnectedShape shape) {
        return switch (shape) {
            case ALL -> this.getAllTexture();
            case CORNER -> this.getCornersTexture();
            case HORIZONTAL -> this.getHorizontalTexture();
            case NONE -> this.getNoneTexture();
            case VERTICAL -> this.getVerticalTexture();
        };
    }

    @Override
    public void load(ResourceManager resourceManager, SpriteRegions regions) {
        EnumMap<ConnectedShape, TextureData> data = new EnumMap<>(ConnectedShape.class);
        int dataSize = -1;
        for(ConnectedShape shape : ConnectedShape.values()) {
            TextureData textureData = AtlasSourceUtil.open(resourceManager, this.getTexture(shape));
            int size = textureData.data().length;
            if(dataSize == -1) {
                dataSize = size;
            }else if(size != dataSize) {
                LOGGER.warn("Textures have different sizes: {} and {}", dataSize, size);
                throw new IllegalArgumentException();
            }
            data.put(shape, textureData);
        }
        SpriteDimensions dimensions = data.get(ConnectedShape.ALL).getDimensions();
        int width = dimensions.width();
        int height = dimensions.height();
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        for(ConnectedPattern pattern : ConnectedPattern.values()) {
            if(pattern.allMatch()) {
                continue;
            }
            TextureData nw = data.get(pattern.getNW());
            TextureData ne = data.get(pattern.getNE());
            TextureData se = data.get(pattern.getSE());
            TextureData sw = data.get(pattern.getSW());
            Identifier texture = pattern.addSuffix(this.getBaseName());
            regions.add(texture, spriteOpener -> {
                NativeImage nativeImage = AtlasSourceUtil.createNativeImage(width, height, (index, x, y, u, v) ->
                        (x >= halfWidth ? (y >= halfHeight ? se : ne) : (y >= halfHeight ? sw : nw)).data()[index]
                );
                return new SpriteContents(texture, dimensions, nativeImage);
            });
        }
    }
}
