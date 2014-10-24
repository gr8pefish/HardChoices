package com.gr8pefish.hardchoices.util;

import com.gr8pefish.hardchoices.ModInformation;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.Level;

public class Logger {

    //hardcoded to log on the info level, might change that if needed
	public static void log(Object object){
        FMLLog.log(ModInformation.NAME, Level.INFO, String.valueOf(object));
	}
	
	public static void chatLog(EntityPlayer player, String text){
		player.addChatComponentMessage(new ChatComponentText(text));
	}

}