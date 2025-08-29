package congregamystica.integrations.naturesaura;

import congregamystica.api.IModModule;
import congregamystica.integrations.naturesaura.items.ItemAuraCaster;
import congregamystica.registry.RegistrarCM;

public class NaturesAuraCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemAuraCaster());
    }
}
