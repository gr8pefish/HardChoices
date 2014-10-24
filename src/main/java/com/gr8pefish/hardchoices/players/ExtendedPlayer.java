package com.gr8pefish.hardchoices.players;

import com.gr8pefish.hardchoices.HardChoices;
import com.gr8pefish.hardchoices.Logger;
import com.gr8pefish.hardchoices.ModInformation;
import com.gr8pefish.hardchoices.handlers.DisabledHandler;
import com.gr8pefish.hardchoices.mods.DisabledMod;
import com.gr8pefish.hardchoices.networking.MyMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtendedPlayer implements IExtendedEntityProperties {

    public final static String EXTENDED_PLAYER_DISABLED_MODS = ModInformation.MODID;
    private final EntityPlayer player;
    public HashMap<String, Boolean> disabledMods;

    protected NBTTagCompound extendedPlayerCompound = new NBTTagCompound();


    public ExtendedPlayer(EntityPlayer player){
        this.player = player;
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
        //TODO start debugging here
        HardChoices.network.sendToServer(new MyMessage("testing message"));

        //load in data, load config + check if name is present in data, if not, add it in
        //groups come solely from config, read every time
        for (int i = 0; i < DisabledHandler.disabledModsList.size(); i++) {
            for (DisabledMod modName : DisabledHandler.disabledModsList) {
                if (this.disabledMods.containsKey(modName.getModId())) {
                    extendedPlayerCompound.setBoolean(modName.getModId(), this.disabledMods.get(modName.getModId())); //save the stored value as what it was before (unnecessary?)
                } else {
                    this.disabledMods.put(modName.getModId(), modName.getDisabled());
                    extendedPlayerCompound.setBoolean(modName.getModId(), modName.getDisabled()); //save the new value (as not disabled)
                }
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
    public void init(Entity entity, World world) {
    }

    public NBTTagCompound getExtProps()
    {
        return extendedPlayerCompound;
    }

    public void setExtProps(NBTTagCompound parCompound)
    {
        extendedPlayerCompound = parCompound;
    }

    public void initDisabledMods(){
        for (int i = 0; i < DisabledHandler.disabledModsList.size(); i++) {
            for (DisabledMod modName : DisabledHandler.disabledModsList) {
                this.disabledMods.put(modName.getModId(), modName.getDisabled());
            }
        }
    }
}
