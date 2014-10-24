package com.gr8pefish.hardchoices.players;

import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

//Just some helper functions for utilizing the player data

public class PlayerData {

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
        return false;
    }

    public static String getModIdFromItemStack(ItemStack stack){
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return identifier.modId;
    }

}
