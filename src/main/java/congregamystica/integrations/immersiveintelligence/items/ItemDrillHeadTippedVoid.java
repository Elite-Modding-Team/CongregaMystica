package congregamystica.integrations.immersiveintelligence.items;

import congregamystica.CongregaMystica;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.immersiveengineering.items.ItemDrillHeadVoid;
import congregamystica.integrations.immersiveengineering.util.DrillStats;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;

public class ItemDrillHeadTippedVoid extends ItemDrillHeadVoid {
    public static final DrillStats TIPPED_VOID_HEAD_STATS = new DrillStats(
            "ingotVoid",
            ConfigHandlerCM.immersive_intelligence.tippedVoidHead.drillSize,
            1,
            ConfigHandlerCM.immersive_intelligence.tippedVoidHead.drillLevel,
            ConfigHandlerCM.immersive_intelligence.tippedVoidHead.drillSpeed,
            ConfigHandlerCM.immersive_intelligence.tippedVoidHead.drillAttack,
            ConfigHandlerCM.immersive_intelligence.tippedVoidHead.drillDurability,
            CongregaMystica.MOD_ID + ":models/immersiveintelligence/drill_tipped_void");

    public ItemDrillHeadTippedVoid() {
        super("drill_head_tipped_void", TIPPED_VOID_HEAD_STATS);
    }

    @Override
    public void preInit() {
        //ItemDrillHeadVoid already registers the required EventSubscriber
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/immersiveintelligence/drill_head_tipped_void"));
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled() && ConfigHandlerCM.immersive_intelligence.tippedVoidHead.enable;
    }
}
