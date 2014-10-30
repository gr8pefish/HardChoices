package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.networking.MyMessage;
import com.gr8pefish.hardchoices.networking.NetworkingHelper;
import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.players.PlayerData;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeHelper {

    private static List<IRecipe> recipes;

    @SuppressWarnings("unchecked")
    public static void init(){
        recipes = CraftingManager.getInstance().getRecipeList();
    }


    private static final String fieldOne = "eventHandler";
    private static final String fieldTwo = "thePlayer";
    private static final String fieldThree = "thePlayer";

//    private static final String fieldOne = "field_70465_c";
//    private static final String fieldTwo = "field_75238_b";
//    private static final String fieldThree = "field_82862_h";



    /*
    Returns the crafting result from an inventoryCrafting.

    Returning null means the player (accessed through reflection) does not have permission to craft that item.
    Otherwise, the original output is returned.
     */
    public static ItemStack getCraftingResult(InventoryCrafting inventoryCrafting, IRecipe originalRecipe) {
        Logger.log("Getting crafting result");
        try {
//            Object instanceContainer = ObfuscationReflectionHelper.getPrivateValue(InventoryCrafting.class, inventoryCrafting, "eventHandler");
            Field container = inventoryCrafting.getClass().getDeclaredField(fieldOne);
            container.setAccessible(true);
            try {
                Object instanceContainer = container.get(inventoryCrafting);
                if (instanceContainer.getClass().equals(ContainerWorkbench.class)) {
                    ContainerWorkbench containerWorkbench = (ContainerWorkbench) instanceContainer;
                    SlotCrafting firstSlot = (SlotCrafting) containerWorkbench.getSlot(0);
                    try {
                        Field player = firstSlot.getClass().getDeclaredField(fieldTwo);
                        player.setAccessible(true);
                        try {
                            EntityPlayer thePlayer = (EntityPlayer) player.get(firstSlot);
                            if (PlayerData.isModDisabledForPlayer(thePlayer, originalRecipe.getRecipeOutput())) {
                                return null;
//                              return new ItemStack(ItemRegistry.disabledItem); //Feature: make it not be removable from crafting grid
                            } else {
                                return originalRecipe.getRecipeOutput().copy();
                            }
                        } catch (IllegalAccessException e) {
                            Logger.log("No access to player");
                        }
                    } catch (NoSuchFieldException e) {
                        Logger.log("No player field.");
                    }
                } else if (instanceContainer.getClass().equals(ContainerPlayer.class)) {
                    Field player = instanceContainer.getClass().getDeclaredField(fieldThree);
                    player.setAccessible(true);
                    try {
                        EntityPlayer thePlayer = (EntityPlayer) player.get(instanceContainer);
                        if (PlayerData.isModDisabledForPlayer(thePlayer, originalRecipe.getRecipeOutput())) {
                            return null;
                        } else {
                            return originalRecipe.getRecipeOutput().copy();
                        }
                    } catch (IllegalAccessException e) {
                        Logger.log("Illegal access to player");
                    }
                    Logger.log(instanceContainer.getClass().getName());
                } else if (Container.class.isAssignableFrom(instanceContainer.getClass())) {
//                } else if (instanceContainer.getClass().getSuperclass().equals(Container.class)) { //TODO alter this, too restricting?
                    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                    if (PlayerData.isModDisabledForPlayer(player, originalRecipe.getRecipeOutput())) {
                        return null;
                    } else{
                        return originalRecipe.getRecipeOutput().copy();
                    }
                }
            } catch (IllegalAccessException e) {
                Logger.log("Illegal access to instance");
            }
        } catch (NoSuchFieldException e) {
            Logger.log("Couldn't get field");
        }
        return null;
    }

    public static void replaceRecipes(IRecipe originalrecipe){
        IRecipe myRecipe = new DisabledRecipes(originalrecipe); //create my recipe
        GameRegistry.addRecipe(myRecipe); //put in new recipe
        recipes.remove(originalrecipe); //remove the old
    }
}
