package congregamystica.integrations.congregamystica.items;

import congregamystica.api.item.AbstractItemAddition;
import congregamystica.api.util.EnumSortType;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.utils.helpers.AspectHelperCM;
import congregamystica.utils.helpers.PechHelper;
import congregamystica.utils.helpers.PechHelper.EnumPechType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.utils.Utils;

import java.util.Map;

public class ItemNativeCluster extends AbstractItemAddition {
    protected ClusterData clusterData;

    public ItemNativeCluster(String unlocName, ClusterData clusterData) {
        super(unlocName);
        this.clusterData = clusterData;
    }

    public ItemNativeCluster(ClusterData clusterData) {
        this(clusterData.clusterId, clusterData);
    }

    public ClusterData getClusterData() {
        return this.clusterData;
    }

    public String getAssociatedOre() {
        return this.clusterData.associatedOre;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull String getItemStackDisplayName(@NotNull ItemStack stack) {
        String key = (this.getUnlocalizedNameInefficiently(stack) + ".name");
        if(I18n.canTranslate(key)) {
            return I18n.translateToLocal(key).trim();
        } else {
            return this.clusterData.nativeDisplayName;
        }
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ItemStack inputOre = OreDictionary.getOres(this.getAssociatedOre()).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        ItemStack outputIngot = OreDictionary.getOres(this.clusterData.associatedIngot).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        ItemStack outputNugget = OreDictionary.getOres(this.clusterData.associatedNugget).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        boolean isGemOutput = this.clusterData.associatedIngot.startsWith("gem");

        if(!inputOre.isEmpty()) {
            //Ore conversion
            if(!isGemOutput || ConfigHandlerCM.clusters.registerGemCrucibleRefining) {
                ThaumcraftApi.addCrucibleRecipe(this.getRegistryName(), new CrucibleRecipe(
                        "METALPURIFICATION",
                        new ItemStack(this),
                        new OreIngredient(this.getAssociatedOre()),
                        new AspectList().add(Aspect.METAL, 5).add(Aspect.ORDER, 5)
                ));
            }

            if(ConfigHandlerCM.clusters.registerPechTrades) {
                PechHelper.addPechTrade(EnumPechType.MINER, 1, new ItemStack(this));
            }

            if (!outputIngot.isEmpty()) {
                //Cluster smelting
                outputIngot.setCount(2);
                GameRegistry.addSmelting(new ItemStack(this), outputIngot, 1.0f);

                //Mining Bonus - This may need to be moved to IProxy postInit()
                if(isGemOutput && ConfigHandlerCM.clusters.specialGemHarvest) {
                    for(ItemStack stack : OreDictionary.getOres(this.clusterData.associatedIngot)) {
                        Utils.addSpecialMiningResult(stack, new ItemStack(this), 1.0f);
                    }
                } else {
                    for (ItemStack stack : OreDictionary.getOres(this.getAssociatedOre())) {
                        Utils.addSpecialMiningResult(stack, new ItemStack(this), 1.0f);
                    }
                }

                //Infernal Smelting Bonus
                if (!outputNugget.isEmpty()) {
                    if (ConfigHandlerCM.clusters.registerSmeltingBonuses) {
                        ThaumcraftApi.addSmeltingBonus(this.getAssociatedOre(), outputNugget);
                    }
                    ThaumcraftApi.addSmeltingBonus(this.clusterData.clusterOreDict, outputNugget);
                }
                ThaumcraftApi.addSmeltingBonus(this.clusterData.clusterOreDict, new ItemStack(ItemsTC.nuggets, 1, 10), 0.02f);
            }
        }
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(new ItemStack(this), AspectHelperCM.getNativeClusterAspects(this.clusterData));
    }

    @Override
    public void registerOreDicts() {
        OreDictionary.registerOre(this.clusterData.clusterOreDict, this);
    }

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.NATIVE_CLUSTER;
    }

    @Override
    public boolean isEnabled() {
        //Clusters are enabled always because the items are generated via config string
        return true;
    }
}
