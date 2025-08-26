package congregamystica.integrations.rustic;

import congregamystica.api.IProxy;
import congregamystica.integrations.rustic.blocks.BlockChandelierBrass;
import congregamystica.integrations.rustic.golems.GolemMaterialIronwood;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.ModHelper;
import congregamystica.utils.libs.ModIds;

public class RusticCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialIronwood());
        //Chain
        //Candle
        if(ModHelper.isModLoaded(ModIds.rustic.modId, ModIds.ConstVersions.rustic_new, true, false)) {
            //Candelabra - Rustic 1.2.0+
        }
        //Lever Candle
        RegistrarCM.addAdditionToRegister(new BlockChandelierBrass());
        //Chairs
        //Tables
    }
}
