package congregamystica.api.item;

import congregamystica.CongregaMystica;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * A base class for adding items. Automatically handles item registration.
 */
public abstract class AbstractItemAddition extends Item implements IItemAddition {
    @SuppressWarnings("ConstantConditions")
    public AbstractItemAddition(String unlocName) {
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void registerModel(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(this.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, loc);
    }
}
