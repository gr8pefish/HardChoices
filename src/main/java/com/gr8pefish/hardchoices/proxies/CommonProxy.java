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


    public static void saveProxyData(EntityPlayer player) {
        ExtendedPlayer playerData = ExtendedPlayer.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);
        Logger.log("storing proxy data");
        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }


    public static void loadProxyData(EntityPlayer player) {
        Logger.log("starting the loading of proxy data....");
        ExtendedPlayer playerData = ExtendedPlayer.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if(savedData != null) {
            Logger.log("loading proxy data");
            playerData.loadNBTData(savedData);
        }

        NetworkingHandler.network.sendTo(new UpdatePlayerMessage(player), (EntityPlayerMP) player);

    }
}

