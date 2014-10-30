package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.handlers.ConfigHandler;
import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;

/*

Currently unused. I would like to eventually make each recipe that is an instance of IRecipe follow my other classes, but that will have to wait until I fix the more basic issues.

 */


public class DisabledRecipes implements IRecipe { //DisabledShapedOreRecipe , ShapelessOreRecipe

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
        return RecipeHelper.getCraftingResult(inventoryCrafting, originalRecipe);
    }

}
