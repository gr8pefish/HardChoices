package com.gr8pefish.hardchoices.recipes;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DisabledShapedOreRecipe extends ShapedOreRecipe {

    public ShapedOreRecipe originalShapedOreRecipe;

    public DisabledShapedOreRecipe(ShapedOreRecipe shapedOreRecipe){
//        super(shapedOreRecipe.getRecipeOutput(), ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shapedOreRecipe, "input"));
        super(shapedOreRecipe.getRecipeOutput(), DisabledBaseRecipes.getInputs(shapedOreRecipe));
        this.originalShapedOreRecipe = shapedOreRecipe;
    }

    @Override
    //Checks, via a helper method, what the crafting result should be (null or the original result)
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return RecipeHelper.getCraftingResult(inventoryCrafting, originalShapedOreRecipe);
    }

}
