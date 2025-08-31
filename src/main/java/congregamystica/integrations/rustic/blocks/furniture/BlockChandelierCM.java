package congregamystica.integrations.rustic.blocks.furniture;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.blocks.BlockChandelier;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class BlockChandelierCM extends BlockChandelier implements IBlockAddition {
    public BlockChandelierCM(String unlocName) {
        super(Material.IRON, unlocName, false);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    //##########################################################
    // IBlockAddition

    @Override
    public boolean isEnabled() {
        return true;
    }
}
