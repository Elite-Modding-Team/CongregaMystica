package congregamystica.integrations.rustic.blocks.herbs;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import rustic.common.blocks.crops.BlockHerbBase;

public class BlockHerbBaseCM extends BlockHerbBase implements IBlockAddition {
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.125, 0.7),
            new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.250, 0.7),
            new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.375, 0.7),
            new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.625, 0.7)
    };
    private final EnumPlantType plantType;

    public BlockHerbBaseCM(String name, EnumPlantType plantType) {
        super(name, false);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.plantType = plantType;
    }

    @Override
    public @NotNull AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CROPS_AABB[this.getAge(state)].offset(state.getOffset(source, pos));
    }

    @Override
    public @NotNull EnumPlantType getPlantType(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return this.plantType;
    }

    @Override
    public Item getHerb() {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        //Herb block is registered with the BlockHerbBase constructor
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        //Herb item is registered with the BlockHerbBase constructor
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
