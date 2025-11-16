package congregamystica.integrations.congregamystica;

import congregamystica.api.IModModule;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.blocks.BlockFenceGateWood;
import congregamystica.integrations.congregamystica.blocks.BlockFenceWood;
import congregamystica.integrations.congregamystica.golems.GolemMaterialSteel;
import congregamystica.integrations.congregamystica.items.*;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.registry.ModItemsCM;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.LogHelper;
import net.minecraft.block.material.MapColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CongregaMysticaCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialSteel());
        RegistrarCM.addAdditionToRegister(new ItemMimicFork());
        RegistrarCM.addAdditionToRegister(new ItemMimicForkRanged());
        RegistrarCM.addAdditionToRegister(new ItemFluxCaster());
        RegistrarCM.addAdditionToRegister(new ItemFluxScribingTools());
        RegistrarCM.addAdditionToRegister(new BlockFenceWood("fence_greatwood", MapColor.WOOD));
        RegistrarCM.addAdditionToRegister(new BlockFenceWood("fence_silverwood", MapColor.WHITE_STAINED_HARDENED_CLAY));
        RegistrarCM.addAdditionToRegister(new BlockFenceGateWood("fence_gate_greatwood", MapColor.WOOD));
        RegistrarCM.addAdditionToRegister(new BlockFenceGateWood("fence_gate_silverwood", MapColor.WHITE_STAINED_HARDENED_CLAY));
        getNativeClustersFromConfig();
        ModItemsCM.getNativeClusters().forEach(RegistrarCM::addAdditionToRegister);
    }

    private void getNativeClustersFromConfig() {
        for(String configString : ConfigHandlerCM.clusters.additionalClusters) {
            try {
                Pattern pattern = Pattern.compile("^(ore\\w+);(\\w+);?(0[xX][0-9a-fA-F]{6})?$");
                Matcher matcher = pattern.matcher(configString);
                if(matcher.find()) {
                    String associatedOre = matcher.group(1);
                    String outputType = matcher.group(2);
                    if(matcher.group(3) != null) {
                        int color = Integer.decode(matcher.group(3));
                        ModItemsCM.addNativeCluster(new ItemNativeClusterDynamic(new ClusterData(associatedOre, outputType, color)));
                    } else {
                        ModItemsCM.addNativeCluster(new ItemNativeCluster(new ClusterData(associatedOre, outputType)));
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
