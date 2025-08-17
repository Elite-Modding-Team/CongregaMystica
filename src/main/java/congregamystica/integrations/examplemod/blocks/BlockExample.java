package congregamystica.integrations.examplemod.blocks;

import congregamystica.CongregaMystica;
import congregamystica.api.IBlockAddition;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.Collections;
import java.util.Map;

public class BlockExample extends Block implements IBlockAddition {
    public BlockExample() {
        super(Material.ROCK);
        this.setRegistryName(CongregaMystica.MOD_ID, "example_block");
        this.setTranslationKey(this.getRegistryName().toString());
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        //Register block here
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        //Register item here
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //Register any recipes associated with the block here
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        //Register block and item model here
    }

    @Override
    public void registerResearchLocation() {
        //Register any associated research here
    }

    @Override
    public Map<ItemStack, AspectList> registerAspects() {
        //Register any aspects here. ItemStacks are handled automatically so long as they are added to the return map.
        return Collections.singletonMap(new ItemStack(this), new AspectList().add(Aspect.EARTH, 5));
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
