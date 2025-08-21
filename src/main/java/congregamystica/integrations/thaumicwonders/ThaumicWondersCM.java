package congregamystica.integrations.thaumicwonders;

import congregamystica.api.IProxy;
import congregamystica.integrations.congregamystica.items.ItemNativeClusterDynamic;
import congregamystica.integrations.thaumicwonders.additions.IntegrationsTW;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchCluster;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchClusterDynamic;
import congregamystica.registry.ModItemsCM;
import congregamystica.registry.RegistrarCM;

public class ThaumicWondersCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new IntegrationsTW());
        ModItemsCM.getNativeClusters().forEach(item -> {
            if(item instanceof ItemNativeClusterDynamic) {
                ModItemsCM.addEldritchCluster(new ItemEldritchClusterDynamic(item.getClusterData()));
            } else {
                ModItemsCM.addEldritchCluster(new ItemEldritchCluster(item.getClusterData()));
            }
        });
        ModItemsCM.getEldritchClusters().forEach(RegistrarCM::addAdditionToRegister);
    }
}
