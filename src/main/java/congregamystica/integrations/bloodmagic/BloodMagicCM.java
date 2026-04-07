package congregamystica.integrations.bloodmagic;

import congregamystica.CongregaMystica;
import congregamystica.api.IModModule;
import congregamystica.integrations.bloodmagic.additions.BloodOrbCM;
import congregamystica.integrations.bloodmagic.items.ItemBloodScribingTools;
import congregamystica.integrations.bloodmagic.items.ItemBoundCaster;
import congregamystica.integrations.bloodmagic.items.ItemEtherealRapier;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.ThaumcraftApi;

public class BloodMagicCM implements IModModule {
    public static Item.ToolMaterial BOUND_VIS = EnumHelper.addToolMaterial("BOUND_VIS", 4, 1, 10.0f, 6.5f, 50);

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemBoundCaster());
        RegistrarCM.addAdditionToRegister(new BloodOrbCM());
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
        RegistrarCM.addAdditionToRegister(new ItemEtherealRapier());
    }

    @Override
    public void registerResearchNode() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/bloodmagic/blood_magic"));
    }
}
