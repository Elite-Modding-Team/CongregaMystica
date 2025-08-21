package congregamystica.integrations.thaumicwonders.additions;

import com.verdantartifice.thaumicwonders.common.crafting.catalyzationchamber.CatalyzationChamberRecipeRegistry;
import congregamystica.api.IAddition;
import congregamystica.registry.ModItemsCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

public class IntegrationsTW implements IAddition {
    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ModItemsCM.getNativeClusters().stream()
                .filter(cluster -> !OreDictionary.getOres(cluster.getAssociatedOre()).isEmpty())
                .forEach(cluster -> CatalyzationChamberRecipeRegistry.addAlchemistRecipe(
                        new OreIngredient(cluster.getAssociatedOre()),
                        new ItemStack(cluster)));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
