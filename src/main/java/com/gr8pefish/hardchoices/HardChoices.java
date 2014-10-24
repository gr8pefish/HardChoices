
package com.gr8pefish.hardchoices;

import java.io.File;

import com.gr8pefish.hardchoices.events.FMLEventHandler;
import com.gr8pefish.hardchoices.events.ForgeEventHandler;
import com.gr8pefish.hardchoices.handlers.*;
import com.gr8pefish.hardchoices.networking.MyMessage;
import com.gr8pefish.hardchoices.util.InformationCommand;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
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

Immediate:
Use obfuscationReflectionHelper
change it so each save/world has new data for the player (onEntityConstructing?)
make sure all lowercase for mods
loop through players mods lists when checking to see if a mod should be disabled in onCraftedEvent (instead of DisabledMods list)

Later:
only overwrite mod recipes that come from the disabledModsList (if possible)
add in tooltip that warns player about deleting mod
commands to directly enable/disable mods (if OP)
config option dying resets disabled mods (off by default)

Possible Features:
Add in mod item (lock) and make it not removable from grid instead of blank. //slot.canTakeStack(EntityPlayer player){return false;}

 */

@Mod(modid = ModInformation.MODID, name = ModInformation.NAME, version = ModInformation.VERSION)

public class HardChoices {

    public static SimpleNetworkWrapper network;
	public static File config;

    @Instance(ModInformation.MODID)
	public static HardChoices instance = new HardChoices();

    @SidedProxy(clientSide="com.gr8pefish.hardchoices.proxies.ClientProxy", serverSide="com.gr8pefish.hardchoices.proxies.ServerProxy")
	public static CommonProxy proxy; 

    @EventHandler
	public void preInit(FMLPreInitializationEvent event){
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.CHANNEL);
        network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.SERVER);
		config=event.getSuggestedConfigurationFile();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
        FMLCommonHandler.instance().bus().register(new FMLEventHandler());
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		DisabledHandler.init();
        DisabledHandler.changeRecipes();
	}

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new InformationCommand());
    }
	
}