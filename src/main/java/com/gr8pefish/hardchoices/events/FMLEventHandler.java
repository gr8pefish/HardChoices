package com.gr8pefish.hardchoices.events;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.networking.MyMessage;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class FMLEventHandler {

    /*
    When an item is crafted, check if the item is from a modset in the config file, and disable the mods in it's set if needed
     */

//    @SideOnly(Side.SERVER)
    @SubscribeEvent //fires on both client and server
    public void itemCraftedEvent(PlayerEvent.ItemCraftedEvent event) { //fired after pulling the item away
        GameRegistry.UniqueIdentifier identifier =  GameRegistry.findUniqueIdentifierFor(event.crafting.getItem());
        Logger.log(event.player.getDisplayName() + " crafted an item from the mod " + identifier.modId + " in the inventory " + event.craftMatrix.getInventoryName());
        for (DisabledMod mod : DisabledHandler.disabledModsList){
            if (identifier.modId.equals(mod.getModId())){
                if (mod.getDisabled()){ //should probably change to be player specific
                    //disabled recipes handled by DisabledRecipes already
                }else{
                    if (!mod.getBlacklistGroupDisabled()){ //if mod group isn't disabled (TODO - CHANGE TO BE PLAYER SPECIFIC)
                        mod.disableGroupMods(event.player);             //disable them
                        CommonProxy.saveProxyData(event.player);        //save changes
                        HardChoices.network.sendToServer(new MyMessage("updtCrftng"));     //send message form client to server to update
                    }
                }
            }
        }
    }


}
