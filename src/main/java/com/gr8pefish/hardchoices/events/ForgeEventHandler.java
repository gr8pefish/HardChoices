package com.gr8pefish.hardchoices.events;

import com.gr8pefish.hardchoices.networking.UpdateCraftingMessage;
import com.gr8pefish.hardchoices.networking.NetworkingHandler;
import com.gr8pefish.hardchoices.networking.UpdatePlayerMessage;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;


//All the events for the forge bus. These have to do with the extended player data.

public class ForgeEventHandler {

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
//        if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) { //should maybe be firing if already an extended player
//            ExtendedPlayer.register((EntityPlayer) event.entity);
//        }
        if (event.entity instanceof  EntityPlayer){
            if (ExtendedPlayer.get((EntityPlayer)event.entity) != null){ //TODO not firing when it should
                ExtendedPlayer thePlayer = ExtendedPlayer.get((EntityPlayer)event.entity);
                for (String mod :thePlayer.disabledMods.keySet()){
                    Logger.log("initalized player, mod name: "+mod+ " disabled: "+thePlayer.disabledMods.get(mod));
                }
            }else{
                Logger.log("Registering new ExtendedPlayer..");
                ExtendedPlayer.register((EntityPlayer)event.entity);
            }

        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){
//        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){
//            //TODO Add in config death option here
//            Logger.log("Death!");
//            CommonProxy.saveProxyData((EntityPlayer) event.entity);
//        }
        if (event.entity instanceof EntityPlayer){
            if (ExtendedPlayer.get((EntityPlayer)event.entity) != null) {
                Logger.log("Death!");
                CommonProxy.saveProxyData((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){
            Logger.log("Entity joined world! (server side)");
            CommonProxy.loadProxyData((EntityPlayer) event.entity);
//            NetworkingHandler.network.sendTo(new UpdatePlayerMessage((EntityPlayer) event.entity), (EntityPlayerMP) event.entity );
        }
//        if (event.entity instanceof EntityPlayer){
//            if (ExtendedPlayer.get((EntityPlayer)event.entity) != null) {
//                Logger.log("ExtendedPlayer joined world!");
//                CommonProxy.loadProxyData((EntityPlayer) event.entity);
//            }
//        }
    }
}
