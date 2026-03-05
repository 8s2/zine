package com.eightsidedsquare.zinetest.client;

import com.eightsidedsquare.zine.client.atlas.AtlasEvents;
import com.eightsidedsquare.zine.client.atlas.ConnectedTexturesSpriteSource;
import com.eightsidedsquare.zine.client.atlas.GeneratorSpriteSource;
import com.eightsidedsquare.zine.client.atlas.RemapSpriteSource;
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
import net.fabricmc.fabric.api.client.rendering.v1.ChunkSectionLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class TestmodClient implements ClientModInitializer {

    public static final ClientRegistryHelper REGISTRY = ClientRegistryHelper.create(TestmodInit.MOD_ID);
    public static final Identifier NEST_MODEL = TestmodInit.id("nest");
    private static final Identifier TEST_MODEL = TestmodInit.id("item/test");
    private static final boolean SHOW_TEST_HUD = false;

    @Override
    public void onInitializeClient() {
        AtlasEvents.modifySourcesEvent(Identifier.withDefaultNamespace("blocks")).register(sources -> {
            sources.add(new GeneratorSpriteSource(
                    new NoiseSpriteGenerator(
                            Gradient1D.builder()
                                    .pt(0xff1a372c, 0.2f)
                                    .pt(0xff42bb1f, 0.6f)
                                    .pt(0xffcaf732, 0.9f)
                                    .build(),
                            new NormalNoise.NoiseParameters(1, 1, 1, 1),
                            42069L,
                            new Vector2f(3f, 2f),
                            new Vector3f(0, -5, 1f),
                            0.05f
                    ),
                    TestmodInit.id("goo"),
                    new SpriteProperties(16, 16, 64, 2, false)
            ));
            sources.add(new RemapSpriteSource(
                    List.of(
                            RemapSpriteSource.textureSet(
                                    "polished_granite",
                                    Map.of(
                                            0, RemapSpriteSource.texture(
                                                    Identifier.parse("block/debug"),
                                                    TestmodInit.id("block/offset"),
                                                    -16,
                                                    16
                                            ),
                                            255, RemapSpriteSource.texture(Identifier.parse("block/tuff"))
                                    )
                            )

                    ),
                    List.of(
                            RemapSpriteSource.mapping(TestmodInit.id("block/bricks_uv"), null, "_bricks"),
                            RemapSpriteSource.mapping(TestmodInit.id("block/stone_bricks_uv"), null, "_stone_bricks"),
                            RemapSpriteSource.mapping(TestmodInit.id("block/mud_bricks_uv"), null, "_mud_bricks"),
                            RemapSpriteSource.mapping(TestmodInit.id("block/tiles_uv"), null, "_tiles"),
                            RemapSpriteSource.mapping(TestmodInit.id("block/cut_uv"), "cut_", null),
                            RemapSpriteSource.mapping(TestmodInit.id("block/full_uv"), null, "_block")
                    )
            ));
            sources.add(new ConnectedTexturesSpriteSource(TestmodInit.id("block/wood")));
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
            ModelTemplates.FLAT_ITEM.create(TestmodItems.TOURMALINE, TextureMapping.layer0(TestmodItems.TOURMALINE), modelCollector);
            ModelTemplates.FLAT_ITEM.create(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE, TextureMapping.layer0(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlocks.TOURMALINE_BLOCK, TextureMapping.cube(TestmodBlocks.TOURMALINE_BLOCK), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlocks.GOO, TextureMapping.cube(TestmodInit.id("goo")), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlocks.WOOD, TextureMapping.cube(TextureMapping.getBlockTexture(TestmodBlocks.WOOD, "_all")), modelCollector);
            ModelTemplates.CUBE_ALL.create(TestmodBlocks.RAINBOW, TextureMapping.cube(TextureMapping.getBlockTexture(TestmodBlocks.RAINBOW)), modelCollector);
        });
        ItemModelEvents.ADD_UNBAKED.register(assetCollector -> {
            assetCollector.accept(TestmodItems.TOURMALINE, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodItems.TOURMALINE)));
            assetCollector.accept(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE)));
            assetCollector.accept(TestmodItems.TOURMALINE_BLOCK, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlocks.TOURMALINE_BLOCK)));
            assetCollector.accept(TestmodItems.GOO, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlocks.GOO)));
            assetCollector.accept(TestmodItems.WOOD, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlocks.WOOD)));
            assetCollector.accept(TestmodItems.RAINBOW, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(TestmodBlocks.RAINBOW)));
            assetCollector.accept(TestmodItems.BIG_DIAMOND, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(Blocks.DIAMOND_BLOCK)));
        });
        ModelLoadingPlugin.register(pluginCtx -> {
            pluginCtx.registerBlockStateResolver(TestmodBlocks.TOURMALINE_BLOCK, ctx -> {
                ctx.setModel(ctx.block().defaultBlockState(), new SingleVariant.Unbaked(new Variant(ModelLocationUtils.getModelLocation(TestmodBlocks.TOURMALINE_BLOCK))).asRoot());
            });
            pluginCtx.registerBlockStateResolver(TestmodBlocks.GOO, ctx -> {
                ctx.setModel(ctx.block().defaultBlockState(), new SingleVariant.Unbaked(new Variant(ModelLocationUtils.getModelLocation(TestmodBlocks.GOO))).asRoot());
            });
        });
        LanguageEvents.MODIFY_TRANSLATIONS.register((translations, languageCode, rightToLeft) -> {
            translations.putIfAbsent(TestmodItems.TOURMALINE.getDescriptionId(), "Tourmaline");
            translations.putIfAbsent(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE.getDescriptionId(), "Checkered Armor Trim");
            translations.putIfAbsent(TestmodItems.TOURMALINE_BLOCK.getDescriptionId(), "Block of Tourmaline");
            translations.putIfAbsent(TestmodItems.GOO.getDescriptionId(), "Goo");
            translations.putIfAbsent(TestmodItems.WOOD.getDescriptionId(), "Wood");
            translations.putIfAbsent(TestmodItems.RAINBOW.getDescriptionId(), "Rainbow");
            translations.putIfAbsent(TestmodItems.BIG_DIAMOND.getDescriptionId(), "Big Diamond");
        });
        ArmorTrimRegistry.registerMaterial(TestmodInit.TOURMALINE_TRIM_MATERIAL);
        ArmorTrimRegistry.registerMaterial(TestmodInit.OBSIDIAN_TRIM_MATERIAL);
        ArmorTrimRegistry.registerPattern(TestmodInit.CHECKERED_TRIM_PATTERN);

        REGISTRY.itemModel("transformed", TransformedItemModel.Unbaked.CODEC);

        REGISTRY.blockStateModel("nest", NestBlockStateModel.Unbaked.CODEC);

        ChunkSectionLayerMap.putBlock(TestmodBlocks.NEST, ChunkSectionLayer.CUTOUT);

        if(SHOW_TEST_HUD) {
            HudElementRegistry.attachElementAfter(VanillaHudElements.TITLE_AND_SUBTITLE, TestmodInit.id("test"), (ctx, tickCounter) -> {
                Font textRenderer = Minecraft.getInstance().font;
                MutableComponent text = Component.literal("ABC 123").withStyle(ChatFormatting.ITALIC, ChatFormatting.BOLD);
                ctx.drawString(textRenderer, text, 10, 10, -1, false);
                ctx.drawString(textRenderer, text, 10, 20, -1, false);
            });
        }
    }
}
