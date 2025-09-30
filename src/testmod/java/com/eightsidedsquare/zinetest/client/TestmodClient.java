package com.eightsidedsquare.zinetest.client;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.ConnectedTexturesAtlasSource;
import com.eightsidedsquare.zine.client.atlas.GeneratorAtlasSource;
import com.eightsidedsquare.zine.client.atlas.RemapAtlasSource;
import com.eightsidedsquare.zine.client.atlas.generator.NoiseSpriteGenerator;
import com.eightsidedsquare.zine.client.atlas.generator.SpriteProperties;
import com.eightsidedsquare.zine.client.atlas.gradient.Gradient1D;
import com.eightsidedsquare.zine.client.item.ItemModelEvents;
import com.eightsidedsquare.zine.client.language.LanguageEvents;
import com.eightsidedsquare.zine.client.model.ModelEvents;
import com.eightsidedsquare.zine.client.registry.ClientRegistryHelper;
import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistry;
import com.eightsidedsquare.zinetest.core.TestmodBlocks;
import com.eightsidedsquare.zinetest.core.TestmodInit;
import com.eightsidedsquare.zinetest.core.TestmodItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.model.SimpleBlockStateModel;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class TestmodClient implements ClientModInitializer {

    public static final ClientRegistryHelper REGISTRY = ClientRegistryHelper.create(TestmodInit.MOD_ID);
    public static int entityId;
    private static final Identifier TEST_MODEL = TestmodInit.id("item/test");
    private static final boolean SHOW_TEST_HUD = false;

    @Override
    public void onInitializeClient() {
        AtlasEvents.modifySourcesEvent(Identifier.ofVanilla("blocks")).register(sources -> {
            sources.add(new GeneratorAtlasSource(
                    new NoiseSpriteGenerator(
                            Gradient1D.builder()
                                    .pt(0xff1a372c, 0.2f)
                                    .pt(0xff42bb1f, 0.6f)
                                    .pt(0xffcaf732, 0.9f)
                                    .build(),
                            new DoublePerlinNoiseSampler.NoiseParameters(1, 1, 1, 1),
                            42069L,
                            new Vector2f(3f, 2f),
                            new Vector3f(0, -5, 1f),
                            0.05f
                    ),
                    TestmodInit.id("goo"),
                    new SpriteProperties(16, 16, 64, 2, false)
            ));
            sources.add(new RemapAtlasSource(
                    List.of(
                            RemapAtlasSource.textureSet(
                                    "polished_granite",
                                    Map.of(
                                            0, RemapAtlasSource.texture(
                                                    Identifier.of("block/debug"),
                                                    TestmodInit.id("block/offset"),
                                                    -16,
                                                    16
                                            ),
                                            255, RemapAtlasSource.texture(Identifier.of("block/tuff"))
                                    )
                            )

                    ),
                    List.of(
                            RemapAtlasSource.mapping(TestmodInit.id("block/bricks_uv"), null, "_bricks"),
                            RemapAtlasSource.mapping(TestmodInit.id("block/stone_bricks_uv"), null, "_stone_bricks"),
                            RemapAtlasSource.mapping(TestmodInit.id("block/mud_bricks_uv"), null, "_mud_bricks"),
                            RemapAtlasSource.mapping(TestmodInit.id("block/tiles_uv"), null, "_tiles"),
                            RemapAtlasSource.mapping(TestmodInit.id("block/cut_uv"), "cut_", null),
                            RemapAtlasSource.mapping(TestmodInit.id("block/full_uv"), null, "_block")
                    )
            ));
            sources.add(new ConnectedTexturesAtlasSource(TestmodInit.id("block/wood")));
        });
        ItemModelEvents.BEFORE_BAKE.register((id, unbaked) -> {
            if(Items.DIAMOND.zine$modelEquals(id)) {
                return ItemModels.composite(unbaked, ItemModels.basic(TEST_MODEL));
            }else if(Items.COPPER_INGOT.zine$modelEquals(id)) {
                return new TransformedItemModel.Unbaked();
            }
            return unbaked;
        });
        ModelEvents.ADD_UNBAKED.register(modelCollector -> {
            Models.GENERATED.upload(TEST_MODEL, TextureMap.layer0(Items.EMERALD), modelCollector);
            Models.GENERATED.upload(TestmodItems.TOURMALINE, TextureMap.layer0(TestmodItems.TOURMALINE), modelCollector);
            Models.GENERATED.upload(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE, TextureMap.layer0(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE), modelCollector);
            Models.CUBE_ALL.upload(TestmodBlocks.TOURMALINE_BLOCK, TextureMap.all(TestmodBlocks.TOURMALINE_BLOCK), modelCollector);
            Models.CUBE_ALL.upload(TestmodBlocks.GOO, TextureMap.all(TestmodInit.id("goo")), modelCollector);
            Models.CUBE_ALL.upload(TestmodBlocks.WOOD, TextureMap.all(TextureMap.getSubId(TestmodBlocks.WOOD, "_all")), modelCollector);
            Models.CUBE_ALL.upload(TestmodBlocks.RAINBOW, TextureMap.all(TextureMap.getId(TestmodBlocks.RAINBOW)), modelCollector);
        });
        ItemModelEvents.ADD_UNBAKED.register(assetCollector -> {
            assetCollector.accept(TestmodItems.TOURMALINE, ItemModels.basic(ModelIds.getItemModelId(TestmodItems.TOURMALINE)));
            assetCollector.accept(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE, ItemModels.basic(ModelIds.getItemModelId(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE)));
            assetCollector.accept(TestmodItems.TOURMALINE_BLOCK, ItemModels.basic(ModelIds.getBlockModelId(TestmodBlocks.TOURMALINE_BLOCK)));
            assetCollector.accept(TestmodItems.GOO, ItemModels.basic(ModelIds.getBlockModelId(TestmodBlocks.GOO)));
            assetCollector.accept(TestmodItems.WOOD, ItemModels.basic(ModelIds.getBlockModelId(TestmodBlocks.WOOD)));
            assetCollector.accept(TestmodItems.RAINBOW, ItemModels.basic(ModelIds.getBlockModelId(TestmodBlocks.RAINBOW)));
            assetCollector.accept(TestmodItems.BIG_DIAMOND, ItemModels.basic(ModelIds.getBlockModelId(Blocks.DIAMOND_BLOCK)));
        });
        ModelLoadingPlugin.register(pluginCtx -> {
            pluginCtx.registerBlockStateResolver(TestmodBlocks.TOURMALINE_BLOCK, ctx -> {
                ctx.setModel(ctx.block().getDefaultState(), new SimpleBlockStateModel.Unbaked(new ModelVariant(ModelIds.getBlockModelId(TestmodBlocks.TOURMALINE_BLOCK))).cached());
            });
            pluginCtx.registerBlockStateResolver(TestmodBlocks.GOO, ctx -> {
                ctx.setModel(ctx.block().getDefaultState(), new SimpleBlockStateModel.Unbaked(new ModelVariant(ModelIds.getBlockModelId(TestmodBlocks.GOO))).cached());
            });
        });
        ModelLoadingPlugin.register(ctx -> ctx.modifyItemModelBeforeBake().register((model, context) -> {
            if(Items.DIAMOND.zine$modelEquals(context.itemId())) {
                return ItemModels.composite(model, ItemModels.basic(TEST_MODEL));
            }
            return model;
        }));
        LanguageEvents.MODIFY_TRANSLATIONS.register((translations, languageCode, rightToLeft) -> {
            translations.putIfAbsent(TestmodItems.TOURMALINE.getTranslationKey(), "Tourmaline");
            translations.putIfAbsent(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.getTranslationKey(), "Checkered Armor Trim");
            translations.putIfAbsent(TestmodItems.TOURMALINE_BLOCK.getTranslationKey(), "Block of Tourmaline");
            translations.putIfAbsent(TestmodItems.GOO.getTranslationKey(), "Goo");
            translations.putIfAbsent(TestmodItems.WOOD.getTranslationKey(), "Wood");
            translations.putIfAbsent(TestmodItems.RAINBOW.getTranslationKey(), "Rainbow");
            translations.putIfAbsent(TestmodItems.BIG_DIAMOND.getTranslationKey(), "Big Diamond");
        });
        ArmorTrimRegistry.registerMaterial(TestmodInit.TOURMALINE_TRIM_MATERIAL);
        ArmorTrimRegistry.registerMaterial(TestmodInit.OBSIDIAN_TRIM_MATERIAL);
        ArmorTrimRegistry.registerPattern(TestmodInit.CHECKERED_TRIM_PATTERN);

        REGISTRY.itemModel("transformed", TransformedItemModel.Unbaked.CODEC);

        if(SHOW_TEST_HUD) {
            HudElementRegistry.attachElementAfter(VanillaHudElements.TITLE_AND_SUBTITLE, TestmodInit.id("test"), (ctx, tickCounter) -> {
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                MutableText text = Text.literal("ABC 123").formatted(Formatting.ITALIC, Formatting.BOLD);
                ctx.drawText(textRenderer, text, 10, 10, -1, false);
                ctx.drawText(textRenderer, text, 10, 20, -1, false);
            });
        }
    }
}
