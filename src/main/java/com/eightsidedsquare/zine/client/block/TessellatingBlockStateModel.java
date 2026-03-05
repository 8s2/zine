package com.eightsidedsquare.zine.client.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TessellatingBlockStateModel implements BlockStateModel {

    private final Mesh[] meshes;
    private final TextureAtlasSprite particleSprite;
    private final int size;

    private TessellatingBlockStateModel(Mesh[] meshes, TextureAtlasSprite particleSprite, int size) {
        this.meshes = meshes;
        this.particleSprite = particleSprite;
        this.size = size;
    }

    @Override
    public void emitQuads(QuadEmitter emitter, BlockAndTintGetter blockView, BlockPos pos, BlockState state, RandomSource random, Predicate<@Nullable Direction> cullTest) {
        for(Direction.Axis axis : Direction.Axis.values()) {
            int x = Math.floorMod(axis.choose(-1 - pos.getZ(), pos.getX(), pos.getX()), this.size);
            int y = Math.floorMod(axis.choose(pos.getY(), pos.getZ(), pos.getY()), this.size);
            this.meshes[getIndex(x, y, this.size, axis)].outputTo(emitter);
        }
    }

    @Override
    public void collectParts(RandomSource random, List<BlockModelPart> parts) {

    }

    @Override
    public TextureAtlasSprite particleIcon() {
        return this.particleSprite;
    }

    private static int getIndex(int x, int y, int size, Direction.Axis axis) {
        return (x + y * size) * 3 + axis.ordinal();
    }

    public record Unbaked(Identifier texture, Optional<Identifier> particleTexture, int size) implements CustomMeshUnbakedBlockStateModel {

        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture),
                Identifier.CODEC.optionalFieldOf("particle_texture").forGetter(Unbaked::particleTexture),
                ExtraCodecs.POSITIVE_INT.fieldOf("size").forGetter(Unbaked::size)
        ).apply(instance, Unbaked::new));

        public Unbaked(Identifier texture, Identifier particleTexture, int size) {
            this(texture, Optional.of(particleTexture), size);
        }

        public Unbaked(Identifier texture, int size) {
            this(texture, Optional.empty(), size);
        }

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }

        @Override
        public int getMeshCount() {
            return this.size * this.size * 3;
        }

        @Override
        public BlockStateModel bake(MutableMesh builder, QuadEmitter emitter, Mesh[] meshes, ModelBaker baker) {
            TextureAtlasSprite sprite = baker.zine$getSprite(this.texture);
            float ratio = this.size / (float) (1 << Mth.ceillog2(this.size));
            for (Direction.Axis axis : Direction.Axis.values()) {
                for(int x = 0; x < this.size; x++) {
                    for(int y = 0; y < this.size; y++) {
                        if(axis.isVertical()) {
                            emitFace(emitter, sprite, axis.getPositive(), x, y, this.size, ratio);
                            emitFace(emitter, sprite, axis.getNegative(), x, this.size - y - 1, this.size, ratio);
                        }else {
                            emitFace(emitter, sprite, axis.getPositive(), x, this.size - y - 1, this.size, ratio);
                            emitFace(emitter, sprite, axis.getNegative(), this.size - x - 1, this.size - y - 1, this.size, ratio);
                        }
                        meshes[getIndex(x, y, this.size, axis)] = builder.immutableCopy();
                        builder.clear();
                    }
                }
            }
            TextureAtlasSprite particleSprite = this.particleTexture.map(baker::zine$getSprite).orElse(sprite);
            return new TessellatingBlockStateModel(meshes, particleSprite, this.size);
        }

        private static void emitFace(QuadEmitter emitter, TextureAtlasSprite sprite, Direction direction, int x, int y, int size, float ratio) {
            emitter.square(direction, 0, 0, 1, 1, 0);
            float u1 = (x / (float) size) * ratio;
            float u2 = ((x + 1) / (float) size) * ratio;
            float v1 = (y / (float) size) * ratio;
            float v2 = ((y + 1) / (float) size) * ratio;
            emitter.uv(0, u1, v1);
            emitter.uv(1, u1, v2);
            emitter.uv(2, u2, v2);
            emitter.uv(3, u2, v1);
            emitter.spriteBake(sprite, MutableQuadView.BAKE_NORMALIZED);
            emitter.color(-1, -1, -1, -1);
            emitter.emit();
        }
    }
}
