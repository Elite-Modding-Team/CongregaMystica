package congregamystica.network;

import congregamystica.CongregaMystica;
import congregamystica.network.packets.PacketAuraToClient;
import congregamystica.network.packets.PacketParticleToClient;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandlerCM {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void preInit() {
        int id = 0;
        INSTANCE.registerMessage(PacketParticleToClient.Handler.class, PacketParticleToClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketAuraToClient.Handler.class, PacketAuraToClient.class, id++, Side.CLIENT);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CongregaMystica.MOD_ID);
    }
}
