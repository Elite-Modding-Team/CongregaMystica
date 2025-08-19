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
    public static List<ItemEldritchCluster> ELDRITCH_CLUSTERS = new ArrayList<>();

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new IntegrationsTW());
        CongregaMysticaCM.NATIVE_CLUSTERS.forEach(item -> {
            if(item instanceof ItemNativeClusterDynamic) {
                ELDRITCH_CLUSTERS.add(new ItemEldritchClusterDynamic(item.getClusterData()));
            } else {
                ELDRITCH_CLUSTERS.add(new ItemEldritchCluster(item.getClusterData()));
            }
        });
        ELDRITCH_CLUSTERS.forEach(RegistrarCM::addAdditionToRegister);
    }
}
