package congregamystica.integrations.rustic.blocks.fluids;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import congregamystica.api.util.EnumSortType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.blocks.fluids.BlockFluidRustic;

public class BlockFluidRusticCM extends BlockFluidRustic implements IBlockAddition {
    public BlockFluidRusticCM(String name, Fluid fluid, int quantaPerBlock) {
        super(name, fluid, Material.WATER);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setQuantaPerBlock(quantaPerBlock);
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {

    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        Item item = Item.getItemFromBlock(this);
        FluidStateMapperCM stateMapper = new FluidStateMapperCM(this.stack.getFluid());
        ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomMeshDefinition(item, stateMapper);
        ModelLoader.setCustomStateMapper(this, stateMapper);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.FLUIDS;
    }
}
