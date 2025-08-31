package congregamystica.integrations.rustic.blocks.furniture;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.blocks.BlockTable;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class BlockTableCM extends BlockTable implements IBlockAddition {
    public BlockTableCM(String type) {
        super(type);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    //##########################################################
    // IBlockAddition

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        //Do not register blocks or items for chairs or tables
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        //Do not register blocks or items for chairs or tables
    }

    @Override
    public boolean isEnabled() {
        //Because of how Rustic is coded, the isEnabled() will need to be checked prior to the RegistrarCM#addAdditionToRegister()
        return true;
    }
}
