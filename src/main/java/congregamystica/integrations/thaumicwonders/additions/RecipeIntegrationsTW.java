package congregamystica.integrations.thaumicwonders.additions;

import com.verdantartifice.thaumicwonders.common.crafting.catalyzationchamber.CatalyzationChamberRecipeRegistry;
import congregamystica.api.IAddition;
import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeIntegrationsTW implements IAddition {
    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        CongregaMysticaCM.NATIVE_CLUSTERS.stream()
                .filter(cluster -> !OreDictionary.getOres(cluster.getClusterData().getAssociatedOre()).isEmpty())
                .forEach(cluster -> CatalyzationChamberRecipeRegistry.addAlchemistRecipe(
                        new OreIngredient(cluster.getClusterData().getAssociatedOre()),
                        new ItemStack(cluster)));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
