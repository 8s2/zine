package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.materialmapping.MappableModel;
import com.eightsidedsquare.zine.client.materialmapping.MaterialMapping;
import com.eightsidedsquare.zine.client.util.ZineMappableModelHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin implements ZineMappableModelHolder {

    @Unique
    private Map<Identifier, MappableModel.Unbaked> mappableModels;
    @Unique
    private Map<Identifier, MaterialMapping.UnbakedSet> materialMappings;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zine$init(
            EntityModelSet entityModelSet,
            SpriteGetter sprites,
            PlayerSkinRenderCache playerSkinRenderCache,
            Map<BlockState, BlockStateModel.UnbakedRoot> unbakedBlockStateModels,
            Map<Identifier, ClientItem> clientInfos,
            Map<Identifier, ResolvedModel> resolvedModels,
            ResolvedModel missingModel,
            CallbackInfo ci
    ) {
        ZineMappableModelHolder holder = ZineMappableModelHolder.HOLDER.orElse(new Impl());
        this.mappableModels = holder.zine$getMappableModels();
        this.materialMappings = holder.zine$getMaterialMappings();
    }

    @Override
    public Map<Identifier, MappableModel.Unbaked> zine$getMappableModels() {
        return this.mappableModels;
    }

    @Override
    public Map<Identifier, MaterialMapping.UnbakedSet> zine$getMaterialMappings() {
        return this.materialMappings;
    }

    @Mixin(ModelBakery.ModelBakerImpl.class)
    public static abstract class ModelBakerImplMixin implements ModelBakerMixin {
        @Unique
        private static final Logger LOGGER = LogUtils.getLogger();
        @Shadow(aliases = "this$0")
        @Final
        private ModelBakery bakery;

        @Override
        public MappableModel.Unbaked zine$getMappableModel(Identifier id) {
            MappableModel.Unbaked unbaked = ((ZineMappableModelHolder) this.bakery).zine$getMappableModels().get(id);
            if (unbaked == null) {
                LOGGER.warn("Requested a mappable model that was not discovered previously: {}", id);
                return MappableModel.Unbaked.EMPTY;
            } else {
                return unbaked;
            }
        }
    }
}
