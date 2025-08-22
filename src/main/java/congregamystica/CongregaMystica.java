package congregamystica;

import congregamystica.proxy.CommonProxy;
import congregamystica.registry.CreativeTabsCM;
import congregamystica.utils.helpers.LogHelper;
import congregamystica.utils.libs.ModIds;
import mod.emt.congregamystica.Tags;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
    public static final String DEPENDENCIES = "required-after:thaumcraft" +
            ";after:" + ModIds.ConstIds.applied_energistics +
            ";after:" + ModIds.ConstIds.blood_magic +
            ";after:" + ModIds.ConstIds.botania +
            ";after:" + ModIds.ConstIds.harvestcraft +
            ";after:" + ModIds.ConstIds.harken_scythe +
            ";after:" + ModIds.ConstIds.immersive_engineering;

    public static final String CLIENT_PROXY = "congregamystica.proxy.ClientProxy";
    public static final String COMMON_PROXY = "congregamystica.proxy.CommonProxy";

    public static final CreativeTabs tabCM = new CreativeTabsCM(CreativeTabs.CREATIVE_TAB_ARRAY.length, "CongregaMysticaTab");

    @Mod.Instance(MOD_ID)
    public static CongregaMystica instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting " + MOD_NAME);
        proxy.preInit();
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        LogHelper.debug("Finished postInit phase.");
    }


    //TODO:
    // General:
    // A system to easily make more gauntlets. Having it depend on Thaumic Augmentation might be the better way in order to utilize augments
    // Blood Magic:
    // Bound Scribing Tools - Automatically gives the player research at specific points, probably indestructible?
    // Harken Scythe:
    // Biomass Gauntlet - Uses blood essence instead of vis, should also do other things to differentiate it more from the livingmetal one
    // Livingmetal Gauntlet - Uses soul essence instead of vis, should also do other things to differentiate it more from the biomass one
}
