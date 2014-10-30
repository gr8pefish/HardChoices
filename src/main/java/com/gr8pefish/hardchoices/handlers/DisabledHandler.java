package com.gr8pefish.hardchoices.handlers;

import java.util.*;

import com.gr8pefish.hardchoices.players.PlayerData;
import com.gr8pefish.hardchoices.recipes.DisabledBaseRecipes;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.recipes.RecipeHelper;
import ibxm.Player;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.util.Logger;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;


@SuppressWarnings("rawtypes")
public class DisabledHandler {

    public static ArrayList<String> disabledModsList = new ArrayList<String>();

    private static ArrayList<ShapedOreRecipe> registeredShapedOreRecipes;
    private static ArrayList<ShapelessOreRecipe> registeredShapelessOreRecipes;
    private static ArrayList<ShapedRecipes> registeredShapedRecipes;
    private static ArrayList<ShapelessRecipes> registeredShapelessRecipes;

    public static ArrayList<IRecipe> registeredRecipes;

	
	public static void init(){
		ConfigHandler.init(HardChoices.config);

        registeredShapedOreRecipes = new ArrayList<ShapedOreRecipe>();
        registeredShapelessOreRecipes = new ArrayList<ShapelessOreRecipe>();
        registeredShapedRecipes = new ArrayList<ShapedRecipes>();
        registeredShapelessRecipes = new ArrayList<ShapelessRecipes>();
        registeredRecipes = new ArrayList<IRecipe>();

        for (Object recipe : CraftingManager.getInstance().getRecipeList()){
            if (recipe.getClass().equals(ShapedOreRecipe.class)) {
                registeredShapedOreRecipes.add((ShapedOreRecipe) recipe);
                registeredRecipes.add((IRecipe) recipe);
            }else if (recipe.getClass().equals(ShapelessOreRecipe.class)) {
                registeredShapelessOreRecipes.add( (ShapelessOreRecipe) recipe);
                registeredRecipes.add((IRecipe) recipe);
            }else if (recipe.getClass().equals(ShapedRecipes.class)) {
                registeredShapedRecipes.add( (ShapedRecipes) recipe);
                registeredRecipes.add((IRecipe) recipe);
            }else if (recipe.getClass().equals(ShapelessRecipes.class)) {
                registeredShapelessRecipes.add( (ShapelessRecipes) recipe);
                registeredRecipes.add((IRecipe) recipe);
            }else {
                Logger.log("Unknown recipe type: " + recipe.getClass().getName());
                registeredRecipes.add((IRecipe) recipe);
            }
        }

        initDisabledMods();
        RecipeHelper.init();
        changeRecipes();
        PlayerData.initModGroups();
	}

    //initialize the disabled mods from the config file

    public static void initDisabledMods(){
        ArrayList<String> allModSets = ConfigHandler.blackList;
        String[] splitString;
        for (String modGroup : allModSets) {
            splitString = modGroup.split(",");
            if (splitString.length<2){
                Logger.log("Need more than one mod per set to disable: "+splitString[0]);
                continue;
            }
            Collections.addAll(disabledModsList, splitString);
//            for (String configModId : splitString){
//                DisabledMod disabledmod = new DisabledMod(configModId);
//                disabledModsList.add(configModId);
//            }
        }
    }

    //Change out the old recipes for my version of the recipe, with the overridden getCraftingResult

    public static void changeRecipes(){
        //TODO initialize for all recipes that implement IRecipe (dynamically if possible, so other mods' special recipe types work)
        for (IRecipe recipe : registeredRecipes) {
            if (recipe.getRecipeOutput() != null && disabledModsList.contains(PlayerData.getModIdFromItemStack(recipe.getRecipeOutput()))) { //not registering chisel correctly
                RecipeHelper.replaceRecipes(recipe);
            }
        }

//        for (ShapedRecipes recipe : registeredShapedRecipes) {
//            DisabledBaseRecipes.init(recipe);
//        }
//
//        for (ShapedOreRecipe recipe : registeredShapedOreRecipes) {
//            DisabledBaseRecipes.init(recipe);
//        }

    }



}
