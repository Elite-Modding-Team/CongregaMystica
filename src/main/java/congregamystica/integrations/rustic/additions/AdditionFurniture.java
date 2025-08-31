package congregamystica.integrations.rustic.additions;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.blocks.furniture.BlockChairCM;
import congregamystica.integrations.rustic.blocks.furniture.BlockTableCM;
import congregamystica.registry.RegistrarCM;

public class AdditionFurniture implements IAddition, IProxy {
    @Override
    public void preInit() {
        //Chairs
        RegistrarCM.addAdditionToRegister(new BlockChairCM("greatwood"));
        RegistrarCM.addAdditionToRegister(new BlockChairCM("silverwood"));
        //Tables
        RegistrarCM.addAdditionToRegister(new BlockTableCM("greatwood"));
        RegistrarCM.addAdditionToRegister(new BlockTableCM("silverwood"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableFurniture;
    }
}
