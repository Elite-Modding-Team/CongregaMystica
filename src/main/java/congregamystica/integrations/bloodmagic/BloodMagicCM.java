package congregamystica.integrations.bloodmagic;

import WayofTime.bloodmagic.orb.BloodOrb;
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
    private static final BloodOrb ORB_DEF = new BloodOrb("", 0, 0, 0);
    @GameRegistry.ObjectHolder("eldritch")
    public static final BloodOrb ORB_ELDRITCH = ORB_DEF;
    public static final Item BLOOD_SCRIBING_TOOLS = null;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemBloodScribingTools());
        RegistrarCM.addAdditionToRegister(new BloodOrbCM());
    }
}
