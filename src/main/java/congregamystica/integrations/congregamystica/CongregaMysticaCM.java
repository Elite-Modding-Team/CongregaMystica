package congregamystica.integrations.congregamystica;

import congregamystica.api.IModModule;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.additions.IntegrationsCM;
import congregamystica.integrations.congregamystica.golems.GolemMaterialSteel;
import congregamystica.integrations.congregamystica.items.ItemMimicFork;
import congregamystica.integrations.congregamystica.items.ItemMimicForkRanged;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.integrations.congregamystica.items.ItemNativeClusterDynamic;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.registry.ModItemsCM;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.LogHelper;
import congregamystica.utils.helpers.PechHelper;
import congregamystica.utils.misc.EnumPechType;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CongregaMysticaCM implements IModModule {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialSteel());
        RegistrarCM.addAdditionToRegister(new IntegrationsCM());
        RegistrarCM.addAdditionToRegister(new ItemMimicFork());
        RegistrarCM.addAdditionToRegister(new ItemMimicForkRanged());
        getClustersFromConfig();
        ModItemsCM.getNativeClusters().forEach(RegistrarCM::addAdditionToRegister);
    }
    
    @Override
    public void init() {
        //Pech trade fixes
        PechHelper.removePechTrade(EnumPechType.MAGE, Ingredient.fromStacks(new ItemStack(Items.POTIONITEM, 1, 8193)));
        PechHelper.removePechTrade(EnumPechType.MAGE, Ingredient.fromStacks(new ItemStack(Items.POTIONITEM, 1, 8261)));
        PechHelper.addPechTrade(EnumPechType.MAGE, 2, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.REGENERATION));
        PechHelper.addPechTrade(EnumPechType.MAGE, 2, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING));
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
