package congregamystica.integrations.rustic.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.RusticCM;
import congregamystica.integrations.rustic.blocks.herbs.BlockHerbBaseCM;
import congregamystica.registry.ModBlocksCM;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;
import rustic.common.crafting.BrewingBarrelRecipe;
import rustic.common.crafting.CrushingTubRecipe;
import rustic.common.crafting.Recipes;
import rustic.common.items.ItemFluidBottle;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;

import java.util.Map;

public class AdditionViscousBrew implements IAddition, IProxy {

    @Override
    public void preInit() {
        this.initBlocks();
        ItemFluidBottle.addFluid(RusticCM.VISCOUS_BREW);
        ItemFluidBottle.addFluid(RusticCM.VISCOUS_WORT);
    }

    private void initBlocks() {
        RegistrarCM.addAdditionToRegister(new BlockHerbBaseCM("viscap", EnumPlantType.Plains));
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        this.registerCrushing();
        this.registerBrewing();
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

    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableViscousBrew;
    }
}
