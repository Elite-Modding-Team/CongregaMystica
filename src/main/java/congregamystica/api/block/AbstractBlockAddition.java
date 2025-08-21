package congregamystica.api.block;

import congregamystica.CongregaMystica;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * A base class for adding blocks. Automatically handles block and item registration.
 */
public abstract class AbstractBlockAddition extends Block implements IBlockAddition {
    @SuppressWarnings("ConstantConditions")
    public AbstractBlockAddition(String unlocName, Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    public AbstractBlockAddition(String unlocName, Material materialIn) {
        this(unlocName, materialIn, materialIn.getMaterialMapColor());
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        registry.register(this);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(Item.getItemFromBlock(this));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void registerModel(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(this.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, loc);
    }
}
