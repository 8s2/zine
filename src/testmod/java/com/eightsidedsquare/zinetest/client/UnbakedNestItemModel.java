package com.eightsidedsquare.zinetest.client;

import com.eightsidedsquare.zine.client.item.MappedMeshItemModel;
import com.eightsidedsquare.zine.client.materialmapping.MappableModel;
import com.eightsidedsquare.zine.client.materialmapping.MeshCache;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;

import java.util.List;
import java.util.Optional;

public record UnbakedNestItemModel(Identifier template, Identifier base, Optional<Transformation> transformation, List<ItemTintSource> tints) implements MappedMeshItemModel.Unbaked<Holder<ChickenVariant>> {
    public static final MapCodec<UnbakedNestItemModel> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Identifier.CODEC.fieldOf("template").forGetter(UnbakedNestItemModel::template)
    ).and(MappedMeshItemModel.Unbaked.group(i)).apply(i, UnbakedNestItemModel::new));

    @Override
    public MappedMeshItemModel.KeyGetter<Holder<ChickenVariant>> keyGetter() {
        return (item, _, _, _, _) -> item.get(DataComponents.CHICKEN_VARIANT);
    }

    @Override
    public MeshCache.Callback<Holder<ChickenVariant>> callback(ModelBaker baker) {
        MappableModel mappableModel = baker.zine$getMappableModel(this.template).bake(baker, BlockModelRotation.IDENTITY);
        return (key, storage, output) ->
            output.accept(mappableModel, storage.get(key).get(TestmodClient.NEST_MODEL));
    }

    @Override
    public MapCodec<? extends ItemModel.Unbaked> type() {
        return CODEC;
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        MappedMeshItemModel.Unbaked.super.resolveDependencies(resolver);
        resolver.zine$markMappableModelDependency(this.template);
    }
}
