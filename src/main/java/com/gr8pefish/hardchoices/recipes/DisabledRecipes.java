package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;

/*

Currently unused. I would like to eventually make each recipe that is an instance of IRecipe follow my other classes, but that will have to wait until I fix the more basic issues.

 */

public class DisabledRecipes implements IRecipe { //DisabledShapedOreRecipe , ShapelessOreRecipe

//    private IRecipe recipe = new IRecipe()

    public DisabledRecipes(ShapedOreRecipe shapedOreRecipe) {
        new ShapedOreRecipe(shapedOreRecipe.getRecipeOutput(), shapedOreRecipe.getInput());
    }

    public DisabledRecipes(ShapelessOreRecipe shapelessOreRecipe) {
        super();
    }

    public DisabledRecipes(ShapedRecipes shapedRecipes) {
        super();
    }

    public DisabledRecipes(ShapelessRecipes shapelessRecipes) {
        super();
    }


    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        try {
            Field field = inv.getClass().getField("eventHandler");
            if (field.getClass().isInstance(Container.class)){
                Logger.log("Got a container");
            }

        }catch (NoSuchFieldException e){Logger.log("Error with catching field 'eventHandler'");return false;}
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
