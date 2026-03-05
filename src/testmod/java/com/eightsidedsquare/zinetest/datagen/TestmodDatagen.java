package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zinetest.core.TestmodInit;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;

public class TestmodDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(TestmodSoundListGen::new);
        pack.addProvider(TestmodRecipeGen.Provider::new);
        pack.addProvider(TestmodItemTagGen::new);
        pack.addProvider(TestmodDynamicGen::new);
        pack.addProvider(TestmodModelGen::new);
        pack.addProvider(TestmodMaterialMappingGen::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.TRIM_MATERIAL, registerable -> {
            registerable.register(TestmodInit.TOURMALINE_TRIM_MATERIAL, new TrimMaterial(
                    MaterialAssetGroup.create("tourmaline"),
                    Component.literal("Tourmaline").withStyle(Style.EMPTY.withColor(0x71f3bb))
            ));
            registerable.register(TestmodInit.OBSIDIAN_TRIM_MATERIAL, new TrimMaterial(
                    MaterialAssetGroup.create("obsidian"),
                    Component.literal("Obsidian").withStyle(Style.EMPTY.withColor(0x3b2754))
            ));
        });
        registryBuilder.add(Registries.TRIM_PATTERN, registerable -> {
            registerable.register(TestmodInit.CHECKERED_TRIM_PATTERN, new TrimPattern(
                    TestmodInit.CHECKERED_TRIM_PATTERN.identifier(),
                    Component.literal("Checkered Armor Trim"),
                    false
            ));
        });
    }
}
