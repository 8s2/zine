package com.eightsidedsquare.zine.mixin.advancement;

import com.eightsidedsquare.zine.common.advancement.ZineAdvancementRewards;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;

@Mixin(AdvancementRewards.class)
public abstract class AdvancementRewardsMixin implements ZineAdvancementRewards {

    @Shadow @Final @Mutable
    private int experience;

    @Shadow @Final @Mutable
    private List<ResourceKey<LootTable>> loot;

    @Shadow @Final @Mutable
    private List<ResourceKey<Recipe<?>>> recipes;

    @Shadow @Final @Mutable
    private Optional<CacheableFunction> function;

    @Override
    public void zine$setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public void zine$setLoot(List<ResourceKey<LootTable>> loot) {
        this.loot = loot;
    }

    @Override
    public void zine$addLootTable(ResourceKey<LootTable> lootTable) {
        this.loot = ZineUtil.addOrUnfreeze(this.loot, lootTable);
    }

    @Override
    public void zine$addLootTables(List<ResourceKey<LootTable>> lootTables) {
        this.loot = ZineUtil.addAllOrUnfreeze(this.loot, lootTables);
    }

    @Override
    public void zine$setRecipes(List<ResourceKey<Recipe<?>>> recipes) {
        this.recipes = recipes;
    }

    @Override
    public void zine$addRecipe(ResourceKey<Recipe<?>> recipe) {
        this.recipes = ZineUtil.addOrUnfreeze(this.recipes, recipe);
    }

    @Override
    public void zine$addRecipes(List<ResourceKey<Recipe<?>>> recipes) {
        this.recipes = ZineUtil.addAllOrUnfreeze(this.recipes, recipes);
    }

    @Override
    public void zine$setFunction(@Nullable CacheableFunction function) {
        this.function = Optional.ofNullable(function);
    }
}
