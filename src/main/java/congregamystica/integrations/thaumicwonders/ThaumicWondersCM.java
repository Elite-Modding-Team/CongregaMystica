package congregamystica.integrations.thaumicwonders;

import congregamystica.api.IProxy;
import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import congregamystica.integrations.congregamystica.items.ItemNativeClusterDynamic;
import congregamystica.integrations.thaumicwonders.additions.IntegrationsTW;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchCluster;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchClusterDynamic;
import congregamystica.registry.RegistrarCM;

import java.util.ArrayList;
import java.util.List;

public class ThaumicWondersCM implements IProxy {
    private static final List<ItemEldritchCluster> ELDRITCH_CLUSTERS = new ArrayList<>();

    public static List<ItemEldritchCluster> getEldritchClusters() {
        return ELDRITCH_CLUSTERS;
    }

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new IntegrationsTW());
        CongregaMysticaCM.getNativeClusters().forEach(item -> {
            if(item instanceof ItemNativeClusterDynamic) {
                ELDRITCH_CLUSTERS.add(new ItemEldritchClusterDynamic(item.getClusterData()));
            } else {
                ELDRITCH_CLUSTERS.add(new ItemEldritchCluster(item.getClusterData()));
            }
        });
        ELDRITCH_CLUSTERS.forEach(RegistrarCM::addAdditionToRegister);
    }
}
