package congregamystica.integrations.appliedenergistics2;

import congregamystica.api.IModModule;
import congregamystica.integrations.appliedenergistics2.additions.IntegrationsAE2;
import congregamystica.registry.RegistrarCM;

public class AppliedEnergisticsCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new IntegrationsAE2());
    }
}
