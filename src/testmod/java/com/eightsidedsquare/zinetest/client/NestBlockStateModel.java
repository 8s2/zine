package com.eightsidedsquare.zinetest.client;

import com.eightsidedsquare.zine.client.materialmapping.MappableModel;
import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingLoader;
import com.eightsidedsquare.zine.client.materialmapping.MeshCache;
import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.Material;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class NestBlockStateModel implements BlockStateModel {

    private final MappableModel mappableModel;
    private final Material.Baked particleMaterial;
    private final MeshCache<Holder<ChickenVariant>> meshes = new MeshCache<>(this::createMesh);

    public NestBlockStateModel(MappableModel mappableModel, Material.Baked particleMaterial) {
        this.mappableModel = mappableModel;
        this.particleMaterial = particleMaterial;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void emitQuads(QuadEmitter emitter, BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, Predicate<@Nullable Direction> cullTest) {
        if(level.getBlockEntityRenderData(pos) instanceof Holder<?> holder && holder.value() instanceof ChickenVariant) {
            this.meshes.get((Holder<ChickenVariant>) holder).outputTo(emitter);
        }
    }

    private void createMesh(Holder<ChickenVariant> variant, MeshCache.Callback callback) {
        callback.accept(this.mappableModel, MaterialMappingLoader.INSTANCE.getMapping(variant).get(TestmodClient.NEST_MODEL));
    }

    @Override
    public void collectParts(RandomSource random, List<BlockModelPart> output) {

    }

    @Override
    public Material.Baked particleMaterial() {
        return this.particleMaterial;
    }

    public record Unbaked(Identifier model) implements CustomUnbakedBlockStateModel {

        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model)
        ).apply(i, Unbaked::new));

        @Override
        public BlockStateModel bake(ModelBaker baker) {
            
            return new NestBlockStateModel(
                    baker.zine$getMappableModel(this.model).bake(baker, BlockModelRotation.get(OctahedralGroup.IDENTITY)),
                    baker.zine$getMissing()
            );
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.zine$markMappableModelDependency(this.model);
        }

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }
    }
}
