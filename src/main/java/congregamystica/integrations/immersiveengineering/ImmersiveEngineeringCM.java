package congregamystica.integrations.immersiveengineering;

import congregamystica.CongregaMystica;
import congregamystica.api.IModModule;
import congregamystica.integrations.immersiveengineering.golems.GolemMaterialTreatedWood;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadThaumium;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadVoid;
import congregamystica.integrations.immersiveengineering.items.ItemUpgradeRefining;
import congregamystica.registry.RegistrarCM;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;

public class ImmersiveEngineeringCM implements IModModule {

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialTreatedWood());
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadThaumium());
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadVoid());
        RegistrarCM.addAdditionToRegister(new ItemUpgradeRefining());
    }

    @Override
    public void registerResearchNode() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/immersiveengineering/immersive_engineering"));
    }
}
