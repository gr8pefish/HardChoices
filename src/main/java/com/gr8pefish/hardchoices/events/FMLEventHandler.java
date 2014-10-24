package com.gr8pefish.hardchoices.events;

import com.gr8pefish.hardchoices.Logger;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.players.PlayerData;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import com.gr8pefish.hardchoices.recipes.RecipeHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public class FMLEventHandler {

//    @SideOnly(Side.SERVER)
    @SubscribeEvent //fires on both client and server
    public void itemCraftedEvent(PlayerEvent.ItemCraftedEvent event) { //fired after pulling the item away
        GameRegistry.UniqueIdentifier identifier =  GameRegistry.findUniqueIdentifierFor(event.crafting.getItem());
        Logger.log(event.player.getDisplayName() + " crafted an item from the mod " + identifier.modId + " in the inventory " + event.craftMatrix.getInventoryName());
        for (DisabledMod mod : DisabledHandler.disabledModsList){
            if (identifier.modId.equals(mod.getModId())){
                if (mod.getDisabled()){
                    //disabled recipes handled by DisabledRecipes already
                }else{
                    if (!mod.getBlacklistGroupDisabled()){ //if mod group isn't disabled
                        mod.disableGroupMods(event.player);            //disable them
                        CommonProxy.saveProxyData(event.player);
                    }
                }
            }
        }
    }
}
