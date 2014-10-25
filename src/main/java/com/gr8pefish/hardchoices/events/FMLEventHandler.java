package com.gr8pefish.hardchoices.events;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.networking.MyMessage;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.players.PlayerData;
import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;

public class FMLEventHandler {

    /*
    When an item is crafted, check if the item is from a modset in the config file, and disable the mods in it's set if needed
     */

    @SubscribeEvent //fires on both client and server
    public void itemCraftedEvent(PlayerEvent.ItemCraftedEvent event) { //fired after pulling the item away
        GameRegistry.UniqueIdentifier identifier =  GameRegistry.findUniqueIdentifierFor(event.crafting.getItem());
//        Logger.log(event.player.getDisplayName() + " crafted an item from the mod " + identifier.modId + " in the inventory " + event.craftMatrix.getInventoryName());
        ExtendedPlayer playerData = ExtendedPlayer.get(event.player);
        for (String modId : playerData.disabledMods.keySet()){
            if (modId.toLowerCase().trim().equals(identifier.modId.toLowerCase().trim())){
                if (!playerData.disabledMods.get(modId)){
                    if (!PlayerData.getBlacklistGroupDisabled(modId, event.player)){   //if mod group isn't disabled
                        PlayerData.disableGroupMods(modId, event.player);              //disable them
                        CommonProxy.saveProxyData(event.player);                       //save changes
                        HardChoices.network.sendToServer(new MyMessage("updtCrftng")); //send message from client to server to update
                    }
                }
            }
        }

    }


}
