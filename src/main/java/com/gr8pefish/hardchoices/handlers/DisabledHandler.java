package com.gr8pefish.hardchoices.handlers;

import java.util.*;

import com.gr8pefish.hardchoices.recipes.DisabledBaseShapedRecipes;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.Logger;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;


@SuppressWarnings("rawtypes")
public class DisabledHandler {

    public static ArrayList<DisabledMod> disabledModsList = new ArrayList<DisabledMod>();
    private static ArrayList<ShapedOreRecipe> registeredShapedOreRecipes;
    private static ArrayList<ShapelessOreRecipe> registeredShapelessOreRecipes;
    private static ArrayList<ShapedRecipes> registeredShapedRecipes;
    private static ArrayList<ShapelessRecipes> registeredShapelessRecipes;
    private static ArrayList<Object> registeredRecipes;

	
	public static void init(){
		ConfigHandler.init(HardChoices.config);

        registeredShapedOreRecipes = new ArrayList<ShapedOreRecipe>();
        registeredShapelessOreRecipes = new ArrayList<ShapelessOreRecipe>();
        registeredShapedRecipes = new ArrayList<ShapedRecipes>();
        registeredShapelessRecipes = new ArrayList<ShapelessRecipes>();
        registeredRecipes = new ArrayList<Object>();

        for (Object recipe : CraftingManager.getInstance().getRecipeList()){
            if (recipe.getClass().equals(ShapedOreRecipe.class)) {
                registeredShapedOreRecipes.add((ShapedOreRecipe) recipe);
                registeredRecipes.add(recipe);
            }else if (recipe.getClass().equals(ShapelessOreRecipe.class)) {
                registeredShapelessOreRecipes.add( (ShapelessOreRecipe) recipe);
                registeredRecipes.add(recipe);
            }else if (recipe.getClass().equals(ShapedRecipes.class)) {
                registeredShapedRecipes.add( (ShapedRecipes) recipe);
                registeredRecipes.add(recipe);
            }else if (recipe.getClass().equals(ShapelessRecipes.class)) {
                registeredShapelessRecipes.add( (ShapelessRecipes) recipe);
                registeredRecipes.add(recipe);
            }else {
                Logger.log("Unknown recipe type: " + recipe.getClass().getName());
                registeredRecipes.add(recipe);
            }
        }

        initDisabledMods(); //TODO make it return lowercase
        changeRecipes();
	}

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
                DisabledMod disabledmod = new DisabledMod(configModId);
                disabledModsList.add(disabledmod);
            }
        }
    }

    private static void addDisabledMods(String modid){
        ArrayList<String> allModSets = ConfigHandler.blackList;
        String[] splitString;
        for (String modGroup : allModSets) {
            splitString = modGroup.split(",");
            if (splitString.length<2){
                Logger.log("Need more than one mod per set to disable: "+splitString[0]);
                continue;
            }
            if (modGroup.contains(modid)){
                for (String configModId : splitString){
                    if (!configModId.equals(modid)){
                        //TODO
                        //disable mod
                        Logger.log("Disabling mod"+configModId);
                    }
                }
            }
        }
    }

    private static void disableCrafting(){
        //TODO?
    }

    public static void changeRecipes(){
//        for (ShapedOreRecipe recipe: registeredShapedOreRecipes) {
//            DisabledShapedOreRecipe newRecipe = new DisabledShapedOreRecipe(recipe); //TODO then what? should wrap the old recipe around new class somehow...
////            recipe = new DisabledRecipes();
//            Logger.log(recipe.getRecipeOutput().getUnlocalizedName());
//        }

        for (ShapedRecipes recipe : registeredShapedRecipes) {
            DisabledBaseShapedRecipes.init(recipe);
        }

//        for (IRecipe recipe: registeredRecipes){
//            Logger.log(recipe.getRecipeOutput().getUnlocalizedName());
//        }

    }

}
