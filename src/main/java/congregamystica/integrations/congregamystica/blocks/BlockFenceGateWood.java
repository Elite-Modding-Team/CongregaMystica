package congregamystica.integrations.congregamystica.blocks;

import congregamystica.CongregaMystica;
import congregamystica.api.block.IBlockAddition;
import congregamystica.api.util.EnumSortType;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

public class BlockFenceGateWood extends BlockFenceGate implements IBlockAddition {
    private final MapColor mapColor;

    public BlockFenceGateWood(String unlocName, MapColor mapColor) {
        super(EnumType.OAK);
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("axe", 0);
        this.mapColor = mapColor;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return Blocks.PLANKS.getFlammability(world, pos, face);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return Blocks.PLANKS.getFireSpreadSpeed(world, pos, face);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return mapColor;
    }

    //##########################################################
    // IItemAddition

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.CONGREGA_MYSTICA;
    }

    @Override
    public boolean isEnabled() {
        //Fence gates are always enabled
        return true;
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        IBlockAddition.super.registerModel(event);
        ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).ignore(BlockFenceGateWood.POWERED).build());
    }
}
