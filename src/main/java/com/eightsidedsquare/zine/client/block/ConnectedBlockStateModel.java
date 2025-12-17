package com.eightsidedsquare.zine.client.block;

import com.eightsidedsquare.zine.client.util.ConnectedPattern;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.BlockModelPart;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

public class ConnectedBlockStateModel implements BlockStateModel {

    private final Mesh[] meshes;
    private final Sprite particleSprite;
    private final EnumMap<Direction, ConnectedPatternCalculator> calculators = new EnumMap<>(Direction.class);

    private ConnectedBlockStateModel(Mesh[] meshes, Sprite particleSprite, boolean fancy) {
        this.meshes = meshes;
        this.particleSprite = particleSprite;
        this.calculators.putAll(fancy ? ConnectedPatternCalculator.FANCY_CUBE : ConnectedPatternCalculator.FAST_CUBE);
    }

    @Override
    public void emitQuads(QuadEmitter emitter, BlockRenderView world, BlockPos pos, BlockState state, Random random, Predicate<@Nullable Direction> cullTest) {
        for (Direction direction : Direction.values()) {
            if(cullTest.test(direction)) {
                continue;
            }
            ConnectedPattern pattern = this.calculators.get(direction).calculate(world, pos, state);
            this.meshes[getIndex(pattern, direction)].outputTo(emitter);
        }
    }

    @Override
    public Sprite particleSprite() {
        return this.particleSprite;
    }

    private static int getIndex(ConnectedPattern pattern, Direction direction) {
        return pattern.ordinal() * 6 + direction.ordinal();
    }

    @Override
    public void addParts(Random random, List<BlockModelPart> parts) {

    }

    public record Unbaked(Identifier baseTexture, boolean fancy) implements CustomMeshUnbakedBlockStateModel {

        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf("base_texture").forGetter(Unbaked::baseTexture),
                Codec.BOOL.fieldOf("fancy").forGetter(Unbaked::fancy)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }

        @Override
        public int getMeshCount() {
            return ConnectedPattern.values().length * 6;
        }

        @Override
        public BlockStateModel bake(MutableMesh builder, QuadEmitter emitter, Mesh[] meshes, Baker baker) {
            Sprite particleSprite = null;
            for (ConnectedPattern pattern : ConnectedPattern.values()) {
                Sprite sprite = baker.zine$getSprite(pattern.addSuffix(this.baseTexture));
                if(pattern == ConnectedPattern.AAAA) {
                    particleSprite = sprite;
                }
                emitMeshes(meshes, builder, emitter, pattern, sprite);
            }
            builder.clear();
            return new ConnectedBlockStateModel(meshes, particleSprite, this.fancy);
        }

        private static void emitMeshes(Mesh[] meshes, MutableMesh builder, QuadEmitter emitter, ConnectedPattern pattern, Sprite sprite) {
            for(Direction direction : Direction.values()) {
                builder.clear();
                emitter.square(direction, 0, 0, 1, 1, 0);
                emitter.spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
                emitter.color(-1, -1, -1, -1);
                emitter.emit();
                meshes[getIndex(pattern, direction)] = builder.immutableCopy();
            }
        }
    }

}
