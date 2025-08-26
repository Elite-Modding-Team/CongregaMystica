package congregamystica.integrations.thaumicwonders.items;

import com.verdantartifice.thaumicwonders.common.crafting.catalyzationchamber.CatalyzationChamberRecipeRegistry;
import congregamystica.api.util.EnumSortType;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.utils.helpers.AspectHelperCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;

import java.util.Map;

public class ItemEldritchCluster extends ItemNativeCluster {
    public static boolean hasResearchLoaded = false;

    public ItemEldritchCluster(ClusterData clusterData) {
        super(clusterData.eldritchId, clusterData);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull String getItemStackDisplayName(@NotNull ItemStack stack) {
        String key = (this.getUnlocalizedNameInefficiently(stack) + ".name");
        if(I18n.canTranslate(key)) {
            return I18n.translateToLocal(key).trim();
        } else {
            return this.clusterData.eldritchDisplayName;
        }
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ItemStack inputOre = OreDictionary.getOres(this.getAssociatedOre()).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        ItemStack outputIngot = OreDictionary.getOres(this.clusterData.associatedIngot).stream().findFirst().orElse(ItemStack.EMPTY).copy();
        ItemStack outputNugget = OreDictionary.getOres(this.clusterData.associatedNugget).stream().findFirst().orElse(ItemStack.EMPTY).copy();

        if(!inputOre.isEmpty()) {
            //Ore conversion
            CatalyzationChamberRecipeRegistry.addAlienistRecipe(new OreIngredient(this.getAssociatedOre()), new ItemStack(this));

            if (!outputIngot.isEmpty()) {
                //Cluster smelting
                outputIngot.setCount(3);
                GameRegistry.addSmelting(this.getDefaultInstance(), outputIngot, 1.0f);

                //Infernal Smelting Bonus
                if (!outputNugget.isEmpty()) {
                    ThaumcraftApi.addSmeltingBonus(this.clusterData.eldritchOreDict, outputNugget);
                }
                ThaumcraftApi.addSmeltingBonus(this.clusterData.eldritchOreDict, new ItemStack(ItemsTC.nuggets, 1, 10), 0.025f);

            }
        }
    }

    //TODO: Fix cluster research loading for every cluster
    @Override
    public void registerResearchLocation() {
        //TODO: Add research
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(this.getDefaultInstance(), AspectHelperCM.getEldritchClusterAspects(this.clusterData));
    }

    @Override
    public void registerOreDicts() {
        OreDictionary.registerOre(this.clusterData.eldritchOreDict, this);
    }

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.ELDRITCH_CLUSTER;
    }
}
