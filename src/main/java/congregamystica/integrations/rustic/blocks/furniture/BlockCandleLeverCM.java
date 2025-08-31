package congregamystica.integrations.rustic.blocks.furniture;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.blocks.BlockCandleLever;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class BlockCandleLeverCM extends BlockCandleLever implements IBlockAddition {
    public BlockCandleLeverCM(String unlocName) {
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
