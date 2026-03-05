package com.eightsidedsquare.zine.common.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeImpl<T extends Recipe<?>> implements RecipeType<T> {

    private final String id;

    public RecipeTypeImpl(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
