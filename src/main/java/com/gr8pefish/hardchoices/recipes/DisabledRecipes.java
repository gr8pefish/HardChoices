package com.gr8pefish.hardchoices.recipes;


import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/*
Includes all crafting table recipes, furnaceRecipe, pulverizerRecipe, eic. are not included here.
 */

public class DisabledRecipes implements IRecipe {

    private IRecipe originalRecipe;

    public DisabledRecipes(IRecipe originalRecipe){
        this.originalRecipe = originalRecipe;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return originalRecipe.matches(inv, world);
    }

    @Override
    public int getRecipeSize() {
        return originalRecipe.getRecipeSize();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return originalRecipe.getRecipeOutput();
    }

    @Override
    //Checks, via a helper method, what the crafting result should be (null or the original result)
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return RecipeHelper.getCraftingResult(originalRecipe);
    }

}
