package com.gr8pefish.hardchoices.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class ConfigHandler {

    public static ArrayList<String> blackList;
    //public static Boolean useDisableItem;
    //public static Boolean resetOnDeath;

	public static void init(File file)
	{
		Configuration config = new Configuration(file);
		blackList = new ArrayList<String>();
		config.load();
		//============================Blocks and Items========================
		ConfigCategory modListings = config.getCategory("modids");
		modListings.setComment("Format is that each line contains a comma delimited list of mods.\n"
                            +   "When one mod in each group is crafted, the other mods in that group are mods."
							+ 	"Each mod group is on their own line.");

		Property blacklistP = config.get("ModIds", "blacklist", new String[] {});
		blacklistP.comment = "ModIds blacklisted - add each group on a separate line.\n"
							+ "Format is 'modid1,modid2,modid3...'\n\n"
                            + "Example:   S:blacklist < \n"
                            + "  Thaumcraft,BloodMagic,Witchery\n"
                            + "  ThermalExpansion,MinefactoryReloaded\n "
                            + " >\n";
		blackList.addAll(Arrays.asList(blacklistP.getStringList())); //TODO make lowercase

        //TODO - these features eventually
//		Property category2 = config.get("DisableItem", "disableItem", true);
//		category2.comment = "Choose true if you would like the output of a mods mod's recipe to be a special 'disabled item'.\n"
//							+"Enter false to simply have nothing appear instead.";
//      useDisableItem = (category2.getBoolean());

//		Property category3 = config.get("Gameplay", "gameplay", false);
//		category2.comment = "Choose true if you would like the player's disabled mods to reset on death."
//      resetOnDeath = (category3.getBoolean());
		
		config.save();
	}
}
