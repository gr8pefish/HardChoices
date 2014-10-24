package com.gr8pefish.hardchoices.players;

import com.gr8pefish.hardchoices.Logger;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public class PlayerData {

    public static void writePlayerData(EntityPlayer player){
        NBTTagCompound compound = new NBTTagCompound();
        int[] integers = new int[DisabledHandler.disabledModsList.size()];
        for (int i = 0; i < DisabledHandler.disabledModsList.size(); i++){
            if (DisabledHandler.disabledModsList.get(i).getDisabled()){
                integers[i] = 0;
            }else{
                integers[i] = 1;
            }
        }
        Logger.log("Integers: "+ Arrays.toString(integers));
        compound.setIntArray("disabledmods", integers);
        player.writeEntityToNBT(compound);
    }

//    public static int[] getPlayerData(EntityPlayer player){
//        NBTTagCompound compound = player.getEntityData();
//        return compound.getIntArray("disabledMods");
//    }

    public static boolean isModDisabledForPlayer(EntityPlayer player, ItemStack stack){
        ExtendedPlayer playerData = ExtendedPlayer.get(player);
        String modid = getModIdFromItemStack(stack);
        for (String modId : playerData.disabledMods.keySet()){
            if (modId.toLowerCase().trim().equals(modid.toLowerCase().trim())){
                Logger.log("Disabled mod "+modId+" : "+playerData.disabledMods.get(modId));
                return playerData.disabledMods.get(modId);
            }
            Logger.log("Testing phase: "+modId.toLowerCase().trim());
        }
//        for (int i = 0; i < DisabledHandler.disabledModsList.size(); i ++){
//            if (DisabledHandler.disabledModsList.get(i).getModId() )
//        }
//        for (int i = 0; i < data.length; i ++){
//            if (DisabledHandler.disabledModsList.get(i).getModId().equals(modid) && data[i] == 0){
//                return true;
//            }
//        }
        return false;
    }

    public static String getModIdFromItemStack(ItemStack stack){
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return identifier.modId;
    }

//    public
}
