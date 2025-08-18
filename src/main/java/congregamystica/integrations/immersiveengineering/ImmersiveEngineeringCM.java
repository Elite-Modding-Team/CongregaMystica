package congregamystica.integrations.immersiveengineering;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.integrations.immersiveengineering.additions.GolemMaterialTreatedWood;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadThaumium;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadVoid;
import congregamystica.integrations.immersiveengineering.util.DrillStats;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class ImmersiveEngineeringCM implements IProxy {
    public static final Item DRILL_HEAD_THAUMIUM = null;
    public static final Item DRILL_HEAD_VOID = null;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadThaumium());
        RegistrarCM.addAdditionToRegister(new ItemDrillHeadVoid());
    }

    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialTreatedWood());
    }
}
