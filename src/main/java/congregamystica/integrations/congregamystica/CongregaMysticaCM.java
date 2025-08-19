package congregamystica.integrations.congregamystica;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
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
    public static List<ItemNativeCluster> NATIVE_CLUSTERS = new ArrayList<>();
    public static final Item MIMIC_FORK = null;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new ItemMimicFork());
        getClustersFromConfig();
        NATIVE_CLUSTERS.forEach(RegistrarCM::addAdditionToRegister);
    }
    
    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialSteel());
    }

    private void getClustersFromConfig() {
        for(String configString : ConfigHandlerCM.clusters.additionalClusters) {
            try {
                Pattern pattern = Pattern.compile("^(ore\\w+);(\\w+);?(0[xX][0-9a-fA-F]{6})?$");
                Matcher matcher = pattern.matcher(configString);
                if(matcher.find()) {
                    String associatedOre = matcher.group(1);
                    String outputType = matcher.group(2);
                    if(matcher.group(3) != null) {
                        int color = Integer.decode(matcher.group(3));
                        NATIVE_CLUSTERS.add(new ItemNativeClusterDynamic(new ClusterData(associatedOre, outputType, color)));
                    } else {
                        NATIVE_CLUSTERS.add(new ItemNativeCluster(new ClusterData(associatedOre, outputType)));
                    }
                } else {
                    LogHelper.error("Invalid cluster configuration string: " + configString);
                }
            } catch (Exception e) {
                LogHelper.error("Invalid cluster configuration string: " + configString);
            }
        }
    }
}
