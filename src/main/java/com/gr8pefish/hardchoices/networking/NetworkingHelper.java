package com.gr8pefish.hardchoices.networking;

import net.minecraft.entity.player.EntityPlayer;

public class NetworkingHelper {

    public boolean isClientSide(EntityPlayer player) {
        return player.worldObj.isRemote;
    }
}
