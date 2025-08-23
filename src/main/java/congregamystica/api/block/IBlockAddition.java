package congregamystica.api.block;

import congregamystica.api.item.IItemAddition;
import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public interface IBlockAddition extends IItemAddition {
    /**
     * Registers a block from within the block class. This must be called to register the block.
     */
    void registerBlock(IForgeRegistry<Block> registry);
}
