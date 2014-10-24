package com.gr8pefish.hardchoices.mods;

import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OldDisabledItem extends Item {

    public OldDisabledItem(){
        super();
    }

    /*
    This method is special, it refers to if the item is used to craft the next item allow it to be removed from the grid.
    This could work, every item in the blacklisted mod would have to be converted to this DisabledItem type though.
     */

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack){
        String[] modid = stack.getUnlocalizedName().split(":");
        for (DisabledMod mod : DisabledHandler.disabledModsList){
            if (mod.getDisabled() && mod.getModId().equals(modid[0])){
                return false;
            }
        }
        return true;
    }


}
