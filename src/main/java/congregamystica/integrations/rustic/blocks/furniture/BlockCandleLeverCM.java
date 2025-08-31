package congregamystica.integrations.rustic.blocks.furniture;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import net.minecraft.block.material.Material;
import rustic.common.blocks.BlockCandleLever;

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
