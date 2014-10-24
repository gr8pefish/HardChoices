package com.gr8pefish.hardchoices.networking;

import com.gr8pefish.hardchoices.util.Logger;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MyMessage implements IMessage{

    private String text;

    public MyMessage() { }

    public MyMessage(String text) {
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

    public static class Handler implements IMessageHandler<MyMessage, IMessage> {

        @Override
        public IMessage onMessage(MyMessage message, MessageContext ctx){
            Logger.log(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));

            ExtendedPlayer player = ExtendedPlayer.get(ctx.getServerHandler().playerEntity);
            player.syncData(); //currently doesn't do anything

            return null; //I shouldn't need to return a message, I just need to update the player's data
        }
    }

}
