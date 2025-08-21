package congregamystica.integrations.immersiveengineering;

import congregamystica.api.IProxy;
import congregamystica.integrations.immersiveengineering.additions.GolemMaterialTreatedWood;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadThaumium;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadVoid;
import congregamystica.integrations.immersiveengineering.items.ItemUpgradeRefining;
import congregamystica.registry.RegistrarCM;

public class ImmersiveEngineeringCM implements IProxy {

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadThaumium());
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadVoid());
        RegistrarCM.addAdditionToRegister(new ItemUpgradeRefining());
    }

    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialTreatedWood());
    }
}
