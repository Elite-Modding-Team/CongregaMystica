package congregamystica.integrations.immersiveengineering.items;

import congregamystica.CongregaMystica;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.immersiveengineering.util.DrillStats;

public class ItemDrillHeadThaumium extends AbstractDrillHead {
    public static final DrillStats THAUMIUM_HEAD_STATS = new DrillStats(
            "ingotThaumium",
            ConfigHandlerCM.immersive_engineering.thaumium_drill_head.drillSize,
            1,
            ConfigHandlerCM.immersive_engineering.thaumium_drill_head.drillLevel,
            ConfigHandlerCM.immersive_engineering.thaumium_drill_head.drillSpeed,
            ConfigHandlerCM.immersive_engineering.thaumium_drill_head.drillAttack,
            ConfigHandlerCM.immersive_engineering.thaumium_drill_head.drillDurability,
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
        return ConfigHandlerCM.immersive_engineering.thaumium_drill_head.enabled;
    }

}
