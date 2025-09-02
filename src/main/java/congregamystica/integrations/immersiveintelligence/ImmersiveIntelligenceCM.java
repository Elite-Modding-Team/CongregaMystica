package congregamystica.integrations.immersiveintelligence;

import congregamystica.api.IModModule;
import congregamystica.integrations.immersiveintelligence.items.ItemDrillHeadTippedThaumium;
import congregamystica.integrations.immersiveintelligence.items.ItemDrillHeadTippedVoid;
import congregamystica.registry.RegistrarCM;

public class ImmersiveIntelligenceCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadTippedThaumium());
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadTippedVoid());
    }
}
