package congregamystica.integrations.embers;

import congregamystica.api.IModModule;
import congregamystica.integrations.embers.items.ItemClockworkCaster;
import congregamystica.registry.RegistrarCM;

public class EmbersCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemClockworkCaster());
    }
}
