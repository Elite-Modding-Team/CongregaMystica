package congregamystica.integrations.immersiveengineering.items;

import congregamystica.CongregaMystica;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.immersiveengineering.util.DrillStats;

public class ItemDrillHeadThaumium extends AbstractDrillHead {
    public static final DrillStats THAUMIUM_HEAD_STATS = new DrillStats(
            "ingotThaumium",
            ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.drillSize,
            1,
            ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.drillLevel,
            ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.drillSpeed,
            ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.drillAttack,
            ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.drillDurability,
            CongregaMystica.MOD_ID + ":models/immersiveengineering/drill_thaumium");

    public ItemDrillHeadThaumium() {
        super("drill_head_thaumium", THAUMIUM_HEAD_STATS);
    }

    @Override
    public void registerResearchLocation() {
        //TODO
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.enable;
    }

}
