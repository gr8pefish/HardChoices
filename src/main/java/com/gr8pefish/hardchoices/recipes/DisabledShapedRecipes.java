package com.gr8pefish.hardchoices.recipes;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

public class DisabledShapedRecipes extends ShapedRecipes{

    ShapedRecipes originalShapedRecipes; //unused

    public DisabledShapedRecipes(ShapedRecipes shapedRecipes){
        super(shapedRecipes.recipeWidth, shapedRecipes.recipeHeight, shapedRecipes.recipeItems, shapedRecipes.getRecipeOutput());
        this.originalShapedRecipes = shapedRecipes;
    }

    @Override
    //Checks, via a helper method, what the crafting result should be (null or the original result)
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return RecipeHelper.getCraftingResult(inventoryCrafting, originalShapedRecipes);
    }
}
