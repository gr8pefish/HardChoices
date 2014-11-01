package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.players.PlayerData;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.lang.reflect.Field;
import java.util.List;

public class RecipeHelper {

    private static List<IRecipe> recipes;

    @SuppressWarnings("unchecked")
    public static void init(){
        recipes = CraftingManager.getInstance().getRecipeList();
    }

    public static ItemStack getCraftingResult(IRecipe originalRecipe){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ExtendedPlayer myPlayer = ExtendedPlayer.get(player);
        if (PlayerData.isModDisabledForPlayer(myPlayer, originalRecipe.getRecipeOutput())) {
            return null;
        } else{
            return originalRecipe.getRecipeOutput().copy();
        }
    }

    public static void replaceRecipes(IRecipe originalrecipe){
        IRecipe myRecipe = new DisabledRecipes(originalrecipe); //create my recipe
        GameRegistry.addRecipe(myRecipe); //put in new recipe
        recipes.remove(originalrecipe); //remove the old
    }
}
