package congregamystica.integrations.botania.blocks.subtiles;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.botania.BotaniaCM;
import congregamystica.integrations.botania.lexicon.LexiconEntryCM;
import congregamystica.network.PacketHandlerCM;
import congregamystica.network.packets.PacketCrystalGrowth;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.world.ore.BlockCrystal;
import thaumcraft.common.lib.SoundsTC;
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
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.Map;

public class SubTilePrimalBloom extends SubTileFunctional implements IAddition, IProxy {
    private static final int MANA_COST = ConfigHandlerCM.botania.primalFlowers.primalBloomManaCost;
    private static final int RANGE = 4;
    public static LexiconEntry PRIMAL_BLOOM_ENTRY;
    public static RecipePetals PRIMAL_BLOOM_RECIPE;

    @Override
    public boolean acceptsRedstone() {
        return super.acceptsRedstone();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.getWorld().isRemote) {
            if (this.ticksExisted % 6 == 0 && this.redstoneSignal == 0) {
                int range = this.getRange();
                int x = this.getPos().getX() + this.getWorld().rand.nextInt(range * 2 + 1) - range;
                int z = this.getPos().getZ() + this.getWorld().rand.nextInt(range * 2 + 1) - range;

                for (int i = 4; i > -2; --i) {
                    int y = this.getPos().getY() + i;
                    BlockPos pos = new BlockPos(x, y, z);
                    if(!this.getWorld().isAirBlock(pos) && this.isCrystal(pos) && this.mana > MANA_COST) {
                        if(this.growCrystal(pos)) {
                            this.mana -= MANA_COST;
                            this.sync();
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean isCrystal(BlockPos pos) {
        IBlockState state = this.getWorld().getBlockState(pos);
        return state.getBlock() instanceof BlockCrystal && ((BlockCrystal) state.getBlock()).aspect != Aspect.FLUX;
    }

    private boolean growCrystal(BlockPos pos) {
        IBlockState state = this.getWorld().getBlockState(pos);
        if(state.getBlock() instanceof BlockCrystal) {
            BlockCrystal crystal = (BlockCrystal) state.getBlock();
            int growth = crystal.getGrowth(state);
            int generation = crystal.getGeneration(state);
            if(growth < 3 && (long) growth < (long) (5 - generation) + pos.toLong() % 3L) {
                this.getWorld().setBlockState(pos, state.withProperty(BlockCrystal.SIZE, growth + 1));
                this.doGrowthEffects(crystal, pos);
                return true;
            } else if(generation < 4) {
                BlockPos spreadPos = BlockCrystal.spreadCrystal(this.getWorld(), pos);
                if(spreadPos != null) {
                    if (this.getWorld().rand.nextInt(6) == 0) {
                        --generation;
                    }
                    this.getWorld().setBlockState(spreadPos, state.getBlock().getDefaultState().withProperty(BlockCrystal.GENERATION, generation + 1));
                    this.doGrowthEffects(crystal, spreadPos);
                    return true;
                }
            }
        }
        return false;
    }

    private void doGrowthEffects(BlockCrystal crystal, BlockPos pos) {
        this.getWorld().playSound(null, pos, SoundsTC.crystal, SoundCategory.AMBIENT, 0.3f, 1.0f);
        PacketHandlerCM.INSTANCE.sendToAllAround(
                new PacketCrystalGrowth(crystal.aspect, pos),
                new NetworkRegistry.TargetPoint(
                        this.getWorld().provider.getDimension(),
                        pos.getX() + 0.5,
                        pos.getY() + 0.2,
                        pos.getZ() + 0.5,
                        32)
        );
    }

    @Override
    public int getMaxMana() {
        return Math.max(300, MANA_COST * 4);
    }

    public int getRange() {
        return RANGE;
    }

    @Override
    public int getColor() {
        return 0x85aea6;
    }

    @Override
    public LexiconEntry getEntry() {
        return PRIMAL_BLOOM_ENTRY;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Circle(this.toBlockPos(), this.getRange());
    }

    //##########################################################
    // IAddition

    @Override
    public void preInit() {
        BotaniaAPI.addSubTileToCreativeMenu(BotaniaCM.PRIMAL_BLOOM);
        BotaniaAPI.registerSubTile(BotaniaCM.PRIMAL_BLOOM, SubTilePrimalBloom.class);
    }

    @Override
    public void postInit() {
        PRIMAL_BLOOM_ENTRY = new LexiconEntryCM(BotaniaCM.PRIMAL_BLOOM, BotaniaAPI.categoryFunctionalFlowers);
        PRIMAL_BLOOM_ENTRY.setIcon(ItemBlockSpecialFlower.ofType(BotaniaCM.PRIMAL_BLOOM));
        PRIMAL_BLOOM_ENTRY.setLexiconPages(
                new PageText("0"),
                new PagePetalRecipe<>("1", PRIMAL_BLOOM_RECIPE)
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        BotaniaAPIClient.registerSubtileModel(SubTilePrimalBloom.class, new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, BotaniaCM.PRIMAL_BLOOM), "normal"));
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        PRIMAL_BLOOM_RECIPE = new RecipePetals(ItemBlockSpecialFlower.ofType(BotaniaCM.PRIMAL_BLOOM),
                ModPetalRecipes.lightBlue,
                ModPetalRecipes.red,
                ModPetalRecipes.green,
                ModPetalRecipes.brown,
                ModPetalRecipes.black,
                ModPetalRecipes.white,
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ModItems.rune, 1, 8),
                "redstoneRoot"
        );
        BotaniaAPI.petalRecipes.add(PRIMAL_BLOOM_RECIPE);
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/botania/primal_flowers"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        AspectList flowerAspects = new AspectList()
                .add(Aspect.PLANT, 15)
                .add(Aspect.ORDER, 10)
                .add(Aspect.ENTROPY, 10)
                .add(Aspect.MAGIC, 5);
        aspectMap.put(ItemBlockSpecialFlower.ofType(BotaniaCM.PRIMAL_BLOOM), flowerAspects);
        AspectList floatingAspects =  new AspectList().add(flowerAspects).add(Aspect.FLIGHT, 5).add(Aspect.LIGHT, 5);
        aspectMap.put(ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), BotaniaCM.PRIMAL_BLOOM), floatingAspects);
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.botania.primalFlowers.enable;
    }
}
