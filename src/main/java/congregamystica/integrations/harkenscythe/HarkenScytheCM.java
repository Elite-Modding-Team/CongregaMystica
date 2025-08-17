package congregamystica.integrations.harkenscythe;

import congregamystica.api.IProxy;
import congregamystica.integrations.harkenscythe.additions.GolemMaterialBiomass;
import congregamystica.integrations.harkenscythe.additions.GolemMaterialLivingmetal;
import congregamystica.registry.RegistrarCM;

public class HarkenScytheCM implements IProxy {
    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialBiomass());
        RegistrarCM.addAdditionToRegister(new GolemMaterialLivingmetal());
    }
}
