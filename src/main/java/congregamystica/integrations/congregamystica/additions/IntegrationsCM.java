package congregamystica.integrations.congregamystica.additions;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.registry.ModItemsCM;
import congregamystica.utils.helpers.PechHelper;
import congregamystica.utils.misc.EnumPechType;
import net.minecraftforge.oredict.OreDictionary;

public class IntegrationsCM implements IAddition, IProxy {
    @Override
    public void init() {
        if(ConfigHandlerCM.clusters.registerPechTrades) {
            for (ItemNativeCluster cluster : ModItemsCM.getNativeClusters()) {
                if (!OreDictionary.getOres(cluster.getAssociatedOre()).isEmpty()) {
                    PechHelper.addPechTrade(EnumPechType.MINER, 1, cluster.getDefaultInstance());
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
