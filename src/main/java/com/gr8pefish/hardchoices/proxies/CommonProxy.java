package com.gr8pefish.hardchoices.proxies;

import com.gr8pefish.hardchoices.networking.NetworkingHandler;
import com.gr8pefish.hardchoices.networking.UpdatePlayerMessage;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class CommonProxy {

    private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

    /**
     * Returns a side-appropriate EntityPlayer for use during message handling
     */
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    //Start of additional code to save the player's data somewhere where it won't disappear when they die.
    //Credit goes to coolAlias, a lot of this was copied from their tutorial mod

    /**
     * Adds an entity's custom data to the map for temporary storage
     * @param compound An NBT Tag Compound that stores the IExtendedEntityProperties data only
     */
    public static void storeEntityData(String name, NBTTagCompound compound)
    {
        extendedEntityData.put(name, compound);
    }

    /**
     * Removes the compound from the map and returns the NBT tag stored for name or null if none exists
     */
    public static NBTTagCompound getEntityData(String name)
    {
        return extendedEntityData.remove(name);
    }

    /**
     * Makes it look nicer in the methods save/loadProxyData
     */
    private static String getSaveKey(EntityPlayer player) {
        return player.getDisplayName() + ":" + ExtendedPlayer.EXTENDED_PLAYER_DISABLED_MODS;
    }

    /*
     *   Saves the data in the proxy so that the data is not deleted when the client player dies or exits the game.
     */

    public static void saveProxyData(EntityPlayer player) {

        ExtendedPlayer playerData = ExtendedPlayer.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);
        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    /*
     * Loads the data from the proxy, and then updates the client to know that changes have been made.
     */

    public static void loadProxyData(EntityPlayer player) {

        ExtendedPlayer playerData = ExtendedPlayer.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if(savedData != null) {
            playerData.loadNBTData(savedData);
        }

        //update the client from the server data via this message
        NetworkingHandler.network.sendTo(new UpdatePlayerMessage(player), (EntityPlayerMP) player);

    }
}

