package congregamystica.integrations.rustic.blocks.furniture;

import congregamystica.api.block.IBlockAddition;
import net.minecraft.block.material.Material;
import rustic.common.blocks.BlockLattice;

public class BlockLatticeCM extends BlockLattice implements IBlockAddition {
    public BlockLatticeCM(String unlocName) {
        super(Material.IRON, unlocName, false);
    }

    //##########################################################
    // IBlockAddition

    @Override
    public boolean isEnabled() {
        return true;
    }
}
