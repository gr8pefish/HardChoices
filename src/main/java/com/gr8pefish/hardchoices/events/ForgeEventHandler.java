package com.gr8pefish.hardchoices.events;


import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

//All the events for the forge bus. These have to do with the extended player data.

public class ForgeEventHandler {

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) {
            ExtendedPlayer.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){ //server side
            //TODO Add in config death option here
            CommonProxy.saveProxyData((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){ //server side
            CommonProxy.loadProxyData((EntityPlayer) event.entity);
        }
    }
}
