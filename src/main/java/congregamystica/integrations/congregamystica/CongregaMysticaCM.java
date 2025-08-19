package congregamystica.integrations.congregamystica;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.item.IItemAddition;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.additions.GolemMaterialSteel;
import congregamystica.integrations.congregamystica.items.ItemMimicFork;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.integrations.congregamystica.items.ItemNativeClusterDynamic;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.LogHelper;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@GameRegistry.ObjectHolder(CongregaMystica.MOD_ID)
public class CongregaMysticaCM implements IProxy {
    public static List<Item> NATIVE_CLUSTERS = new ArrayList<>();
    public static final Item MIMIC_FORK = null;

    @Override
    public void preInit() {
        getClustersFromConfig();
        for(Item item : NATIVE_CLUSTERS) {
            RegistrarCM.addAdditionToRegister((IItemAddition) item);
        }
        RegistrarCM.addAdditionToRegister(new ItemMimicFork());
    }
    
    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialSteel());
    }

    private void getClustersFromConfig() {
        for(String configString : ConfigHandlerCM.clusters.additionalClusters) {
            try {
                Pattern pattern = Pattern.compile("^(ore\\w+);?(0[xX][0-9a-fA-F]{6})?$");
                Matcher matcher = pattern.matcher(configString);
                if(matcher.find()) {
                    String associatedOre = matcher.group(1);
                    if(matcher.groupCount() > 1) {
                        int color = Integer.decode(matcher.group(2));
                        NATIVE_CLUSTERS.add(new ItemNativeClusterDynamic(new ClusterData(associatedOre, color)));
                    } else {
                        NATIVE_CLUSTERS.add(new ItemNativeCluster(new ClusterData(associatedOre)));
                    }
                }
            } catch (Exception e) {
                LogHelper.error("Invalid cluster configuration string: " + configString);
            }
        }
    }
}
