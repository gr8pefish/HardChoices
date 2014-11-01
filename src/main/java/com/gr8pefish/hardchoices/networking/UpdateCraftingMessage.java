package com.gr8pefish.hardchoices.networking;

import com.gr8pefish.hardchoices.proxies.CommonProxy;
import com.gr8pefish.hardchoices.util.Logger;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class UpdateCraftingMessage implements IMessage{

    private String text;

    public UpdateCraftingMessage() { }

    public UpdateCraftingMessage(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeUTF8String(buf, text);
    }

    public static class Handler implements IMessageHandler<UpdateCraftingMessage, IMessage> {

        @Override
        public IMessage onMessage(UpdateCraftingMessage message, MessageContext ctx){
            if (ctx.side.isServer() && message.text.equals("updtCrftng")) {
                CommonProxy.loadProxyData(ctx.getServerHandler().playerEntity);
            }
            return null; //I shouldn't need to return a message, I just need to update the player's data on the server side when I get a message from the client
        }
    }

}
