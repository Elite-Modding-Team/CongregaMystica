package congregamystica.integrations.congregamystica.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.IItemAddition;
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

public class ItemNativeCluster extends Item implements IItemAddition {
    protected ClusterData clusterData;

    public ItemNativeCluster(ClusterData clusterData) {
        this.setRegistryName(CongregaMystica.MOD_ID, clusterData.getClusterId());
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.clusterData = clusterData;
        OreDictionary.registerOre(this.clusterData.getClusterOreDict(), this);
    }

    public ClusterData getClusterData() {
        return this.clusterData;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String key = (this.getUnlocalizedNameInefficiently(stack) + ".name");
        if(I18n.canTranslate(key)) {
            return I18n.translateToLocal(key).trim();
        } else {
            return this.clusterData.getDisplayName();
        }
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ItemStack inputOre = OreDictionary.getOres(this.clusterData.getAssociatedOre()).stream().findFirst().orElse(ItemStack.EMPTY);
        ItemStack outputIngot = OreDictionary.getOres(this.clusterData.getAssociatedIngot()).stream().findFirst().orElse(ItemStack.EMPTY);
        ItemStack outputNugget = OreDictionary.getOres(this.clusterData.getAssociatedNugget()).stream().findFirst().orElse(ItemStack.EMPTY);

        if(!inputOre.isEmpty() && !outputIngot.isEmpty()) {
            //Cluster smelting
            outputIngot.setCount(2);
            GameRegistry.addSmelting(this, outputIngot, 1.0f);

            //Infernal Smelting Bonus
            if(!outputNugget.isEmpty()) {
                ThaumcraftApi.addSmeltingBonus(this.clusterData.getAssociatedOre(), outputNugget);
                ThaumcraftApi.addSmeltingBonus(this.clusterData.getClusterOreDict(), outputNugget);
            }
            ThaumcraftApi.addSmeltingBonus(this.clusterData.getClusterOreDict(), new ItemStack(ItemsTC.nuggets, 1, 10), 0.02f);

            //Ore conversion
            ThaumcraftApi.addCrucibleRecipe(this.getRegistryName(), new CrucibleRecipe(
                    "METALPURIFICATION",
                    new ItemStack(this),
                    new OreIngredient(this.clusterData.getAssociatedOre()),
                    new AspectList().add(Aspect.METAL, 5).add(Aspect.ORDER, 5)
            ));

            //Mining Bonus - This may need to be moved to IProxy postInit()
            for(ItemStack stack : OreDictionary.getOres(this.clusterData.getAssociatedOre())) {
                Utils.addSpecialMiningResult(stack, new ItemStack(this), 1.0f);
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
