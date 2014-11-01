package com.gr8pefish.hardchoices.networking;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.proxies.CommonProxy;
import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class UpdatePlayerMessage implements  IMessage{
    private NBTTagCompound compound;

    public UpdatePlayerMessage() {}

    public UpdatePlayerMessage(EntityPlayer player){
        compound = new NBTTagCompound();
        ExtendedPlayer.get(player).saveNBTData(compound);
    }

    @Override
    public void fromBytes(ByteBuf buf){
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<UpdatePlayerMessage, IMessage> {
        @Override
        public IMessage onMessage(UpdatePlayerMessage message, MessageContext ctx) {
            EntityPlayer player = HardChoices.proxy.getPlayerEntity(ctx);
            ExtendedPlayer.get(player).loadNBTData(message.compound);
            return null;
        }
    }
}