package congregamystica.integrations.bloodmagic;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.integrations.bloodmagic.additions.BloodOrbCM;
import congregamystica.integrations.bloodmagic.items.ItemBloodScribingTools;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("unused")
@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class BloodMagicCM implements IProxy {
    public static final Item BLOOD_SCRIBING_TOOLS = null;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
        RegistrarCM.addAdditionToRegister(new BloodOrbCM());
    }
}
