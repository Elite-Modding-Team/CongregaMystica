package congregamystica.integrations.bloodmagic;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.integrations.bloodmagic.additions.BloodOrbCM;
import congregamystica.integrations.bloodmagic.items.ItemBloodScribingTools;
import congregamystica.integrations.bloodmagic.items.ItemBoundCaster;
import congregamystica.registry.RegistrarCM;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;

public class BloodMagicCM implements IProxy {
    @Override
    public void preInit() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/bloodmagic/blood_magic"));

        RegistrarCM.addAdditionToRegister(new ItemBoundCaster());
        RegistrarCM.addAdditionToRegister(new BloodOrbCM());
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
    }
}
