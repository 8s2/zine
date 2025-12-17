package com.eightsidedsquare.zine.client;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.ConnectedTexturesAtlasSource;
import com.eightsidedsquare.zine.client.atlas.GeneratorAtlasSource;
import com.eightsidedsquare.zine.client.atlas.RemapAtlasSource;
import com.eightsidedsquare.zine.client.atlas.generator.SpriteGenerator;
import com.eightsidedsquare.zine.client.atlas.gradient.Gradient;
import com.eightsidedsquare.zine.client.block.ConnectedBlockStateModel;
import com.eightsidedsquare.zine.client.block.TessellatingBlockStateModel;
import com.eightsidedsquare.zine.client.gui.CompositeTooltipComponent;
import com.eightsidedsquare.zine.client.gui.TooltipComponentWrapper;
import com.eightsidedsquare.zine.client.item.ItemModelEvents;
import com.eightsidedsquare.zine.client.model.ModelEvents;
import com.eightsidedsquare.zine.client.registry.ClientRegistryHelper;
import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistryImpl;
import com.eightsidedsquare.zine.common.item.tooltip.CompositeTooltipData;
import com.eightsidedsquare.zine.core.ZineMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.util.Identifier;

public class ZineClient implements ClientModInitializer {

    public static final ClientRegistryHelper REGISTRY = ClientRegistryHelper.create(ZineMod.MOD_ID);

    @Override
    public void onInitializeClient() {
        this.callBootstraps();

        this.registerEvents();

        REGISTRY.atlasSource("generator", GeneratorAtlasSource.CODEC);
        REGISTRY.atlasSource("remap", RemapAtlasSource.CODEC);
        REGISTRY.atlasSource("connected_textures", ConnectedTexturesAtlasSource.CODEC);

        REGISTRY.blockStateModel("connected", ConnectedBlockStateModel.Unbaked.CODEC);
        REGISTRY.blockStateModel("tessellating", TessellatingBlockStateModel.Unbaked.CODEC);
    }

    private void callBootstraps() {
        Gradient.bootstrap();
        SpriteGenerator.bootstrap();
    }

    private void registerEvents() {
        AtlasEvents.modifySourcesEvent(Identifier.ofVanilla("items")).register(ArmorTrimRegistryImpl::modifyItemsAtlas);
        AtlasEvents.modifySourcesEvent(Identifier.ofVanilla("armor_trims")).register(ArmorTrimRegistryImpl::modifyArmorTrimsAtlas);
        ModelEvents.ADD_UNBAKED.register(ArmorTrimRegistryImpl::addUnbakedModels);
        ItemModelEvents.BEFORE_BAKE.register(ArmorTrimRegistryImpl::modifyItemModels);
        TooltipComponentCallback.EVENT.register(tooltipData -> switch (tooltipData) {
            case CompositeTooltipData compositeTooltipData -> new CompositeTooltipComponent(compositeTooltipData);
            case TooltipComponentWrapper(TooltipComponent component) -> component;
            default -> null;
        });
    }
}
