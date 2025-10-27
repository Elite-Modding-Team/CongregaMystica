package congregamystica.registry;

import congregamystica.CongregaMystica;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchCluster;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class ModItemsCM {
    //Congrega Mystica
    public static final Item FLUX_SCRIBING_TOOLS = null;
    public static final Item GOGGLED_THAUMIUM_HELMET = null;
    public static final Item MIMIC_FORK = null;
    public static final Item MIMIC_FORK_RANGED = null;

    //Blood Magic
    public static final Item BLOOD_SCRIBING_TOOLS = null;
    public static final Item CASTER_BOUND = null;

    //Botania
    public static final Item CASTER_ELEMENTIUM = null;

    //Immersive Engineering
    public static final Item DRILL_HEAD_THAUMIUM = null;
    public static final Item DRILL_HEAD_VOID = null;
    public static final Item UPGRADE_REFINING = null;

    //Rustic


    private static final List<ItemNativeCluster> NATIVE_CLUSTERS = new ArrayList<>();
    private static final List<ItemEldritchCluster> ELDRITCH_CLUSTERS = new ArrayList<>();

    public static List<ItemNativeCluster> getNativeClusters() {
        return NATIVE_CLUSTERS;
    }

    public static void addNativeCluster(ItemNativeCluster cluster) {
        NATIVE_CLUSTERS.add(cluster);
    }

    public static List<ItemEldritchCluster> getEldritchClusters() {
        return ELDRITCH_CLUSTERS;
    }

    public static void addEldritchCluster(ItemEldritchCluster cluster) {
        ELDRITCH_CLUSTERS.add(cluster);
    }
}
