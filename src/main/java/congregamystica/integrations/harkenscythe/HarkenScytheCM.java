package congregamystica.integrations.harkenscythe;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.integrations.harkenscythe.additions.GolemMaterialBiomass;
import congregamystica.integrations.harkenscythe.additions.GolemMaterialLivingmetal;
import congregamystica.registry.RegistrarCM;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class HarkenScytheCM implements IProxy {
    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialBiomass());
        RegistrarCM.addAdditionToRegister(new GolemMaterialLivingmetal());
    }
}
