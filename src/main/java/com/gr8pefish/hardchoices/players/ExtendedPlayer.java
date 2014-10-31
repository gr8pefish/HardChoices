package com.gr8pefish.hardchoices.players;

import com.gr8pefish.hardchoices.ModInformation;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.HashMap;
import java.util.Set;

/*

A player class that is extended to include a map of the disabled mods for that player to be used when checking crafting recipes. Format is <modid, boolean>, where the boolean describes if it is disabled.

 */

public class ExtendedPlayer implements IExtendedEntityProperties {

    public final static String EXTENDED_PLAYER_DISABLED_MODS = ModInformation.MODID;
    public HashMap<String, Boolean> disabledMods;

    public NBTTagCompound extendedPlayerCompound = new NBTTagCompound();


    public ExtendedPlayer(EntityPlayer player){
        this.disabledMods = new HashMap<String, Boolean>();
        initDisabledMods();
    }

    public static final void register(EntityPlayer player){
        player.registerExtendedProperties(ExtendedPlayer.EXTENDED_PLAYER_DISABLED_MODS, new ExtendedPlayer(player));
    }

    public static final ExtendedPlayer get(EntityPlayer player){
        return (ExtendedPlayer) player.getExtendedProperties(EXTENDED_PLAYER_DISABLED_MODS);
    }


    @Override
    public void saveNBTData(NBTTagCompound compound) {
        //load in data, load config + check if name is present in data, if not, add it in
        //groups come solely from config, read every time (inefficient, but the only way to make it work with changed configs when a world is already made)
        for (String modName : DisabledHandler.disabledModsList) {
            if (!this.disabledMods.containsKey(modName)) {
                this.disabledMods.put(modName, false);
                extendedPlayerCompound.setBoolean(modName, false); //save the new value (as not disabled)
            }else{
                extendedPlayerCompound.setBoolean(modName, this.disabledMods.get(modName)); //save the stored value as what it was before
            }
        }
        compound.setTag(EXTENDED_PLAYER_DISABLED_MODS, extendedPlayerCompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXTENDED_PLAYER_DISABLED_MODS);
        Set keySet = properties.func_150296_c(); //get tagMap for keySet
        for (Object key : keySet){
            this.disabledMods.put((String)key, properties.getBoolean((String)key));
        }
    }

    @Override
    public void init(Entity entity, World world) { }


    public void initDisabledMods(){
        for (String modName : DisabledHandler.disabledModsList) {
            this.disabledMods.put(modName, false);
        }

    }
}
