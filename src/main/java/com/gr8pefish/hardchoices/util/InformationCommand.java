package com.gr8pefish.hardchoices.util;

import com.gr8pefish.hardchoices.Logger;
import com.gr8pefish.hardchoices.handlers.ConfigHandler;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import com.gr8pefish.hardchoices.players.PlayerData;
import com.gr8pefish.hardchoices.recipes.RecipeHelper;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class InformationCommand implements ICommand {

    private List aliases= new ArrayList();
    public InformationCommand() {
        this.aliases.add("dis");
    }

    @Override
    public String getCommandName() {
        return "disabledmods";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "disabledmods";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] strings) {
        EntityPlayer player;
        if(sender instanceof EntityPlayer) {
            player = (EntityPlayer) sender;

            ChatComponentText groups = new ChatComponentText("Categories:  ");
            ChatComponentText disabled = new ChatComponentText("Disabled: ");

            try {
                ExtendedPlayer newPlayer = ExtendedPlayer.get(player);
                Set<String> keys = newPlayer.disabledMods.keySet();
                for (String key: keys){
                    disabled.appendText(key);
                    disabled.appendText(newPlayer.disabledMods.get(key).toString());
//                    if (!(newPlayer.disabledMods.get(key))){
//                        disabled.appendText(key);
//                    }
                }
            } catch (ClassCastException e){
                Logger.log("Couldn't cast to extended player");
            }

            ArrayList<String> allModSets = ConfigHandler.blackList;
            for (int i = 0; i < allModSets.size(); i++){
                groups.appendText("Category "+i+": "+allModSets.get(i) + "     ");
            }

            player.addChatMessage(groups);
            player.addChatMessage(disabled);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] strings) {
        return this.aliases;
//        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int integer) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
