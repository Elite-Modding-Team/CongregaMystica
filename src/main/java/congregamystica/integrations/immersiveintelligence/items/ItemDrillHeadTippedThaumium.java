package congregamystica.integrations.immersiveintelligence.items;

import congregamystica.CongregaMystica;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadThaumium;
import congregamystica.integrations.immersiveengineering.util.DrillStats;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;

public class ItemDrillHeadTippedThaumium extends ItemDrillHeadThaumium {
    public static final DrillStats TIPPED_THAUMIUM_HEAD_STATS = new DrillStats(
            "ingotThaumium",
            ConfigHandlerCM.immersive_intelligence.tippedThaumiumHead.drillSize,
            1,
            ConfigHandlerCM.immersive_intelligence.tippedThaumiumHead.drillLevel,
            ConfigHandlerCM.immersive_intelligence.tippedThaumiumHead.drillSpeed,
            ConfigHandlerCM.immersive_intelligence.tippedThaumiumHead.drillAttack,
            ConfigHandlerCM.immersive_intelligence.tippedThaumiumHead.drillDurability,
            CongregaMystica.MOD_ID + ":models/immersiveintelligence/drill_tipped_thaumium"
    );

    public ItemDrillHeadTippedThaumium() {
        super("drill_head_tipped_thaumium", TIPPED_THAUMIUM_HEAD_STATS);
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/immersiveintelligence/drill_head_tipped_thaumium"));
    }

    @Override
    public boolean isEnabled() {
        //Requires Thaumium Drill Head to be enabled
        return super.isEnabled() && ConfigHandlerCM.immersive_intelligence.tippedThaumiumHead.enable;
    }
}
