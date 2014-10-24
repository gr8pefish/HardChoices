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
    public void Info(EntityPlayer player) {

        // Sending packet to server
        IMessage msg = new SimplePacket.SimpleMessage(500, true);
        PacketHandler.net.sendToServer(msg);

        // Sending packet to client
        if (player instanceof EntityPlayerMP) {
            IMessage message = new SimplePacket.SimpleMessage(800, false);
            PacketHandler.net.sendTo(message, (EntityPlayerMP) player);
        }
    }
}
