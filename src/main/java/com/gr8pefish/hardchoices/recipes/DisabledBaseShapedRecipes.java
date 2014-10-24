package com.gr8pefish.hardchoices.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class DisabledBaseShapedRecipes {

    public static ShapedRecipes recipe;

    public static void init(ShapedRecipes shapedRecipes){

        recipe = new DisabledShapedRecipes(shapedRecipes);

        GameRegistry.addRecipe(recipe); //put in new recipe
        CraftingManager.getInstance().getRecipeList().remove(shapedRecipes); //remove the old

    }
}
