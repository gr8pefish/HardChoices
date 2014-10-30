package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DisabledBaseRecipes {

    public static ShapedRecipes shapedRecipe;
    public static ShapedOreRecipe shapedOreRecipe;

    public static void init(ShapedRecipes shaped){

        shapedRecipe = new DisabledShapedRecipes(shaped);

        GameRegistry.addRecipe(shapedRecipe); //put in new recipe
        CraftingManager.getInstance().getRecipeList().remove(shaped); //remove the old

    }

    public static void  init(ShapedOreRecipe shapedOre){

        shapedOreRecipe = new DisabledShapedOreRecipe(shapedOre); //errors shown in method below

//        GameRegistry.addRecipe(shapedOreRecipe);
//        CraftingManager.getInstance().getRecipeList().remove(shapedOre);

    }

    //passed in when trying to call super(output, inputs) of ShapedOreRecipe as the second parameter
    public static Object[] getInputs(ShapedOreRecipe shapedOreRecipe){
//        Object[] originalInputs = shapedOreRecipe.getInput();
        return shapedOreRecipe.getInput();  //net.minecraftforge.oredict.OreDictionary$UnmodifiableArrayList cannot be cast to java.lang.Character
                                            // (so it returns Object[] full of UnmodifiableArrayLists (which in turn are composed of possible oreDictionary equivalent items)
                                            // (and I want Object[] full of ArrayLists because the init of shapedOreRecipes needs to modify them)
//        return ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shapedOreRecipe, "input"); //same error as .getInput() one line above
    }
}
