package congregamystica.integrations.examplemod.items;

import congregamystica.CongregaMystica;
import congregamystica.api.IItemAddition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class ItemExample extends Item implements IItemAddition {
    public ItemExample() {
        this.setRegistryName(CongregaMystica.MOD_ID, "example_item");
        this.setTranslationKey(this.getRegistryName().toString());
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        //Register item here
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //Register any recipes associated with the item here
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        //Register item model here
    }

    @Override
    public void registerResearchLocation() {
        //Register any associated research here
    }

    @Override
    public Map<ItemStack, AspectList> registerAspects() {
        return IItemAddition.super.registerAspects();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
