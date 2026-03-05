package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.materialmapping.*;
import com.eightsidedsquare.zine.client.model.ModelEvents;
import com.eightsidedsquare.zine.client.util.ZineMappableModelHolder;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.model.cuboid.CuboidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;

@Mixin(ModelManager.class)
public abstract class ModelManagerMixin {

    @Unique
    private static final ScopedValue<CompletableFuture<ZineMappableModelHolder>> HOLDER_FUTURE = ScopedValue.newInstance();

    @Inject(method = "lambda$loadBlockModels$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;sequence(Ljava/util/List;)Ljava/util/concurrent/CompletableFuture;"))
    private static void zine$addUnbakedModels(
            Executor executor,
            Map<Identifier, Resource> models,
            CallbackInfoReturnable<CompletionStage<?>> cir,
            @Local(name = "result") List<CompletableFuture<Pair<Identifier, CuboidModel>>> result
    ) {
        ModelEvents.ADD_UNBAKED.invoker().addUnbakedModels((id, modelSupplier) ->
            result.add(CompletableFuture.supplyAsync(() -> {
                try {
                    CuboidModel model = CuboidModel.GSON.fromJson(modelSupplier.get(), CuboidModel.class);
                    return Pair.of(id, model);
                }catch (Exception ignored) {
                }
                return null;
            }, executor))
        );
    }

    @Inject(method = "reload", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ClientItemInfoLoader;scheduleLoad(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", shift = At.Shift.AFTER))
    private void zine$storeMeshesFuture(
            PreparableReloadListener.SharedState currentReload,
            Executor taskExecutor,
            PreparableReloadListener.PreparationBarrier preparationBarrier,
            Executor reloadExecutor,
            CallbackInfoReturnable<CompletableFuture<Void>> cir,
            @Share("holder") LocalRef<CompletableFuture<ZineMappableModelHolder>> holder
    ) {
        CompletableFuture<Map<Identifier, MappableModel.Unbaked>> mappableModels = MappableModelLoader.loadModels(currentReload.resourceManager(), taskExecutor);
        CompletableFuture<Map<Identifier, MaterialMapping.UnbakedSet>> materialMappings = MaterialMappingLoader.loadMappings(currentReload.resourceManager(), taskExecutor);
        holder.set(CompletableFuture.allOf(mappableModels, materialMappings).thenApply(_ ->
                new ZineMappableModelHolder.Impl(mappableModels.join(), materialMappings.join())
        ));
    }

    @WrapOperation(method = "reload", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;allOf([Ljava/util/concurrent/CompletableFuture;)Ljava/util/concurrent/CompletableFuture;"))
    private CompletableFuture<Void> zine$includeMeshesFuture(
            CompletableFuture<?>[] futures,
            Operation<CompletableFuture<Void>> original,
            @Share("holder") LocalRef<CompletableFuture<ZineMappableModelHolder>> holder
    ) {
        CompletableFuture<?>[] newFutures = new CompletableFuture<?>[futures.length + 1];
        System.arraycopy(futures, 0, newFutures, 0, futures.length);
        newFutures[newFutures.length - 1] = holder.get();
        return original.call((Object) newFutures);
    }

    @WrapOperation(method = "reload", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenApplyAsync(Ljava/util/function/Function;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", ordinal = 1))
    private <T, U> CompletableFuture<U> zine$wrapDiscoverModelDependenciesFuture(
            CompletableFuture<T> future,
            Function<? super T, ? extends U> fn,
            Executor executor,
            Operation<CompletableFuture<U>> original,
            @Share("holder") LocalRef<CompletableFuture<ZineMappableModelHolder>> holder
    ) {
        Function<? super T, ? extends U> func = t -> ScopedValue.where(HOLDER_FUTURE, holder.get()).call(() -> fn.apply(t));
        return original.call(future, func, executor);
    }

    @WrapOperation(method = "lambda$reload$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelManager;discoverModelDependencies(Ljava/util/Map;Lnet/minecraft/client/resources/model/BlockStateModelLoader$LoadedModels;Lnet/minecraft/client/resources/model/ClientItemInfoLoader$LoadedClientInfos;)Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;"))
    private static ModelManager.ResolvedModels zine$wrapDiscoverModelDependencies(
            Map<Identifier, UnbakedModel> allModels,
            BlockStateModelLoader.LoadedModels blockStateModels,
            ClientItemInfoLoader.LoadedClientInfos itemInfos,
            Operation<ModelManager.ResolvedModels> original
    ) {
        return HOLDER_FUTURE.isBound() ?
                ScopedValue.where(ZineMappableModelHolder.HOLDER, HOLDER_FUTURE.get().join())
                        .call(() -> original.call(allModels, blockStateModels, itemInfos)) :
                original.call(allModels, blockStateModels, itemInfos);
    }

    @WrapOperation(method = "discoverModelDependencies", at = @At(value = "NEW", target = "(Ljava/util/Map;Lnet/minecraft/client/resources/model/UnbakedModel;)Lnet/minecraft/client/resources/model/ModelDiscovery;"))
    private static ModelDiscovery zine$wrapModelDiscovery(
            Map<Identifier, UnbakedModel> unbakedModels,
            UnbakedModel missingUnbakedModel,
            Operation<ModelDiscovery> original
    ) {
        return ScopedValue.where(ZineMappableModelHolder.HOLDER, ZineMappableModelHolder.HOLDER.orElse(new ZineMappableModelHolder.Impl()))
                .call(() -> original.call(unbakedModels, missingUnbakedModel));
    }

    @ModifyExpressionValue(method = "discoverModelDependencies", at = @At(value = "NEW", target = "(Lnet/minecraft/client/resources/model/ResolvedModel;Ljava/util/Map;)Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;"))
    private static ModelManager.ResolvedModels zine$appendMappableModelsToResult(
            ModelManager.ResolvedModels resolvedModels,
            @Local(name = "result") ModelDiscovery result
    ) {
        ((ZineMappableModelHolder) (Object) resolvedModels).zine$copyFrom(((ZineMappableModelHolder) result));
        return resolvedModels;
    }

    @WrapOperation(method = "lambda$reload$4", at = @At(value = "NEW", target = "(Lnet/minecraft/client/model/geom/EntityModelSet;Lnet/minecraft/client/resources/model/sprite/SpriteGetter;Lnet/minecraft/client/renderer/PlayerSkinRenderCache;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Lnet/minecraft/client/resources/model/ResolvedModel;)Lnet/minecraft/client/resources/model/ModelBakery;"))
    private ModelBakery zine$wrapNewModelBakery(
            EntityModelSet entityModelSet,
            SpriteGetter sprites,
            PlayerSkinRenderCache playerSkinRenderCache,
            Map<BlockState, BlockStateModel.UnbakedRoot> unbakedBlockStateModels,
            Map<Identifier, ClientItem> clientInfos,
            Map<Identifier, ResolvedModel> resolvedModels,
            ResolvedModel missingModel,
            Operation<ModelBakery> original,
            @Local(name = "resolvedModels") ModelManager.ResolvedModels managerResolvedModels
    ) {
        return ScopedValue.where(
                ZineMappableModelHolder.HOLDER,
                ((ZineMappableModelHolder) (Object) managerResolvedModels)
        ).call(() -> original.call(entityModelSet, sprites, playerSkinRenderCache, unbakedBlockStateModels, clientInfos, resolvedModels, missingModel));
    }

    @WrapOperation(method = "loadModels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelBakery;bakeModels(Lnet/minecraft/client/resources/model/sprite/MaterialBaker;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
    private static CompletableFuture<ModelBakery.BakingResult> zine$wrapBakeModels(
            ModelBakery bakery,
            MaterialBaker materials,
            Executor taskExecutor,
            Operation<CompletableFuture<ModelBakery.BakingResult>> original
    ) {
        materials.zine$setMappings(MaterialMappingStorage.bake(((ZineMappableModelHolder) bakery).zine$getMaterialMappings(), materials));
        return original.call(bakery, materials, taskExecutor);
    }

    @Mixin(ModelManager.ResolvedModels.class)
    public static abstract class ResolvedModelsMixin implements ZineMappableModelHolder {
        @Unique
        private Map<Identifier, MappableModel.Unbaked> mappableModels = Map.of();
        @Unique
        private Map<Identifier, MaterialMapping.UnbakedSet> materialMappings = Map.of();

        @Override
        public void zine$setMappableModels(Map<Identifier, MappableModel.Unbaked> mappableModels) {
            this.mappableModels = mappableModels;
        }

        @Override
        public Map<Identifier, MappableModel.Unbaked> zine$getMappableModels() {
            return this.mappableModels;
        }

        @Override
        public void zine$setMaterialMappings(Map<Identifier, MaterialMapping.UnbakedSet> materialMappings) {
            this.materialMappings = materialMappings;
        }

        @Override
        public Map<Identifier, MaterialMapping.UnbakedSet> zine$getMaterialMappings() {
            return this.materialMappings;
        }
    }

    @Mixin(targets = "net.minecraft.client.resources.model.ModelManager$1")
    public static abstract class MaterialBakerImplMixin implements MaterialBakerMixin {
        @Shadow @Final
        private Map<Material, Material.Baked> bakedMaterials;
        @Shadow @Final
        private Function<Material, Material.Baked> bakerFunction;
        @Shadow @Final
        private Material.Baked blockMissing;
        @Unique
        private MaterialMappingStorage mappings = MaterialMappingStorage.EMPTY;

        @Override
        public Material.Baked zine$get(Material material) {
            Material.Baked baked = this.bakedMaterials.computeIfAbsent(material, this.bakerFunction);
            return baked == null ? this.blockMissing : baked;
        }

        @Override
        public Material.Baked zine$getMissing() {
            return this.blockMissing;
        }

        @Override
        public MaterialMappingStorage zine$getMappings() {
            return this.mappings;
        }

        @Override
        public void zine$setMappings(MaterialMappingStorage mappings) {
            this.mappings = mappings;
        }
    }
}
