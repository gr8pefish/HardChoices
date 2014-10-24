package com.gr8pefish.hardchoices.mods;

import com.gr8pefish.hardchoices.Logger;
import com.gr8pefish.hardchoices.handlers.ConfigHandler;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.players.ExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class DisabledMod {

    protected String modId;
    protected ArrayList<String> modGroup;
    protected boolean blacklisted;
    protected boolean disabled;

    public DisabledMod(String modid) {
        modId = modid;
        modGroup = initModGroup();
        blacklisted = initBlacklisted();
        disabled = false;

    }

    private ArrayList<String> initModGroup(){
        ArrayList<String> allModSets = ConfigHandler.blackList;
        for (String modGroup : allModSets) {
            if (modGroup.contains(this.modId)) {
                return new ArrayList<String>(Arrays.asList(modGroup.split(",")));
            }
        }
        return null;
    }

    private boolean initBlacklisted(){
        ArrayList<String> allModSets = ConfigHandler.blackList;
        for (String modGroup : allModSets) {
            if (modGroup.contains(this.modId)) {
                return true;
            }
        }
        return false;
    }

    public boolean getBlacklisted(){
        return this.blacklisted;
    }

    public boolean getDisabled(){
        return this.disabled;
    }

    public ArrayList<String> getModGroup(){
        return this.modGroup;
    }

    public String getModId(){
        return this.modId;
    }

    public void setDisabled() {
        this.disabled = true;
    }

    public boolean getBlacklistGroupDisabled(){
        if (this.modGroup != null && this.modGroup.contains(this.modId)){
            for (DisabledMod mod : DisabledHandler.disabledModsList){
                if (this.modGroup.contains(mod.getModId()) && !mod.getModId().equals(this.getModId())){
                    return mod.getDisabled();
                }
            }
        }
        return false;
    }

    public void disableGroupMods(EntityPlayer player){
        for (DisabledMod mod : DisabledHandler.disabledModsList) {
            if (this.modGroup.contains(mod.getModId()) && !mod.getModId().equals(this.getModId())){
                mod.setDisabled();
                ExtendedPlayer playerData = ExtendedPlayer.get(player);
                playerData.disabledMods.put(mod.modId, true);

                Logger.log("Disabled mod: "+mod.getModId());
            }
        }
    }

//    public void getCurrentCraftingRecipe(EntityPlayer player, IInventory inventory){
//        if inventory
//    }
//
//    public void removeModRecipe(String modId){
//        CraftingManager.getInstance().getRecipeList().remove();
//        value = (IRecipe) CraftingManager.getInstance().getRecipeList().get(index);
//        ShapedRecipes tester = new ShapedRecipes();
////        if (attemptedRecipe.getRecipeOutput() == ItemStack anyBannedModItem) {
////            removeItemInCraftedSlot
////        }
//    }
}
