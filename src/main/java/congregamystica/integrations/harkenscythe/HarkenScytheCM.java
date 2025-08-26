package congregamystica.integrations.harkenscythe;

import congregamystica.api.IProxy;
import congregamystica.integrations.harkenscythe.golems.GolemMaterialBiomass;
import congregamystica.integrations.harkenscythe.golems.GolemMaterialLivingmetal;
import congregamystica.registry.RegistrarCM;

public class HarkenScytheCM implements IProxy {

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialBiomass());
        RegistrarCM.addAdditionToRegister(new GolemMaterialLivingmetal());
    }
}
