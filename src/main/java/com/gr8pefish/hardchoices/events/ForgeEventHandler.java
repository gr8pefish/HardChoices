package com.gr8pefish.hardchoices.events;

import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ForgeEventHandler {

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) {
            ExtendedPlayer.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){

            CommonProxy.saveProxyData((EntityPlayer) event.entity);
//            NBTTagCompound playerData = new NBTTagCompound();
//            ExtendedPlayer.get((EntityPlayer) event.entity).saveNBTData(playerData);
//            CommonProxy.storeEntityData(((EntityPlayer) event.entity).getDisplayName(), playerData);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
        {
            CommonProxy.loadProxyData((EntityPlayer) event.entity);
//            NBTTagCompound playerData = CommonProxy.getEntityData(((EntityPlayer) event.entity).getDisplayName());
//            if (playerData != null)
//            {
//                ExtendedPlayer.get((EntityPlayer) event.entity).loadNBTData(playerData);
//            }
        }
    }
}
