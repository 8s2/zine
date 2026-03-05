package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.materialmapping.MappableModel;
import com.eightsidedsquare.zine.client.materialmapping.MaterialMapping;
import com.eightsidedsquare.zine.client.util.ZineMappableModelHolder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.resources.model.ModelDiscovery;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelDiscovery.class)
public abstract class ModelDiscoveryMixin implements ZineMappableModelHolder {

    @Shadow @Final
    private static Logger LOGGER;
    @Shadow @Final @Mutable
    private ResolvableModel.Resolver resolver;
    @Unique
    private final Map<Identifier, MappableModel.Unbaked> mappableModels = new Object2ObjectOpenHashMap<>();
    @Unique
    private Map<Identifier, MaterialMapping.UnbakedSet> materialMappings;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zine$init(Map<Identifier, UnbakedModel> unbakedModels, UnbakedModel missingUnbakedModel, CallbackInfo ci) {
        final ResolvableModel.Resolver originalResolver = this.resolver;
        ZineMappableModelHolder holder = ZineMappableModelHolder.HOLDER.orElse(new Impl());
        Map<Identifier, MappableModel.Unbaked> unbakedMappableModels = holder.zine$getMappableModels();
        this.materialMappings = holder.zine$getMaterialMappings();
        this.resolver = new ResolvableModel.Resolver() {
            @Override
            public void markDependency(Identifier id) {
                originalResolver.markDependency(id);
            }

            @Override
            public void zine$markMappableModelDependency(Identifier id) {
                ModelDiscoveryMixin.this.mappableModels.computeIfAbsent(id, modelId -> {
                    MappableModel.Unbaked unbaked = unbakedMappableModels.get(modelId);
                    if(unbaked == null) {
                        LOGGER.warn("Missing mappable model: {}", id);
                        return MappableModel.Unbaked.EMPTY;
                    }
                    return unbaked;
                });
            }
        };
    }

    @Override
    public Map<Identifier, MappableModel.Unbaked> zine$getMappableModels() {
        return this.mappableModels;
    }

    @Override
    public Map<Identifier, MaterialMapping.UnbakedSet> zine$getMaterialMappings() {
        return this.materialMappings;
    }
}
