package com.gr8pefish.hardchoices.handlers;

import java.util.*;

import com.gr8pefish.hardchoices.players.PlayerData;
import com.gr8pefish.hardchoices.recipes.RecipeHelper;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;


import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.util.Logger;


/*
Handler for performing all of the tasks that my mod accomplishes. Not necessary, but makes the code structure more understandable.
 */
@SuppressWarnings("rawtypes")
public class DisabledHandler {

    public static ArrayList<String> disabledModsList = new ArrayList<String>();

    public static ArrayList<IRecipe> registeredRecipes;
	
	public static void init(){

        ConfigHandler.init(HardChoices.config);

        registeredRecipes = new ArrayList<IRecipe>();

        for (Object recipe : CraftingManager.getInstance().getRecipeList()){
            registeredRecipes.add((IRecipe) recipe);
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
            for (String configModId : splitString){
                disabledModsList.add(configModId.toLowerCase().trim().replaceAll("\\s+",""));
                Logger.log("Adding the mod "+configModId.toLowerCase().trim()+" to the disabledList.");
            }
        }
    }

    //Change out the old recipes for my version of the recipe, with the overridden getCraftingResult
    public static void changeRecipes(){
        //TODO maybe add in other recipes (so smelting, pulverizing, etc..)
        for (IRecipe recipe : registeredRecipes) {
            if (recipe.getRecipeOutput() != null && disabledModsList.contains(PlayerData.getModIdFromItemStack(recipe.getRecipeOutput()))) {
                RecipeHelper.replaceRecipes(recipe);
            }
        }
    }



}
