package congregamystica.integrations.theoneprobe;

import congregamystica.api.IModModule;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.theoneprobe.providers.TOPBrainJar;
import congregamystica.integrations.theoneprobe.providers.TOPVisBattery;
import congregamystica.integrations.theoneprobe.providers.TOPVisGenerator;
import congregamystica.integrations.theoneprobe.providers.TOPVoidSiphon;
import congregamystica.utils.libs.ModIds;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TheOneProbeCM implements IModModule {
    @Override
    public void preInit() {
        if(ConfigHandlerCM.the_one_probe.brainJar) {
            FMLInterModComms.sendFunctionMessage(ModIds.ConstIds.the_one_probe, "getTheOneProbe", TOPBrainJar.class.getName());
        }
        if(ConfigHandlerCM.the_one_probe.visBattery) {
            FMLInterModComms.sendFunctionMessage(ModIds.ConstIds.the_one_probe, "getTheOneProbe", TOPVisBattery.class.getName());
        }
        if(ConfigHandlerCM.the_one_probe.visGenerator) {
            FMLInterModComms.sendFunctionMessage(ModIds.ConstIds.the_one_probe, "getTheOneProbe", TOPVisGenerator.class.getName());
        }
        if(ConfigHandlerCM.the_one_probe.voidSiphon) {
            FMLInterModComms.sendFunctionMessage(ModIds.ConstIds.the_one_probe, "getTheOneProbe", TOPVoidSiphon.class.getName());
        }
    }
}
