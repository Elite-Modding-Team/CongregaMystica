package congregamystica.integrations.bloodmagic;

import congregamystica.api.IProxy;
import congregamystica.integrations.bloodmagic.additions.BloodOrbCM;
import congregamystica.integrations.bloodmagic.items.ItemBloodScribingTools;
import congregamystica.integrations.bloodmagic.items.ItemBoundCaster;
import congregamystica.registry.RegistrarCM;

public class BloodMagicCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemBoundCaster());
        RegistrarCM.addAdditionToRegister(new BloodOrbCM());
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
    }
}
