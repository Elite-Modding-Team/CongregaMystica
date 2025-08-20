package congregamystica.integrations.congregamystica;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.additions.GolemMaterialSteel;
import congregamystica.integrations.congregamystica.additions.IntegrationsCM;
import congregamystica.integrations.congregamystica.items.ItemMimicFork;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.integrations.congregamystica.items.ItemNativeClusterDynamic;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.LogHelper;
import congregamystica.utils.helpers.PechHelper;
import congregamystica.utils.misc.EnumPechType;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
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
        RegistrarCM.addAdditionToRegister(new IntegrationsCM());
        RegistrarCM.addAdditionToRegister(new ItemMimicFork());
        getClustersFromConfig();
        NATIVE_CLUSTERS.forEach(RegistrarCM::addAdditionToRegister);
    }
    
    @Override
    public void init() {
        RegistrarCM.addAdditionToRegister(new GolemMaterialSteel());

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
