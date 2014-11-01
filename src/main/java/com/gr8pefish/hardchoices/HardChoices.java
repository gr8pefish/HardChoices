
package com.gr8pefish.hardchoices;

import java.io.File;

import com.gr8pefish.hardchoices.events.FMLEventHandler;
import com.gr8pefish.hardchoices.events.ForgeEventHandler;
import com.gr8pefish.hardchoices.handlers.*;
import com.gr8pefish.hardchoices.networking.NetworkingHandler;
import com.gr8pefish.hardchoices.util.InformationCommand;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

import com.gr8pefish.hardchoices.proxies.CommonProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/*

TODO

Bugs:


Immediate:
change it so each save/world has new data for the player (onEntityConstructing?)

Later:
add in tooltip that warns player about deleting mod
commands to directly enable/disable mods (if OP)
config option dying resets disabled mods (off by default)

Possible Features:
Add in mod item (lock) and make it not removable from grid instead of blank. //slot.canTakeStack(EntityPlayer player){return false;}

 */

@Mod(modid = ModInformation.MODID, name = ModInformation.NAME, version = ModInformation.VERSION)

public class HardChoices {

	public static File config;

    @Instance(ModInformation.MODID)
	public static HardChoices instance = new HardChoices();

    @SidedProxy(clientSide="com.gr8pefish.hardchoices.proxies.ClientProxy", serverSide="com.gr8pefish.hardchoices.proxies.CommonProxy")
	public static CommonProxy proxy; 

    @EventHandler
	public void preInit(FMLPreInitializationEvent event){
        //register packets and config file
        NetworkingHandler.initPackets();
        config=event.getSuggestedConfigurationFile();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
        //register both my event handlers here
        FMLCommonHandler.instance().bus().register(new FMLEventHandler());
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
        //read from config and update recipes as needed
		DisabledHandler.init();
        DisabledHandler.changeRecipes();
	}

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event){
        //register minecrfat command
        event.registerServerCommand(new InformationCommand());
    }
	
}