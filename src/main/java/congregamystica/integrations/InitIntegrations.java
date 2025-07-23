package congregamystica.integrations;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InitIntegrations {

    public static void preInit() {}

    public static void init() {}

    public static void postInit() {}

    @SideOnly(Side.CLIENT)
    public static void preInitClient() {}

    @SideOnly(Side.CLIENT)
    public static void initClient() {}

    @SideOnly(Side.CLIENT)
    public static void postInitClient() {}

}
