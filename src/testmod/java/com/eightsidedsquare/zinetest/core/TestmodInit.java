package com.eightsidedsquare.zinetest.core;

import com.eightsidedsquare.zine.common.advancement.VanillaAdvancementModifications;
import com.eightsidedsquare.zine.common.block.ModifyBlockSoundGroupCallback;
import com.eightsidedsquare.zine.common.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

import java.util.List;

public class TestmodInit implements ModInitializer {

    public static final String MOD_ID = "zinetest";
    public static final RegistryHelper REGISTRY = RegistryHelper.create(MOD_ID);

    public static final ResourceKey<TrimMaterial> TOURMALINE_TRIM_MATERIAL = ResourceKey.create(Registries.TRIM_MATERIAL, id("tourmaline"));
    public static final ResourceKey<TrimMaterial> OBSIDIAN_TRIM_MATERIAL = ResourceKey.create(Registries.TRIM_MATERIAL, id("obsidian"));
    public static final ResourceKey<TrimPattern> CHECKERED_TRIM_PATTERN = ResourceKey.create(Registries.TRIM_PATTERN, id("checkered"));

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        TestmodBlocks.init();
        TestmodItems.init();
        TestmodBlockEntities.init();

        DynamicRegistrySetupCallback.EVENT.register(registryView -> {
            registryView.registerEntryAdded(Registries.PROCESSOR_LIST, (rawId, id, processorList) -> {
                if(ProcessorLists.TRIAL_CHAMBERS_COPPER_BULB_DEGRADATION.identifier().equals(id)) {
                    processorList.zine$addProcessor(
                            new RuleProcessor(List.of(
                                    new ProcessorRule(
                                            new BlockMatchTest(Blocks.TUFF_BRICKS),
                                            AlwaysTrueTest.INSTANCE,
                                            TestmodBlocks.GOO.defaultBlockState()
                                    )
                            ))
                    );
                }
            });
        });

        ModifyBlockSoundGroupCallback.EVENT.register(ctx -> {
            ctx.setSoundGroup(SoundType.TUFF_BRICKS, Blocks.STONE_BRICKS, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICK_SLAB);
        });

        VanillaAdvancementModifications.registerTacticalFishingBucketItem(Items.AXOLOTL_BUCKET);
        VanillaAdvancementModifications.registerTrimWithAnyArmorPatternRecipe(ResourceKey.create(Registries.RECIPE, id("checkered_armor_trim_smithing_template_smithing_trim")));
    }
}
