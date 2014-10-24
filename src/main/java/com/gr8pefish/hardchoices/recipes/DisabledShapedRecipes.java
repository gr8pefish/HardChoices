package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import java.lang.reflect.Field;

public class DisabledShapedRecipes extends ShapedRecipes{

    ShapedRecipes originalShapedRecipes; //unused

    public DisabledShapedRecipes(ShapedRecipes shapedRecipes){
        super(shapedRecipes.recipeWidth, shapedRecipes.recipeHeight, shapedRecipes.recipeItems, shapedRecipes.getRecipeOutput());
        this.originalShapedRecipes = shapedRecipes;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) { //all this is just to get the player that is crafting to check if they are "allowed" to craft this item
        return RecipeHelper.getCraftingResult(inventoryCrafting, originalShapedRecipes);
    }
}
