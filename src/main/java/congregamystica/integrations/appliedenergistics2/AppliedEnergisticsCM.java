package congregamystica.integrations.appliedenergistics2;

import congregamystica.api.IProxy;
import congregamystica.integrations.appliedenergistics2.additions.IntegrationsAE2;
import congregamystica.registry.RegistrarCM;

public class AppliedEnergisticsCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new IntegrationsAE2());
    }
}
