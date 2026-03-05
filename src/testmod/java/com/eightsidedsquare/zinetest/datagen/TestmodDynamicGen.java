package com.eightsidedsquare.zinetest.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public class TestmodDynamicGen extends FabricDynamicRegistryProvider {

    public TestmodDynamicGen(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider wrapperLookup, Entries entries) {
        this.add(entries, Registries.TRIM_MATERIAL);
        this.add(entries, Registries.TRIM_PATTERN);
    }

    private <T> void add(Entries entries, ResourceKey<Registry<T>> key) {
        entries.addAll((HolderLookup.RegistryLookup<T>) entries.getLookup(key));
    }

    @Override
    public String getName() {
        return "Dynamic";
    }
}
