package com.eightsidedsquare.zine.common.advancement;

import net.minecraft.commands.CacheableFunction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ZineAdvancementRewards {

    default void zine$setExperience(int experience) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setLoot(List<ResourceKey<LootTable>> loot) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addLootTable(ResourceKey<LootTable> lootTable) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addLootTables(List<ResourceKey<LootTable>> lootTables) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setRecipes(List<ResourceKey<Recipe<?>>> recipes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRecipe(ResourceKey<Recipe<?>> recipe) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRecipes(List<ResourceKey<Recipe<?>>> recipes) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setFunction(@Nullable CacheableFunction function) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
