package com.gr8pefish.hardchoices;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class Logger {

	public static void log(String text)
	{
		System.out.println("[HardChoices] " + text);
	}
	
	public static void chatLog(EntityPlayer player, String text){
		player.addChatComponentMessage(new ChatComponentText(text));
	}

}