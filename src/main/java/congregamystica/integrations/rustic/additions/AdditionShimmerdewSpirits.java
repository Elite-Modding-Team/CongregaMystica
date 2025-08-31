package congregamystica.integrations.rustic.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.RusticCM;
import congregamystica.integrations.rustic.blocks.herbs.BlockHerbBaseCM;
import congregamystica.registry.ModBlocksCM;
import congregamystica.registry.RegistrarCM;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
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

public class AdditionShimmerdewSpirits implements IAddition, IProxy {

    @Override
    public void preInit() {
        this.initBlocks();
        ItemFluidBottle.addFluid(RusticCM.SHIMMERDEW_SPIRITS);
        ItemFluidBottle.addFluid(RusticCM.SHIMMERDEW_WORT);
    }

    private void initBlocks() {
        RegistrarCM.addAdditionToRegister(new BlockHerbBaseCM("shimmerdew_bulb", EnumPlantType.Plains));
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        this.registerCrushing();
        this.registerBrewing();
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(CongregaMystica.MOD_ID, "shimmerdew_bulb"), new InfusionRecipe(
                "CM_SHIMMERDEW_SPIRITS",
                new ItemStack(ModBlocksCM.SHIMMERDEW_BULB),
                3,
                new AspectList().add(Aspect.PLANT, 50).add(Aspect.LIFE, 50).add(Aspect.AURA, 25),
                Ingredient.fromStacks(new ItemStack(BlocksTC.shimmerleaf)),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)),
                Ingredient.fromItem(ItemsTC.salisMundus),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)),
                Ingredient.fromItem(Items.WHEAT_SEEDS)
        ));
    }

    private void registerCrushing() {
        Recipes.add(new CrushingTubRecipe(new FluidStack(RusticCM.SHIMMERDEW_WORT, 250), new ItemStack(BlocksTC.shimmerleaf)));
        Recipes.add(new CrushingTubRecipe(new FluidStack(RusticCM.SHIMMERDEW_WORT, 125), new ItemStack(ModBlocksCM.SHIMMERDEW_BULB)));
    }

    private void registerBrewing() {
        Recipes.add(new BrewingBarrelRecipe(new FluidStack(RusticCM.SHIMMERDEW_SPIRITS, 1), new FluidStack(RusticCM.SHIMMERDEW_WORT, 1)));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/rustic/shimmerdew_spirits"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(new ItemStack(ModBlocksCM.SHIMMERDEW_BULB), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1).add(Aspect.ENERGY, 1));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableShimmerdewSpirits;
    }
}
