package congregamystica.integrations.congregamystica.items;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.item.IItemAddition;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.util.ClusterData;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.utils.Utils;

import java.util.Map;

public class ItemNativeCluster extends Item implements IItemAddition, IProxy {
    protected ClusterData clusterData;

    public ItemNativeCluster(ClusterData clusterData) {
        this.setRegistryName(CongregaMystica.MOD_ID, clusterData.clusterId);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.clusterData = clusterData;
    }

    public ClusterData getClusterData() {
        return this.clusterData;
    }

    public String getAssociatedOre() {
        return this.clusterData.associatedOre;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
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
    public void init() {
        OreDictionary.registerOre(this.clusterData.clusterOreDict, this);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ItemStack inputOre = OreDictionary.getOres(this.getAssociatedOre()).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        ItemStack outputIngot = OreDictionary.getOres(this.clusterData.associatedIngot).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        ItemStack outputNugget = OreDictionary.getOres(this.clusterData.associatedNugget).stream().findFirst().orElse(ItemStack.EMPTY).copy();

        if(!inputOre.isEmpty()) {
            //Ore conversion
            ThaumcraftApi.addCrucibleRecipe(this.getRegistryName(), new CrucibleRecipe(
                    "METALPURIFICATION",
                    new ItemStack(this),
                    new OreIngredient(this.getAssociatedOre()),
                    new AspectList().add(Aspect.METAL, 5).add(Aspect.ORDER, 5)
            ));


            if (!outputIngot.isEmpty()) {
                //Cluster smelting
                outputIngot.setCount(2);
                GameRegistry.addSmelting(this.getDefaultInstance(), outputIngot, 1.0f);

                //Mining Bonus - This may need to be moved to IProxy postInit()
                if(this.clusterData.associatedIngot.startsWith("gem") && ConfigHandlerCM.clusters.specialGemHarvest) {
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
    public void registerModel(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(this.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, loc);
    }

    @Override
    public void registerResearchLocation() {
        //TODO
    }

    @Override
    public void registerAspects(Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        //Clusters are enabled always because the items are generated via config string
        return true;
    }
}
