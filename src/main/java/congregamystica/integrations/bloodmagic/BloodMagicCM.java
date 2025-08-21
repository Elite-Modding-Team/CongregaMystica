package congregamystica.integrations.bloodmagic;

import congregamystica.api.IProxy;
import congregamystica.integrations.bloodmagic.items.ItemBloodOrbCM;
import congregamystica.integrations.bloodmagic.items.ItemBloodScribingTools;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.Item;

public class BloodMagicCM implements IProxy {
    public static final Item BLOOD_SCRIBING_TOOLS = null;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
        RegistrarCM.addAdditionToRegister(new ItemBloodOrbCM());
    }
}
