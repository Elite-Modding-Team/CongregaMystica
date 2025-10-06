package congregamystica.integrations.congregamystica.blocks;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import congregamystica.api.util.EnumSortType;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFenceWood extends BlockFence implements IBlockAddition {
    public BlockFenceWood(String unlocName, MapColor mapColor) {
        super(Material.WOOD, mapColor);
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("axe", 0);
    }
	
    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return Blocks.PLANKS.getFlammability(world, pos, face);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return Blocks.PLANKS.getFireSpreadSpeed(world, pos, face);
    }

    //##########################################################
    // IItemAddition

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.CONGREGA_MYSTICA;
    }

    @Override
    public boolean isEnabled() {
        //Fences are always enabled
        return true;
    }
}
