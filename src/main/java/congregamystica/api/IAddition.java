package congregamystica.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public interface IAddition {
    /**
     * Registers a block from within the block class. This must be called to register the block.
     */
    default void registerBlock(IForgeRegistry<Block> registry) {}
    /**
     * Registers an item from within the item or block class. This must be called to register the item.
     */
    default void registerItem(IForgeRegistry<Item> registry) {}

    /**
     * Registers a recipe during the IRecipe registry event. Always try to use this over proxy recipe
     * registries to avoid incompatibilities.
     */
    default void registerRecipe(IForgeRegistry<IRecipe> registry) {}

    /**
     * Registers the model for the item or block. This method must be called to register the model.
     */
    default void registerModel(ModelRegistryEvent event) {}

    /**
     * Returns true if this item is enabled. Used for additions with configurable Enable/Disable or if they
     * are reliant on another feature.
     */
    boolean isEnabled();
}
