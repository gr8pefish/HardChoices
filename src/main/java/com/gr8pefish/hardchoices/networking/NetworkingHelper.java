package com.gr8pefish.hardchoices.networking;

import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class NetworkingHelper {

    public static boolean isClientSide(EntityPlayer player) {
        return player.worldObj.isRemote;
    }

    public static void printSides(EntityPlayer thePlayer){
        if (isClientSide(thePlayer)){
            Logger.log("Client side");
        }else{
            Logger.log("Server side");
        }
    }

}
