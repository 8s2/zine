package com.eightsidedsquare.zinetest.datagen;

import com.eightsidedsquare.zinetest.core.Testmod;
import com.eightsidedsquare.zinetest.core.TestmodItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class TestmodRecipeProvider extends RecipeProvider {

    protected TestmodRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes() {
        this.trimSmithing(
                TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE,
                Testmod.CHECKERED_TRIM_PATTERN,
                ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(TestmodItems.CHECKERED_ARMOR_TRIM_SMITHING_TEMPLATE).withSuffix("_smithing_trim"))
        );
    }

    protected static class Provider extends FabricRecipeProvider {

        protected Provider(FabricPackOutput output, CompletableFuture<net.minecraft.core.HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
            return new TestmodRecipeProvider(recipes, advancements);
        }
    }
}
