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
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(CongregaMystica.MOD_ID, "cindermote"), new InfusionRecipe(
                "CM_CINDERFIRE_WHISKEY",
                new ItemStack(ModBlocksCM.CINDERMOTE),
                3,
                new AspectList().add(Aspect.PLANT, 50).add(Aspect.LIFE, 50).add(Aspect.FIRE, 25),
                Ingredient.fromStacks(new ItemStack(BlocksTC.cinderpearl)),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)),
                Ingredient.fromItem(ItemsTC.salisMundus),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)),
                Ingredient.fromItem(Items.WHEAT_SEEDS)
        ));
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
        aspectMap.put(new ItemStack(ModBlocksCM.CINDERMOTE), new AspectList().add(Aspect.PLANT, 1).add(Aspect.FIRE, 1).add(Aspect.AURA, 1));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableCinderfireWhiskey;
    }
}
