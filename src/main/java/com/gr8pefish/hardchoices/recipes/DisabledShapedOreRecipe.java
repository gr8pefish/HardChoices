package com.gr8pefish.hardchoices.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DisabledShapedOreRecipe extends ShapedOreRecipe {

    public ShapedOreRecipe originalShapedOreRecipe;

    public DisabledShapedOreRecipe(ShapedOreRecipe shapedOreRecipe){
        super(shapedOreRecipe.getRecipeOutput(), shapedOreRecipe.getInput());
        this.originalShapedOreRecipe = shapedOreRecipe;
    }

    @Override
    //Checks, via a helper method, what the crafting result should be (null or the original result)
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return RecipeHelper.getCraftingResult(inventoryCrafting, originalShapedOreRecipe);
    }

}
