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

public class AdditionCinderfireWhsikey implements IAddition, IProxy {

    @Override
    public void preInit() {
        this.initBlocks();
        ItemFluidBottle.addFluid(RusticCM.CINDERFIRE_WHISKEY);
        ItemFluidBottle.addFluid(RusticCM.CINDERFIRE_WORT);
    }

    private void initBlocks() {
        RegistrarCM.addAdditionToRegister(new BlockHerbBaseCM("cindermote", EnumPlantType.Desert));
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        this.registerCrushing();
        this.registerBrewing();
    }

    private void registerCrushing() {
        Recipes.add(new CrushingTubRecipe(new FluidStack(RusticCM.CINDERFIRE_WORT, 250), new ItemStack(BlocksTC.cinderpearl)));
        Recipes.add(new CrushingTubRecipe(new FluidStack(RusticCM.CINDERFIRE_WORT, 125), new ItemStack(ModBlocksCM.CINDERMOTE)));
    }

    private void registerBrewing() {
        Recipes.add(new BrewingBarrelRecipe(new FluidStack(RusticCM.CINDERFIRE_WHISKEY, 1), new FluidStack(RusticCM.CINDERFIRE_WORT, 1)));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/rustic/cinderfire_whiskey"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableCinderfireWhiskey;
    }
}
