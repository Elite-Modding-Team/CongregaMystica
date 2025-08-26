package congregamystica.integrations.rustic;

import congregamystica.api.IProxy;
import congregamystica.integrations.rustic.golems.GolemMaterialIronwood;
import congregamystica.registry.RegistrarCM;

public class RusticCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialIronwood());
    }

    @Override
    public void init() {

    }
}
