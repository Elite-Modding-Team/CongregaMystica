package congregamystica.api.block;

import congregamystica.api.item.IItemAddition;
import congregamystica.api.util.EnumSortType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public interface IBlockAddition extends IItemAddition {
    /**
     * Registers a block from within the block class. This must be called to register the block.
     */
    default void registerBlock(IForgeRegistry<Block> registry) {
        if(this instanceof Block) {
            registry.register((Block) this);
        }
    }

    @Override
    default void registerItem(IForgeRegistry<Item> registry) {
        if(this instanceof Block && ((Block) this).getRegistryName() != null) {
            registry.register(new ItemBlock((Block) this).setRegistryName(((Block) this).getRegistryName()));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    default void registerModel(ModelRegistryEvent event) {
        if(this instanceof Block && ((Block) this).getRegistryName() != null) {
            ModelResourceLocation loc = new ModelResourceLocation(((Block) this).getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) this), 0, loc);
        }
    }

    @Override
    default EnumSortType getRegistryOrderType() {
        return EnumSortType.BLOCKS;
    }
}
