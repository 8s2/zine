package com.eightsidedsquare.zinetest.client;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.ConnectedTexturesSpriteSource;
import com.eightsidedsquare.zine.client.atlas.RemapSpriteSource;
import com.eightsidedsquare.zine.client.item.ItemModelEvents;
import com.eightsidedsquare.zine.client.language.LanguageEvents;
import com.eightsidedsquare.zine.client.model.ModelEvents;
import com.eightsidedsquare.zine.client.registry.ClientRegistryHelper;
import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistry;
import com.eightsidedsquare.zinetest.core.Testmod;
import com.eightsidedsquare.zinetest.core.TestmodBlockItems;
import com.eightsidedsquare.zinetest.core.TestmodItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class TestmodClient implements ClientModInitializer {
    public static final ClientRegistryHelper REGISTRY = ClientRegistryHelper.create(Testmod.MOD_ID);
    public static final Identifier NEST_MODEL = Testmod.id("nest");
    private static final Identifier TEST_MODEL = Testmod.id("item/test");
    private static final boolean SHOW_TEST_HUD = false;

    @Override
    public void onInitializeClient() {
        AtlasEvents.modifySourcesEvent(Identifier.withDefaultNamespace("blocks")).register(sources -> {
            sources.add(new RemapSpriteSource(
                    List.of(
                            RemapSpriteSource.textureSet(
                                    "polished_granite",
                                    Map.of(
                                            0, RemapSpriteSource.texture(
                                                    Identifier.parse("block/debug"),
                                                    Testmod.id("block/offset"),
                                                    -16,
                                                    16
                                            ),
                                            255, RemapSpriteSource.texture(Identifier.parse("block/tuff"))
                                    )
                            )

                    ),
                    List.of(
                            RemapSpriteSource.mapping(Testmod.id("block/bricks_uv"), null, "_bricks"),
                            RemapSpriteSource.mapping(Testmod.id("block/stone_bricks_uv"), null, "_stone_bricks"),
                            RemapSpriteSource.mapping(Testmod.id("block/mud_bricks_uv"), null, "_mud_bricks"),
                            RemapSpriteSource.mapping(Testmod.id("block/tiles_uv"), null, "_tiles"),
                            RemapSpriteSource.mapping(Testmod.id("block/cut_uv"), "cut_", null),
                            RemapSpriteSource.mapping(Testmod.id("block/full_uv"), null, "_block")
                    )
            ));
            sources.add(new ConnectedTexturesSpriteSource(Testmod.id("block/wood")));
//            sources.add(new SingleFile(Identifier.withDefaultNamespace("item/egg")));
//            sources.add(new SingleFile(Identifier.withDefaultNamespace("item/brown_egg")));
//            sources.add(new SingleFile(Identifier.withDefaultNamespace("item/blue_egg")));
        });
        ItemModelEvents.BEFORE_BAKE.register((id, unbaked) -> {
            if(id.equals(BuiltInRegistries.ITEM.getKey(Items.DIAMOND))) {
                return ItemModelUtils.composite(unbaked, ItemModelUtils.plainModel(TEST_MODEL));
            }else if(id.equals(BuiltInRegistries.ITEM.getKey(Items.COPPER_INGOT))) {
                return new TransformedItemModel.Unbaked();
            }
            return unbaked;
        });
        ModelEvents.ADD_UNBAKED.register(modelCollector -> {
            ModelTemplates.FLAT_ITEM.create(TEST_MODEL, TextureMapping.layer0(Items.EMERALD), modelCollector);
            ModelTemplates.FLAT_ITEM.create(TestmodItems.TOURMALINE.item(), TextureMapping.layer0(TestmodItems.TOURMALINE.item()), modelCollector);
            ModelTemplates.FLAT_ITEM.create(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.item(), TextureMapping.layer0(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.item()), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlockItems.TOURMALINE_BLOCK.block(), TextureMapping.cube(TestmodBlockItems.TOURMALINE_BLOCK.block()), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlockItems.WOOD.block(), TextureMapping.cube(TextureMapping.getBlockTexture(TestmodBlockItems.WOOD.block(), "_all")), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlockItems.RAINBOW.block(), TextureMapping.cube(TextureMapping.getBlockTexture(TestmodBlockItems.RAINBOW.block())), modelCollector);
        });
        ItemModelEvents.ADD_UNBAKED.register(assetCollector -> {
            assetCollector.accept(TestmodItems.TOURMALINE.item(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodItems.TOURMALINE.item())));
            assetCollector.accept(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.item(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.item())));
            assetCollector.accept(TestmodBlockItems.TOURMALINE_BLOCK.item(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlockItems.TOURMALINE_BLOCK.block())));
            assetCollector.accept(TestmodBlockItems.WOOD.item(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlockItems.WOOD.block())));
            assetCollector.accept(TestmodBlockItems.RAINBOW.item(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlockItems.RAINBOW.block())));
            assetCollector.accept(TestmodBlockItems.BIG_DIAMOND.item(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(Blocks.DIAMOND_BLOCK)));
        });
        ModelLoadingPlugin.register(pluginCtx -> {
            pluginCtx.registerBlockStateResolver(TestmodBlockItems.TOURMALINE_BLOCK.block(), ctx -> {
                ctx.setModel(ctx.block().defaultBlockState(), new SingleVariant.Unbaked(new Variant(ModelLocationUtils.getModelLocation(TestmodBlockItems.TOURMALINE_BLOCK.block()))).asRoot());
            });
        });
        LanguageEvents.MODIFY_TRANSLATIONS.register((translations, languageCode, rightToLeft) -> {
            translations.putIfAbsent(TestmodItems.TOURMALINE.item().getDescriptionId(), "Tourmaline");
            translations.putIfAbsent(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.item().getDescriptionId(), "Checkered Armor Trim");
            translations.putIfAbsent(TestmodBlockItems.TOURMALINE_BLOCK.block().getDescriptionId(), "Block of Tourmaline");
            translations.putIfAbsent(TestmodBlockItems.WOOD.block().getDescriptionId(), "Wood");
            translations.putIfAbsent(TestmodBlockItems.RAINBOW.block().getDescriptionId(), "Rainbow");
            translations.putIfAbsent(TestmodBlockItems.BIG_DIAMOND.block().getDescriptionId(), "Big Diamond");
        });
        ArmorTrimRegistry.registerMaterial(Testmod.TOURMALINE_TRIM_MATERIAL);
        ArmorTrimRegistry.registerMaterial(Testmod.OBSIDIAN_TRIM_MATERIAL);

        REGISTRY.itemModel("transformed", TransformedItemModel.Unbaked.CODEC);
        REGISTRY.itemModel("nest", UnbakedNestItemModel.CODEC);

        REGISTRY.blockStateModel("nest", NestBlockStateModel.Unbaked.CODEC);

        if(SHOW_TEST_HUD) {
            HudElementRegistry.attachElementAfter(VanillaHudElements.TITLE_AND_SUBTITLE, Testmod.id("test"), (ctx, tickCounter) -> {
                Font font = Minecraft.getInstance().font;
                MutableComponent text = Component.literal("ABC 123").withStyle(ChatFormatting.ITALIC, ChatFormatting.BOLD);
                ctx.text(font, text, 10, 10, -1, false);
                ctx.text(font, text, 10, 20, -1, false);
            });
        }
    }
}
