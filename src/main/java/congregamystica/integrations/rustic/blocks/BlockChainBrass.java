package congregamystica.integrations.rustic.blocks;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.blocks.BlockChain;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class BlockChainBrass extends BlockChain implements IBlockAddition {
    public BlockChainBrass() {
        super(Material.IRON, "chain_brass", false);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    //##########################################################
    // IBlockAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {

    }

    @Override
    public void registerResearchLocation() {

    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
