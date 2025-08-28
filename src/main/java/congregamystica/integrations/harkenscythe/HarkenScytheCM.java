package congregamystica.integrations.harkenscythe;

import congregamystica.api.IModModule;
import congregamystica.integrations.harkenscythe.golems.GolemMaterialBiomass;
import congregamystica.integrations.harkenscythe.golems.GolemMaterialLivingmetal;
import congregamystica.registry.RegistrarCM;

public class HarkenScytheCM implements IModModule {

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialBiomass());
        RegistrarCM.addAdditionToRegister(new GolemMaterialLivingmetal());
    }
}
