package congregamystica.integrations.rustic;

import congregamystica.api.IProxy;
import congregamystica.integrations.rustic.blocks.*;
import congregamystica.integrations.rustic.golems.GolemMaterialIronwood;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.ModHelper;
import congregamystica.utils.libs.ModIds;

public class RusticCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialIronwood());
        /*
            Registry Order:
                - Chain
                - Candle
                - Double Candle (Candelabra)
                - Lever Candle
                - Chandelier
                - Chairs
                - Tables
         */

        RegistrarCM.addAdditionToRegister(new BlockChainCM("chain_brass"));
        RegistrarCM.addAdditionToRegister(new BlockCandleCM("candle_brass"));
        //Candelabra - Rustic 1.2.0+
        if(ModHelper.isModLoaded(ModIds.rustic.modId, ModIds.ConstVersions.rustic_new, true, false)) {
            //TODO: See if this causes crashes with earlier versions of rustic
            RegistrarCM.addAdditionToRegister(new BlockCandleDoubleCM("candle_double_brass"));
        }
        RegistrarCM.addAdditionToRegister(new BlockCandleLeverCM("candle_lever_brass"));
        RegistrarCM.addAdditionToRegister(new BlockChandelierCM("chandelier_brass"));
        //Chairs
        if(true) {//TODO: Config to disable chairs
            RegistrarCM.addAdditionToRegister(new BlockChairCM("greatwood"));
            RegistrarCM.addAdditionToRegister(new BlockChairCM("silverwood"));
        }
        //Tables
        if(true) {//TODO: Config to disable tables
            RegistrarCM.addAdditionToRegister(new BlockTableCM("greatwood"));
            RegistrarCM.addAdditionToRegister(new BlockTableCM("silverwood"));
        }
    }
}
