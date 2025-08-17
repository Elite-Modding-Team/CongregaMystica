package congregamystica.integrations.immersiveengineering;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.integrations.immersiveengineering.additions.GolemMaterialTreatedWood;
import congregamystica.registry.RegistrarCM;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class ImmersiveEngineeringCM implements IProxy {
    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialTreatedWood());
    }
}
