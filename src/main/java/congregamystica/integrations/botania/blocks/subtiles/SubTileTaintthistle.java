package congregamystica.integrations.botania.blocks.subtiles;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.botania.BotaniaCM;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.world.taint.ITaintBlock;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockArc;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.crafting.ModPetalRecipes;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SubTileTaintthistle extends SubTileFunctional implements IAddition, IProxy {
    private static final int MANA_COST = ConfigHandlerCM.botania.taintthistle.manaCost;
    private static final int RANGE = 8;
    public static LexiconEntry TAINTTHISTLE_ENTRY;
    public static RecipePetals TAINTTHISTLE_RECIPE;

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        boolean did = false;
        if(!this.supertile.getWorld().isRemote && this.redstoneSignal <= 0 && this.mana >= MANA_COST && this.ticksExisted % 100 == 0) {
            int limit = ConfigHandlerCM.botania.taintthistle.taintLimit;
            List<BlockPos> taintPositions = this.getShuffledTaintedBlockList(limit);
            for (BlockPos pos : taintPositions) {
                PacketHandler.INSTANCE.sendToAllAround(
                        new PacketFXBlockArc(this.getPos(), pos, this.getColor() << 16, this.getColor() << 8, this.getColor() << 0),
                        new NetworkRegistry.TargetPoint(this.getWorld().provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32));
                this.supertile.getWorld().setBlockToAir(pos);
                this.mana -= MANA_COST;
                limit--;
                did = true;
                if (this.mana < MANA_COST || limit <= 0) {
                    break;
                }
            }
        }
        if(did) {
            this.supertile.getWorld().playSound(null, this.getPos(), SoundsTC.zap, SoundCategory.BLOCKS, 1.0f, 1.0f);
            this.sync();
        }
    }

    private List<BlockPos> getShuffledTaintedBlockList(int limit) {
        List<BlockPos> positions = new ArrayList<>();
        AxisAlignedBB area = new AxisAlignedBB(this.getPos()).grow(RANGE);
        for (int x = (int) area.minX; x <= (int) area.maxX; x++) {
            for (int y = (int) area.minY; y <= (int) area.maxY; y++) {
                for (int z = (int) area.minZ; z <= (int) area.maxZ; z++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    IBlockState state = this.supertile.getWorld().getBlockState(checkPos);
                    if (this.isTaintedBlock(state)) {
                        positions.add(checkPos);
                    }
                }
            }
        }
        if(positions.size() > limit) {
            Collections.shuffle(positions);
        }
        return positions;
    }

    private boolean isTaintedBlock(IBlockState state) {
        return state.getBlock() == BlocksTC.fluxGoo || state.getBlock() instanceof ITaintBlock;
    }

    @Override
    public int getMaxMana() {
        return MANA_COST * ConfigHandlerCM.botania.taintthistle.taintLimit;
    }

    @Override
    public int getColor() {
        return 0x4D00FF;
    }

    @Override
    public LexiconEntry getEntry() {
        return TAINTTHISTLE_ENTRY;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(this.toBlockPos(), RANGE);
    }

    //##########################################################
    // IAddition

    @Override
    public void preInit() {
        BotaniaAPI.addSubTileToCreativeMenu(BotaniaCM.TAINTTHISTLE);
        BotaniaAPI.registerSubTile(BotaniaCM.TAINTTHISTLE, SubTileTaintthistle.class);
    }

    @Override
    public void postInit() {
        SubTileTaintthistle.TAINTTHISTLE_ENTRY = new BasicLexiconEntry(BotaniaCM.TAINTTHISTLE, BotaniaAPI.categoryFunctionalFlowers);
        SubTileTaintthistle.TAINTTHISTLE_ENTRY.setIcon(ItemBlockSpecialFlower.ofType(BotaniaCM.TAINTTHISTLE));
        SubTileTaintthistle.TAINTTHISTLE_ENTRY.setLexiconPages(
                new PageText("0"),
                new PagePetalRecipe<>("1", TAINTTHISTLE_RECIPE)
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        BotaniaAPIClient.registerSubtileModel(SubTileTaintthistle.class, new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, BotaniaCM.TAINTTHISTLE), "normal"));
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        TAINTTHISTLE_RECIPE = new RecipePetals(ItemBlockSpecialFlower.ofType(BotaniaCM.TAINTTHISTLE),
                ModPetalRecipes.purple,
                ModPetalRecipes.purple,
                ModPetalRecipes.magenta,
                ModPetalRecipes.magenta,
                new ItemStack(ModItems.rune, 1, 10),
                new ItemStack(ModItems.rune, 1, 12),
                "redstoneRoot"
        );
        BotaniaAPI.petalRecipes.add(TAINTTHISTLE_RECIPE);
    }

    @Override
    public void registerResearchLocation() {
        //TODO: Add research
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        AspectList flowerAspects = new AspectList()
                .add(Aspect.PLANT, 15)
                .add(Aspect.SENSES, 15)
                .add(Aspect.VOID, 5)
                .add(Aspect.ORDER, 5)
                .add(Aspect.MAGIC, 5);
        aspectMap.put(ItemBlockSpecialFlower.ofType(BotaniaCM.TAINTTHISTLE), flowerAspects);
        AspectList floatingAspects =  new AspectList().add(flowerAspects).add(Aspect.FLIGHT, 5).add(Aspect.LIGHT, 5);
        aspectMap.put(ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), BotaniaCM.TAINTTHISTLE), floatingAspects);
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.botania.taintthistle.enable;
    }
}
