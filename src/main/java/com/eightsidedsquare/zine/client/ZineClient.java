package com.eightsidedsquare.zine.client;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.ConnectedTexturesSpriteSource;
import com.eightsidedsquare.zine.client.atlas.GeneratorSpriteSource;
import com.eightsidedsquare.zine.client.atlas.RemapSpriteSource;
import com.eightsidedsquare.zine.client.atlas.generator.SpriteGenerator;
import com.eightsidedsquare.zine.client.atlas.gradient.Gradient;
import com.eightsidedsquare.zine.client.block.ConnectedBlockStateModel;
import com.eightsidedsquare.zine.client.block.TessellatingBlockStateModel;
import com.eightsidedsquare.zine.client.gui.CompositeTooltipComponent;
import com.eightsidedsquare.zine.client.gui.TooltipComponentWrapper;
import com.eightsidedsquare.zine.client.item.ItemModelEvents;
import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingLoader;
import com.eightsidedsquare.zine.client.model.ModelEvents;
import com.eightsidedsquare.zine.client.registry.ClientRegistryHelper;
import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistryImpl;
import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltipData;
import com.eightsidedsquare.zine.core.ZineMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public class ZineClient implements ClientModInitializer {

    public static final ClientRegistryHelper REGISTRY = ClientRegistryHelper.create(ZineMod.MOD_ID);

    @Override
    public void onInitializeClient() {
        this.callBootstraps();

        this.registerEvents();

//        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(MaterialMappingLoader.ID, MaterialMappingLoader.INSTANCE);

        REGISTRY.spriteSource("generator", GeneratorSpriteSource.CODEC);
        REGISTRY.spriteSource("remap", RemapSpriteSource.CODEC);
        REGISTRY.spriteSource("connected_textures", ConnectedTexturesSpriteSource.CODEC);

        REGISTRY.blockStateModel("connected", ConnectedBlockStateModel.Unbaked.CODEC);
        REGISTRY.blockStateModel("tessellating", TessellatingBlockStateModel.Unbaked.CODEC);
    }

    private void callBootstraps() {
        Gradient.bootstrap();
        SpriteGenerator.bootstrap();
    }

    private void registerEvents() {
        AtlasEvents.modifySourcesEvent(Identifier.withDefaultNamespace("items")).register(ArmorTrimRegistryImpl::modifyItemsAtlas);
        AtlasEvents.modifySourcesEvent(Identifier.withDefaultNamespace("armor_trims")).register(ArmorTrimRegistryImpl::modifyArmorTrimsAtlas);
        ModelEvents.ADD_UNBAKED.register(ArmorTrimRegistryImpl::addUnbakedModels);
        ItemModelEvents.BEFORE_BAKE.register(ArmorTrimRegistryImpl::modifyItemModels);
        ClientTooltipComponentCallback.EVENT.register(tooltipData -> switch (tooltipData) {
            case CompositeTooltipData compositeTooltipData -> new CompositeTooltipComponent(compositeTooltipData);
            case TooltipComponentWrapper(ClientTooltipComponent component) -> component;
            default -> null;
        });
    }
}
