package com.eightsidedsquare.zine.client.block;

import com.eightsidedsquare.zine.client.util.ConnectedPattern;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

public class ConnectedBlockStateModel implements BlockStateModel {

    private final Mesh[] meshes;
    private final Material.Baked particleMaterial;
    private final EnumMap<Direction, ConnectedPatternCalculator> calculators = new EnumMap<>(Direction.class);
    private final int materialFlags;

    private ConnectedBlockStateModel(Mesh[] meshes, Material.Baked particleMaterial, boolean fancy, int materialFlags) {
        this.meshes = meshes;
        this.particleMaterial = particleMaterial;
        this.calculators.putAll(fancy ? ConnectedPatternCalculator.FANCY_CUBE : ConnectedPatternCalculator.FAST_CUBE);
        this.materialFlags = materialFlags;
    }

    @Override
    public void emitQuads(QuadEmitter emitter, BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, Predicate<@Nullable Direction> cullTest) {
        for (Direction direction : Direction.values()) {
            if(cullTest.test(direction)) {
                continue;
            }
            ConnectedPattern pattern = this.calculators.get(direction).calculate(level, pos, state);
            this.meshes[getIndex(pattern, direction)].outputTo(emitter);
        }
    }

    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> output) {

    }

    @Override
    public Material.Baked particleMaterial() {
        return this.particleMaterial;
    }

    private static int getIndex(ConnectedPattern pattern, Direction direction) {
        return pattern.ordinal() * 6 + direction.ordinal();
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return this.materialFlags;
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
        public BlockStateModel bake(MutableMesh builder, QuadEmitter emitter, Mesh[] meshes, ModelBaker baker) {
            Material.Baked particleMaterial = null;
            int materialFlags = 0;
            for (ConnectedPattern pattern : ConnectedPattern.values()) {
                Material.Baked material = baker.zine$get(pattern.addSuffix(this.baseTexture));
                materialFlags |= material.zine$getMaterialFlags();
                if(pattern == ConnectedPattern.AAAA) {
                    particleMaterial = material;
                }
                emitMeshes(meshes, builder, emitter, pattern, material);
            }
            if(particleMaterial == null) {
                particleMaterial = baker.zine$getMissing();
            }
            return new ConnectedBlockStateModel(meshes, particleMaterial, this.fancy, materialFlags);
        }

        private static void emitMeshes(Mesh[] meshes, MutableMesh builder, QuadEmitter emitter, ConnectedPattern pattern, Material.Baked material) {
            for(Direction direction : Direction.values()) {
                builder.clear();
                emitter.square(direction, 0, 0, 1, 1, 0);
                emitter.materialBake(material, MutableQuadView.BAKE_LOCK_UV);
                emitter.color(-1, -1, -1, -1);
                emitter.emit();
                meshes[getIndex(pattern, direction)] = builder.immutableCopy();
            }
        }
    }

}
