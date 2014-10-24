package com.gr8pefish.hardchoices.networking;

import com.gr8pefish.hardchoices.proxies.CommonProxy;
import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MyMessage implements IMessage{

//    private NBTTagCompound compound;
    private String text;

    public MyMessage() { }

    public MyMessage(String text) { this.text = text; }

//    public MyMessage(NBTTagCompound compound) { this.compound = compound; }

    @Override
    public void fromBytes(ByteBuf buf){
        text = ByteBufUtils.readUTF8String(buf);
//        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeUTF8String(buf, text);
//        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<MyMessage, IMessage> {

        @Override
        public IMessage onMessage(MyMessage message, MessageContext ctx){
            if (ctx.side.isServer() && message.text.equals("updtCrftng")) {
//                Logger.log("Got message in server: "+message.text);
                CommonProxy.loadProxyData(ctx.getServerHandler().playerEntity);
            }

            return null; //I shouldn't need to return a message, I just need to update the player's data on the server side when I get a message from the client
        }
    }

}
