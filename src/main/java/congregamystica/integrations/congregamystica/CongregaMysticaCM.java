package congregamystica.integrations.congregamystica;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.integrations.congregamystica.additions.GolemMaterialSteel;
import congregamystica.registry.RegistrarCM;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class CongregaMysticaCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialSteel());
    }
}
