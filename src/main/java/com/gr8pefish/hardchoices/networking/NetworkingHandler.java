package com.gr8pefish.hardchoices.networking;

import com.gr8pefish.hardchoices.ModInformation;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkingHandler {

    public static SimpleNetworkWrapper network;

    public static void initPackets() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.CHANNEL);
        registerMessage(UpdateCraftingMessage.Handler.class, UpdateCraftingMessage.class, Side.SERVER);
        registerMessage(UpdatePlayerMessage.Handler.class, UpdatePlayerMessage.class, Side.CLIENT);
    }

    private static int nextPacketId = 0;

    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}

