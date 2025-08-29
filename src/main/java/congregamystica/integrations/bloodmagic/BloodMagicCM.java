package congregamystica.integrations.bloodmagic;

import congregamystica.CongregaMystica;
import congregamystica.api.IModModule;
import congregamystica.integrations.bloodmagic.additions.BloodOrbCM;
import congregamystica.integrations.bloodmagic.items.ItemBloodScribingTools;
import congregamystica.integrations.bloodmagic.items.ItemBoundCaster;
import congregamystica.registry.RegistrarCM;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;

public class BloodMagicCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemBoundCaster());
        RegistrarCM.addAdditionToRegister(new BloodOrbCM());
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
    }

    @Override
    public void registerResearchNode() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/bloodmagic/blood_magic"));
    }
}
