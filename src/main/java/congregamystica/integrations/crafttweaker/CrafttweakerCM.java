package congregamystica.integrations.crafttweaker;

import congregamystica.api.IProxy;
import congregamystica.utils.helpers.PechHelper;
import crafttweaker.mc1120.events.ScriptRunEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrafttweakerCM implements IProxy {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onScriptReloading(ScriptRunEvent.Pre event) {

    }

    @SubscribeEvent
    public void onScriptReloadingPost(ScriptRunEvent.Post event) {
        PechHelper.verifyTrades();
    }
}
