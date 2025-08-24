package congregamystica.network;

import congregamystica.CongregaMystica;
import congregamystica.network.packets.PacketEnumParticle;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandlerCM {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void preInit() {
        int id = 0;
        INSTANCE.registerMessage(PacketEnumParticle.Handler.class, PacketEnumParticle.class, id++, Side.CLIENT);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CongregaMystica.MOD_ID);
    }
}
