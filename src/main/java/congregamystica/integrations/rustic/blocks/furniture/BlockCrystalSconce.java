package congregamystica.integrations.rustic.blocks.furniture;

import congregamystica.api.IProxy;
import congregamystica.integrations.rustic.tiles.TileCrystalSconce;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

import java.util.Random;

public class BlockCrystalSconce extends BlockCandleCM implements ITileEntityProvider, IProxy {
    protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4, 0.0F, 0.4, 0.6, 0.8125, 0.6);
    protected static final AxisAlignedBB CANDLE_NORTH_AABB = new AxisAlignedBB(0.35, 0.0F, 0.7, 0.65, 0.75, 1.0F);
    protected static final AxisAlignedBB CANDLE_SOUTH_AABB = new AxisAlignedBB(0.35, 0.0F, 0.0F, 0.65, 0.75, 0.3);
    protected static final AxisAlignedBB CANDLE_WEST_AABB = new AxisAlignedBB(0.7, 0.0F, 0.35, 1.0F, 0.75, 0.65);
    protected static final AxisAlignedBB CANDLE_EAST_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.35, 0.3, 0.75, 0.65);

    public BlockCrystalSconce(String unlocName) {
        super(unlocName);
    }

    @Override
    public @NotNull AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case EAST:
                return CANDLE_EAST_AABB;
            case WEST:
                return CANDLE_WEST_AABB;
            case SOUTH:
                return CANDLE_SOUTH_AABB;
            case NORTH:
                return CANDLE_NORTH_AABB;
            default:
                return STANDING_AABB;
        }
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = playerIn.getHeldItem(hand);
        if(heldStack.getItem() instanceof IEssentiaContainerItem) {
            AspectList aspects = ((IEssentiaContainerItem) heldStack.getItem()).getAspects(heldStack);
            if(aspects != null && aspects.getAspects().length > 0 && aspects.getAspects()[0] != null) {
                TileEntity tile = worldIn.getTileEntity(pos);
                if(tile instanceof TileCrystalSconce) {
                    ((TileCrystalSconce) tile).setAspect(aspects.getAspects()[0]);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        worldIn.removeTileEntity(pos);
    }

    @Override
    public void getDrops(@NotNull NonNullList<ItemStack> drops, IBlockAccess world, @NotNull BlockPos pos, @NotNull IBlockState state, int fortune) {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileCrystalSconce) {
            NBTTagCompound tileTag = new NBTTagCompound();
            tileTag.setTag("BlockEntityTag", tile.writeToNBT(new NBTTagCompound()));
            stack.setTagCompound(tileTag);
        }
        drops.add(stack);
    }

    @Override
    public @NotNull ItemStack getPickBlock(@NotNull IBlockState state, @NotNull RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        ItemStack stack = super.getPickBlock(state, target, world, pos, player);
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileCrystalSconce) {
            NBTTagCompound tileTag = new NBTTagCompound();
            tileTag.setTag("BlockEntityTag", tile.writeToNBT(new NBTTagCompound()));
            stack.setTagCompound(tileTag);
        }
        return stack;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileCrystalSconce();
    }

    @Override
    public void harvestBlock(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable TileEntity te, @NotNull ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean eventReceived(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }



    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

    }

    //##########################################################
    // IBlockAddition

    @SideOnly(Side.CLIENT)
    @Override
    public void initClient() {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        IBlockColor color = (state, worldIn, pos, tintIndex) -> {
            if(worldIn != null && pos != null) {
                TileEntity tile = worldIn.getTileEntity(pos);
                if(tile instanceof TileCrystalSconce) {
                    Aspect aspect = ((TileCrystalSconce) tile).getAspect();
                    return tintIndex > 0 && aspect != null ? aspect.getColor() : Aspect.LIGHT.getColor();
                }
            }
            return -1;
        };
        blockColors.registerBlockColorHandler(color, this);
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        super.registerBlock(registry);
        GameRegistry.registerTileEntity(TileCrystalSconce.class, this.getRegistryName());
    }
}
