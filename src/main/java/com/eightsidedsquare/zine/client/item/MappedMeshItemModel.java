package com.eightsidedsquare.zine.client.item;

import com.eightsidedsquare.zine.client.materialmapping.MappedMesh;
import com.eightsidedsquare.zine.client.materialmapping.MeshCache;
import com.mojang.datafixers.Products;
import com.mojang.math.Transformation;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.cuboid.ItemTransforms;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MappedMeshItemModel<K> implements ItemModel {
    private final MeshCache<K> meshes;
    private final KeyGetter<K> keyGetter;
    private final FoilGetter foilGetter;
    private final List<ItemTintSource> tints;
    private final ItemTransforms itemTransforms;
    private final Matrix4fc transformation;

    public MappedMeshItemModel(
            MeshCache<K> meshes,
            KeyGetter<K> keyGetter,
            FoilGetter foilGetter,
            List<ItemTintSource> tints,
            ItemTransforms itemTransforms,
            Matrix4fc transformation
    ) {
        this.meshes = meshes;
        this.keyGetter = keyGetter;
        this.foilGetter = foilGetter;
        this.tints = tints;
        this.itemTransforms = itemTransforms;
        this.transformation = transformation;
    }

    @Override
    public void update(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        output.appendModelIdentityElement(this);
        K key = this.keyGetter.getKey(item, displayContext, level, owner, seed);
        if(key == null) {
            return;
        }
        MappedMesh mesh = this.meshes.get(key);
        if(mesh == null) {
            return;
        }
        output.appendModelIdentityElement(key);
        ItemStackRenderState.LayerRenderState layer = output.newLayer();
        if(item.hasFoil()) {
            ItemStackRenderState.FoilType foilType = this.foilGetter.getFoilType(item);
            layer.setFoilType(foilType);
            output.setAnimated();
            output.appendModelIdentityElement(foilType);
        }
        if(!this.tints.isEmpty()) {
            IntList tintLayers = layer.tintLayers();
            for (ItemTintSource tintSource : this.tints) {
                int tint = tintSource.calculate(item, level, owner == null ? null : owner.asLivingEntity());
                tintLayers.add(tint);
                output.appendModelIdentityElement(tint);
            }
        }
        layer.setLocalTransform(this.transformation);
        layer.setItemTransform(this.itemTransforms.getTransform(displayContext));
        mesh.outputTo(layer);
        if(mesh.hasMaterialFlag(2)) {
            output.setAnimated();
        }
    }

    @FunctionalInterface
    public interface KeyGetter<K> {
        @Nullable K getKey(ItemStack item, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed);
    }

    @FunctionalInterface
    public interface FoilGetter {
        ItemStackRenderState.FoilType getFoilType(ItemStack item);
    }

    public interface Unbaked<K> extends ItemModel.Unbaked {
        Identifier base();

        Optional<Transformation> transformation();

        List<ItemTintSource> tints();

        KeyGetter<K> keyGetter();

        MeshCache.Callback<K> callback(ModelBaker baker);

        default FoilGetter foilGetter() {
            return _ -> ItemStackRenderState.FoilType.STANDARD;
        }

        @Override
        default ItemModel bake(BakingContext context, Matrix4fc transformation) {
            ModelBaker baker = context.blockModelBaker();
            Matrix4fc modelTransform = Transformation.compose(transformation, this.transformation());
            return new MappedMeshItemModel<>(
                    new MeshCache<>(baker.zine$getMappings(), baker.zine$getMissing(), this.callback(baker)),
                    this.keyGetter(),
                    this.foilGetter(),
                    this.tints(),
                    baker.getModel(this.base()).getTopTransforms(),
                    modelTransform
            );
        }

        @Override
        default void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.base());
        }

        static <T extends Unbaked<?>> Products.P3<RecordCodecBuilder.Mu<T>, Identifier, Optional<Transformation>, List<ItemTintSource>> group(RecordCodecBuilder.Instance<T> instance) {
            return instance.group(
                    Identifier.CODEC.fieldOf("base").forGetter(Unbaked::base),
                    Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").forGetter(Unbaked::transformation),
                    ItemTintSources.CODEC.listOf().optionalFieldOf("tints", List.of()).forGetter(Unbaked::tints)
            );
        }
    }
}
