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



//    private IRecipe recipe = new IRecipe()
//
//    public DisabledRecipes(ShapedOreRecipe shapedOreRecipe) {
//        new ShapedOreRecipe(shapedOreRecipe.getRecipeOutput(), shapedOreRecipe.getInput());
//    }
//
//    public DisabledRecipes(ShapelessOreRecipe shapelessOreRecipe) {
//        super();
//    }
//
//    public DisabledRecipes(ShapedRecipes shapedRecipes) {
//        super();
//    }
//
//    public DisabledRecipes(ShapelessRecipes shapelessRecipes) {
//        super();
//    }

    private IRecipe newRecipe;
    private IRecipe originalRecipe;



    public DisabledRecipes(IRecipe originalRecipe){

        DisabledHandler.registeredRecipes.iterator();

        if (originalRecipe instanceof ShapedRecipes){

            ShapedRecipes myShapedRecipe = new ShapedRecipes(((ShapedRecipes) originalRecipe).recipeHeight, ((ShapedRecipes) originalRecipe).recipeWidth, ((ShapedRecipes) originalRecipe).recipeItems, originalRecipe.getRecipeOutput());
            Logger.log("Shaped recipe: ");
        }
        this.originalRecipe = originalRecipe;

        GameRegistry.addRecipe(newRecipe);
        CraftingManager.getInstance().getRecipeList().remove(originalRecipe);

    }

//    public static void init(ShapedRecipes shaped){
//
//        shapedRecipe = new DisabledRecipes(shaped);
//
//        GameRegistry.addRecipe(shapedRecipe); //put in new recipe
//        CraftingManager.getInstance().getRecipeList().remove(shaped); //remove the old
//
//    }



    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    public boolean isItemDisabled(ItemStack stack){
        String[] modid = stack.getUnlocalizedName().split(":");
        for (DisabledMod mod : DisabledHandler.disabledModsList){
            if (mod.getDisabled() && mod.getModId().equals(modid[0])){
                return true;
            }
        }
        return false;
    }
}
