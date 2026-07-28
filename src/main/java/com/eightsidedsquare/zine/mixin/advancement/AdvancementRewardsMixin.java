package com.eightsidedsquare.zine.mixin.advancement;

import com.eightsidedsquare.zine.common.advancement.ZineAdvancementRewards;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Mixin(AdvancementRewards.class)
public abstract class AdvancementRewardsMixin implements ZineAdvancementRewards {

    @Shadow @Final @Mutable
    private int experience;

    @Shadow @Final @Mutable
    private HolderSet<LootTable> loot;

    @Shadow @Final @Mutable
    private List<ResourceKey<Recipe<?>>> recipes;

    @Shadow @Final @Mutable
    private Optional<CacheableFunction> function;

    @Override
    public void zine$setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public void zine$setLoot(HolderSet<LootTable> loot) {
        this.loot = loot;
    }

    @Override
    public void zine$addLootTable(Holder<LootTable> lootTable) {
        this.loot = ZineUtil.mergeValue(this.loot, Function.identity(), lootTable);
    }

    @Override
    public void zine$addLootTables(HolderSet<LootTable> lootTables) {
        this.loot = ZineUtil.mergeValues(this.loot, lootTables);
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
