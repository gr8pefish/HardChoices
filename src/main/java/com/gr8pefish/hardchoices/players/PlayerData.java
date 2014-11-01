package com.gr8pefish.hardchoices.players;

import com.gr8pefish.hardchoices.handlers.ConfigHandler;
import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

//Just some helper functions for utilizing the player data

public class PlayerData {

    public static ArrayList<ArrayList<String>> modGroups;

    public static void initModGroups(){
        modGroups = initModGroupings();
    }

    //simply returns the boolean value of the mod (referring to if it is disabled or not) for the player
    public static boolean isModDisabledForPlayer(ExtendedPlayer player, ItemStack stack){
        String modid = getModIdFromItemStack(stack);
        return player.disabledMods.get(modid);
    }

    public static String getModIdFromItemStack(ItemStack stack){
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return identifier.modId.toLowerCase().trim();
    }

    //initializes the mod groupings, reading form the config file (stripping the spaces away and making it all lowercase first)
    private static ArrayList<ArrayList<String>> initModGroupings(){
        ArrayList<ArrayList<String>> tempArrayList = new ArrayList<ArrayList<String>>();
        ArrayList<String> allModSets = ConfigHandler.blackList;
        for (String modGroup : allModSets) {
            tempArrayList.add(new ArrayList<String>(Arrays.asList(modGroup.trim().toLowerCase().replaceAll("\\s+","").split(","))));
        }
        return tempArrayList;
    }

    //returns the boolean disabled value of a mod in the mod group of the mod passed in as a parameter
    //TODO - make sure it checks all the mods in teh groups, so changing teh config file of a disabled group after launching/playing doesn't cause errors
    public static boolean getBlacklistGroupDisabled(String modid, EntityPlayer player){
        ExtendedPlayer thePlayer = ExtendedPlayer.get(player);
        for (ArrayList<String> set : modGroups) {
            if (set.contains(modid)) {
                for (String setid : set){
                    if (!setid.equals(modid) && thePlayer.disabledMods.get(setid) != null){
                        return thePlayer.disabledMods.get(setid);
                    }
                }
            }
        }
        return false;
    }

    //sets the boolean disabled value of each mod in the grouping of mods to true (i.e. it is indeed disabled)
    public static void disableGroupMods(String modid, EntityPlayer player){
        ExtendedPlayer thePlayer = ExtendedPlayer.get(player);
        for (ArrayList<String> set : modGroups) {
            if (set.contains(modid)){
                for (String modId : set) {
                    if (!modId.equals(modid) && !thePlayer.disabledMods.get(modId)) {
                        thePlayer.disabledMods.put(modId, true);
                        if (!player.worldObj.isRemote) {
                            Logger.log("Disabled the mod: " + modId); //Just so it only prints once
                        }
                    }
                }
            }
        }
    }

}
