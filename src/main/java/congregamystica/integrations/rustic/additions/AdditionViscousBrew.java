package congregamystica.integrations.rustic.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.RusticCM;
import congregamystica.integrations.rustic.blocks.herbs.BlockHerbBaseCM;
import congregamystica.registry.ModBlocksCM;
import congregamystica.registry.RegistrarCM;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.crafting.BrewingBarrelRecipe;
import rustic.common.crafting.CrushingTubRecipe;
import rustic.common.crafting.Recipes;
import rustic.common.items.ItemFluidBottle;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;

import java.util.Map;

public class AdditionViscousBrew implements IAddition, IProxy {

    @Override
    public void preInit() {
        this.initBlocks();
        ItemFluidBottle.addFluid(RusticCM.VISCOUS_BREW);
        ItemFluidBottle.addFluid(RusticCM.VISCOUS_WORT);
    }

    private void initBlocks() {
        RegistrarCM.addAdditionToRegister(new BlockHerbBaseCM("viscap", EnumPlantType.Crop) {
            @Override
            public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
                if (!worldIn.isRemote && entityIn instanceof EntityLivingBase && worldIn.rand.nextInt(5) == 0) {
                    ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
                }
            }
        });
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        this.registerCrushing();
        this.registerBrewing();
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(CongregaMystica.MOD_ID, "viscap"), new InfusionRecipe(
                "CM_VISCOUS_BREW",
                new ItemStack(ModBlocksCM.VISCAP),
                3,
                new AspectList().add(Aspect.PLANT, 50).add(Aspect.LIFE, 50).add(Aspect.MAGIC, 25),
                Ingredient.fromStacks(new ItemStack(BlocksTC.vishroom)),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)),
                Ingredient.fromItem(ItemsTC.salisMundus),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)),
                Ingredient.fromItem(Items.WHEAT_SEEDS)
        ));
    }

    private void registerCrushing() {
        Recipes.add(new CrushingTubRecipe(new FluidStack(RusticCM.VISCOUS_WORT, 250), new ItemStack(BlocksTC.vishroom)));
        Recipes.add(new CrushingTubRecipe(new FluidStack(RusticCM.VISCOUS_WORT, 125), new ItemStack(ModBlocksCM.VISCAP)));
    }

    private void registerBrewing() {
        Recipes.add(new BrewingBarrelRecipe(new FluidStack(RusticCM.VISCOUS_BREW, 1), new FluidStack(RusticCM.VISCOUS_WORT, 1)));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/rustic/viscous_brew"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(new ItemStack(ModBlocksCM.VISCAP), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1).add(Aspect.MAGIC, 1));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableViscousBrew;
    }
}
