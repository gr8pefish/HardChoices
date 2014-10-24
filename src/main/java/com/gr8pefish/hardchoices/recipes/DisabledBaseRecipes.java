package com.gr8pefish.hardchoices.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DisabledBaseRecipes {

    public static ShapedRecipes shapedRecipe;
    public static ShapedOreRecipe shapedOreRecipe;

    public static void init(ShapedRecipes shaped){

        shapedRecipe = new DisabledShapedRecipes(shaped);

        GameRegistry.addRecipe(shapedRecipe); //put in new recipe
        CraftingManager.getInstance().getRecipeList().remove(shaped); //remove the old

    }

    public static void  init(ShapedOreRecipe shapedOre){

//        shapedOreRecipe = new DisabledShapedOreRecipe(shapedOre);
//
//        GameRegistry.addRecipe(shapedOreRecipe);
//        CraftingManager.getInstance().getRecipeList().remove(shapedOre);

    }
}
