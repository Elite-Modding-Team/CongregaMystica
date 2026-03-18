package congregamystica.integrations.congregamystica.blocks;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.block.IBlockAddition;
import congregamystica.api.util.EnumSortType;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.blocks.tiles.TileArcanePatternProvider;
import congregamystica.registry.ModGuiHandlerCM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;
import java.util.Objects;

public class BlockArcanePatternProvider extends BlockContainer implements IBlockAddition, IProxy {
    protected BlockArcanePatternProvider() {
        super(Material.IRON);
        this.setRegistryName(CongregaMystica.MOD_ID, "arcane_pattern_provider");
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setSoundType(SoundType.METAL);
        this.setHardness(2.0f);
        this.setResistance(10.0f);
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileArcanePatternProvider) {
            playerIn.openGui(CongregaMystica.instance, ModGuiHandlerCM.ID_ARCANE_PATTERN_PROVIDER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (!worldIn.isRemote && tile instanceof TileArcanePatternProvider) {
            IItemHandler handler = ((TileArcanePatternProvider) tile).inventoryHandler;
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.extractItem(i, handler.getSlotLimit(i), true);
                if(!stack.isEmpty()) {
                    spawnAsEntity(worldIn, pos, stack);
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileArcanePatternProvider();
    }

    //##########################################################
    // IProxy

    //##########################################################
    // IBlockAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {

    }

    @Override
    public void registerResearchLocation() {

    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        IBlockAddition.super.registerBlock(registry);
        GameRegistry.registerTileEntity(TileArcanePatternProvider.class, Objects.requireNonNull(this.getRegistryName()));
    }

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.CONGREGA_MYSTICA;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.congrega_mystica.arcaneCrafter.enable;
    }
}
