package congregamystica.api.item;

import congregamystica.api.IAddition;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public interface IItemAddition extends IAddition {
    /**
     * Registers an item from within the item or block class. This must be called to register the item.
     */
    void registerItem(IForgeRegistry<Item> registry);

    @Override
    void registerModel(ModelRegistryEvent event);
}
