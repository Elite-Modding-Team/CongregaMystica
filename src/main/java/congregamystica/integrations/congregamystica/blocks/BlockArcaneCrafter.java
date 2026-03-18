package congregamystica.integrations.congregamystica.blocks;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.block.IBlockAddition;
import congregamystica.api.util.EnumSortType;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.blocks.render.TileArcaneCrafterTESR;
import congregamystica.integrations.congregamystica.blocks.tiles.TileArcaneCrafter;
import congregamystica.registry.ModBlocksCM;
import congregamystica.registry.ModItemsCM;
import congregamystica.utils.helpers.PlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.ICaster;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BlockArcaneCrafter extends BlockContainer implements IBlockAddition, IProxy {
    public static final ResourceLocation HUD_ARCANE_CRAFTER = new ResourceLocation(CongregaMystica.MOD_ID, "textures/gui/hud_arcane_crafter.png");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final AxisAlignedBB AABB_TOP = new AxisAlignedBB(0, 0.625, 0, 1.0, 1.0, 1.0);
    public static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.625, 0.75);
    public static int[] crystalX = new int[] {48, 3, 94, 3, 94, 48};
    public static int[] crystalY = new int[] {2, 25, 25, 70, 70, 92};

    public BlockArcaneCrafter() {
        super(Material.IRON);
        this.setRegistryName(CongregaMystica.MOD_ID, "arcane_crafter");
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setSoundType(SoundType.METAL);
        this.setHardness(2.0f);
        this.setResistance(10.0f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull AxisAlignedBB entityBox, @NotNull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_TOP);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BOTTOM);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @Nullable RayTraceResult collisionRayTrace(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Vec3d start, @NotNull Vec3d end) {
        if(rayTrace(pos, start, end, AABB_TOP) != null || rayTrace(pos, start, end, AABB_BOTTOM) != null) {
            return super.collisionRayTrace(blockState, worldIn, pos, start, end);
        } else {
            return null;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, @NotNull ItemStack stack) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileArcaneCrafter && placer instanceof EntityPlayer) {
            ((TileArcaneCrafter) tile).setPlayer((EntityPlayer) placer);
        }
    }

    @Override
    public void breakBlock(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileArcaneCrafter) {
            IItemHandler handler = ((TileArcaneCrafter) tile).stackHandler;
            for(int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if(!stack.isEmpty()) {
                    spawnAsEntity(worldIn, pos, stack.copy());
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
        return new TileArcaneCrafter();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(@NotNull IBlockState base_state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, @NotNull EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getMinecraft();
            ItemStack mhStack = mc.player.getHeldItemMainhand();
            ItemStack ohStack = mc.player.getHeldItemOffhand();
            if (mhStack.getItem() instanceof ICaster || ohStack.getItem() instanceof ICaster) {
                RayTraceResult trace = PlayerHelper.rayTrace(mc.player, 0);
                if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                    TileEntity tile = mc.world.getTileEntity(trace.getBlockPos());
                    if (tile instanceof TileArcaneCrafter) {
                        this.renderArcaneCrafterHud(mc, event.getResolution(), (TileArcaneCrafter) tile);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderArcaneCrafterHud(Minecraft mc, ScaledResolution resolution, TileArcaneCrafter crafter) {
        InventoryCrafting craft = crafter.stackHandler.getInventoryCrafting(false);
        int width = 112;
        int height = 110;
        int xc = resolution.getScaledWidth() / 2 + 20;
        int yc = resolution.getScaledHeight() / 2 - height / 2;

        mc.getTextureManager().bindTexture(HUD_ARCANE_CRAFTER);
        GlStateManager.enableBlend();
        mc.ingameGUI.drawTexturedModalRect(xc, yc, 0, 0, width, height);
        GlStateManager.disableBlend();
        mc.getTextureManager().bindTexture(Gui.ICONS);

//        Gui.drawRect(xc - 6, yc - 6, xc + width + 6, yc + height + 6, 0x22000000);
//        Gui.drawRect(xc - 4, yc - 4, xc + width + 4, yc + height + 4, 0x22000000);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                int xp = xc + j * 18 + 30;
                int yp = yc + i * 18 + 29;

//                Gui.drawRect(xp, yp, xp + 16, yp + 16, 0x22FFFFFF);

                ItemStack stack = craft.getStackInSlot(index);
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.enableRescaleNormal();
                mc.getRenderItem().renderItemAndEffectIntoGUI(stack, xp, yp);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, xp, yp, null);
                RenderHelper.disableStandardItemLighting();
            }
        }

        for(int i = 9; i < craft.getSizeInventory(); i++) {
            int meta = i - 9;
            if(meta < 6) {
                ItemStack stack = craft.getStackInSlot(i);
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.enableRescaleNormal();
                mc.getRenderItem().renderItemAndEffectIntoGUI(stack, xc + crystalX[meta], yc + crystalY[meta]);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, xc + crystalX[meta], yc + crystalY[meta], null);
                RenderHelper.disableStandardItemLighting();
            }
        }

        GlStateManager.color(1f, 1f, 1f, 1f);
    }

    //##########################################################
    // IProxy

    @Override
    public void preInitClient() {
        MinecraftForge.EVENT_BUS.register(this);
    }


    //##########################################################
    // IBlockAddition

    //TODO: Possibly render a hover fake arcane workbench to display current items

    @SuppressWarnings("ConstantConditions")
    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //Arcane Crafter
        ThaumcraftApi.addArcaneCraftingRecipe(ModBlocksCM.ARCANE_CRAFTER.getRegistryName(), new ShapedArcaneRecipe(new ResourceLocation(""),
                "CM_ARCANE_CRAFTER",
                50,
                new AspectList().add(Aspect.WATER, 1).add(Aspect.ORDER, 1).add(Aspect.EARTH, 1),
                new ItemStack(this),
                "RH ",
                "MCM",
                " W ",
                'R', ItemsTC.visResonator,
                'H', Blocks.HOPPER,
                'M', ItemsTC.mechanismComplex,
                'C', BlocksTC.arcaneWorkbench,
                'W', BlocksTC.plankGreatwood
        ));
        //Crafting Placeholder
        ThaumcraftApi.addArcaneCraftingRecipe(ModItemsCM.CRAFTER_PLACEHOLDER.getRegistryName(), new ShapedArcaneRecipe(new ResourceLocation(""),
                "CM_ARCANE_CRAFTER",
                5,
                new AspectList().add(Aspect.ORDER, 1),
                new ItemStack(ModItemsCM.CRAFTER_PLACEHOLDER, 9),
                "PPP",
                "PCP",
                "PPP",
                'P', "paper",
                'C', ThaumcraftApiHelper.makeCrystal(Aspect.VOID)
        ));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/congregamystica/arcane_crafter"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.CONGREGA_MYSTICA;
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> registry) {
        IBlockAddition.super.registerBlock(registry);
        GameRegistry.registerTileEntity(TileArcaneCrafter.class, Objects.requireNonNull(this.getRegistryName()));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.congrega_mystica.arcaneCrafter.enable;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        IBlockAddition.super.registerModel(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileArcaneCrafter.class, new TileArcaneCrafterTESR());
    }
}
