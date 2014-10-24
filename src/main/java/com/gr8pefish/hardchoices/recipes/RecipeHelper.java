package com.gr8pefish.hardchoices.recipes;

import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.players.PlayerData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.lang.reflect.Field;
import java.util.Set;

public class RecipeHelper {

    /*
    Returns the crafting result from an inventoryCrafting.

    Returning null means the player (accessed through reflection) does not have permission to craft that item.
    Otherwise, the original output is returned.
     */
    public static ItemStack getCraftingResult(InventoryCrafting inventoryCrafting, IRecipe originalRecipe){
        try{
            Field container = inventoryCrafting.getClass().getDeclaredField("eventHandler");
            container.setAccessible(true);
            try {
                Object instanceContainer = container.get(inventoryCrafting);
                if (instanceContainer.getClass().equals(ContainerWorkbench.class)){
                    ContainerWorkbench containerWorkbench = (ContainerWorkbench) instanceContainer;
                    SlotCrafting firstSlot = (SlotCrafting) containerWorkbench.getSlot(0);
                    try{
                        Field player = firstSlot.getClass().getDeclaredField("thePlayer");
                        player.setAccessible(true);
                        try{
                            EntityPlayer thePlayer = (EntityPlayer) player.get(firstSlot);
//                            Logger.log("Player who is crafting: " + thePlayer.getDisplayName());
                            Logger.log("isRecipeDisabled: "+PlayerData.isModDisabledForPlayer(thePlayer, originalRecipe.getRecipeOutput()));
                            if (PlayerData.isModDisabledForPlayer(thePlayer, originalRecipe.getRecipeOutput())){
                                return null;
//                              return new ItemStack(ItemRegistry.disabledItem); //Feature: make it not be removable from crafting grid
                            }else{
                               return originalRecipe.getRecipeOutput();
                            }
                        }catch (IllegalAccessException e){
                            Logger.log("No access to player");
                        }
                    }catch(NoSuchFieldException e){
                        Logger.log("No player field.");
                    }
                }else if (instanceContainer.getClass().equals(ContainerPlayer.class)){
                    Field player = instanceContainer.getClass().getDeclaredField("thePlayer");
                    player.setAccessible(true);
                    try {
                        EntityPlayer thePlayer = (EntityPlayer) player.get(instanceContainer);
//                        Logger.log("Player who is crafting: " + thePlayer.getDisplayName());
//                        Logger.log("Normal return item: "+originalRecipe.getRecipeOutput().getDisplayName());
//                        return originalShapedRecipes.getRecipeOutput();
                        return null;
                    }catch (IllegalAccessException e){
                        Logger.log("Illegal access to player");
                    }
                Logger.log(instanceContainer.getClass().getName());
                }else if (instanceContainer.getClass().isAssignableFrom(Container.class)){ //TODO, fix this: it isn't true when it should be (maybe change to x instanceof y)
                    Container containerUsed = (Container) instanceContainer;
                    Logger.log("container");
                    try{
                        Field set = containerUsed.getClass().getDeclaredField("playerList");
                        set.setAccessible(true);
                        Set theSet = (Set) set.get(containerUsed);
                        for (Object player: theSet){
                            Logger.log(player.toString());
                        }
                    }catch (NoSuchFieldException e){
                        Logger.log("Illegal access to playerList");
                    }
                }
            }catch (IllegalAccessException e){
                Logger.log("Illegal access to instance");
            }
        }catch (NoSuchFieldException e){
            Logger.log("Couldn't get field");
        }
        return null;
    }


    public static boolean isItemDisabled(ItemStack stack){ //TODO deprecated?
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        for (DisabledMod mod : DisabledHandler.disabledModsList){
            if (mod.getDisabled() && mod.getModId().equals(identifier.modId)){
                return true;
            }
        }
        return false;
    }






}
