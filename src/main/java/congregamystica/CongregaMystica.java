package congregamystica;

import congregamystica.proxy.CommonProxy;
import mod.emt.congregamystica.Tags;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = CongregaMystica.MOD_ID,
        name = CongregaMystica.MOD_NAME,
        version = CongregaMystica.MOD_VERSION,
        dependencies = CongregaMystica.DEPENDENCIES
)
public class CongregaMystica {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = "Congrega Mystica";
    public static final String MOD_VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:thaumcraft";

    public static final String CLIENT_PROXY = "congregamystica.proxy.ClientProxy";
    public static final String COMMON_PROXY = "congregamystica.proxy.CommonProxy";

    public static Logger LOGGER;

    @Mod.Instance(MOD_ID)
    public static CongregaMystica instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit() {
        LOGGER.info("Starting " + MOD_NAME);
        proxy.preInit();
        LOGGER.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init() {
        proxy.init();
        LOGGER.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit() {
        proxy.postInit();
        LOGGER.debug("Finished postInit phase.");
    }
}
