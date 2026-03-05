package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zinetest.core.TestmodInit;
import com.eightsidedsquare.zinetest.core.TestmodItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public class TestmodRecipeGen extends RecipeProvider {

    protected TestmodRecipeGen(net.minecraft.core.HolderLookup.Provider registries, RecipeOutput exporter) {
        super(registries, exporter);
    }

    @Override
    public void buildRecipes() {
        this.trimSmithing(
                TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE,
                TestmodInit.CHECKERED_TRIM_PATTERN,
                ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE).withSuffix("_smithing_trim"))
        );
    }

    protected static class Provider extends FabricRecipeProvider {

        protected Provider(FabricPackOutput output, CompletableFuture<net.minecraft.core.HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(net.minecraft.core.HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
            return new TestmodRecipeGen(wrapperLookup, recipeExporter);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
