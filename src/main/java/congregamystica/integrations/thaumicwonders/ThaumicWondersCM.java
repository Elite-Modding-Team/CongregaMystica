package congregamystica.integrations.thaumicwonders;

import congregamystica.api.IModModule;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.items.ItemNativeClusterDynamic;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.integrations.thaumicwonders.additions.IntegrationsTW;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchCluster;
import congregamystica.integrations.thaumicwonders.items.ItemEldritchClusterDynamic;
import congregamystica.registry.ModItemsCM;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.LogHelper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThaumicWondersCM implements IModModule {
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

        getAdditionalEldritchClusters();
        ModItemsCM.getEldritchClusters().forEach(RegistrarCM::addAdditionToRegister);
    }

    private void getAdditionalEldritchClusters() {
        Map<ClusterData, Boolean> eldritchClusters = new LinkedHashMap<>();
        for(String configString : ConfigHandlerCM.thaumic_wonders.additionalClusters) {
            try {
                Pattern pattern = Pattern.compile("^(ore\\w+);(\\w+);?(0[xX][0-9a-fA-F]{6})?$");
                Matcher matcher = pattern.matcher(configString);
                if(matcher.find()) {
                    String associatedOre = matcher.group(1);
                    String outputType = matcher.group(2);
                    if(matcher.group(3) != null) {
                        int color = Integer.decode(matcher.group(3));
                        eldritchClusters.put(new ClusterData(associatedOre, outputType, color), true);
                    } else {
                        eldritchClusters.put(new ClusterData(associatedOre, outputType), false);
                    }

                } else {
                    LogHelper.error("Invalid eldritch cluster configuration string: " + configString);
                }
            } catch (Exception e) {
                LogHelper.error("Invalid eldritch cluster configuration string: " + configString);
            }
        }
        eldritchClusters.keySet().removeIf(eldritchData -> ModItemsCM.getNativeClusters().stream().anyMatch(nativeData ->
                eldritchData.associatedOre.equals(nativeData.getAssociatedOre())));

        eldritchClusters.forEach((data,dynamic) -> ModItemsCM.addEldritchCluster(dynamic ? new ItemEldritchClusterDynamic(data) : new ItemEldritchCluster(data)));
    }
}
