package congregamystica.aspects.mods;

import congregamystica.api.IAspectProvider;
import congregamystica.aspects.AspectCalculator;
import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import hellfirepvp.astralsorcery.common.crafting.altar.AltarRecipeRegistry;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.TraitRecipe;
import hellfirepvp.astralsorcery.common.lib.Constellations;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Tuple;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AspectsAS implements IAspectProvider {
    @Override
    public void getAspects(Map<ItemStack, AspectList> aspects) {

        handleStarlightAltarRecipes(aspects);
    }

    private void handleStarlightAltarRecipes(Map<ItemStack, AspectList> aspects) {
        //Altar recipes use between 20 and 6500 starlight normally with most of them using between 200 and 1000
        AltarRecipeRegistry.recipes.forEach((altarLevel, recipes) -> recipes.forEach(recipe -> {
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutputForMatching(), recipe.getNativeRecipe().getIngredients().toArray(new Ingredient[0]));
            if(!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                int sky = (int) Math.ceil(Math.cbrt(recipe.getPassiveStarlightRequired()));
                tuple.getSecond().add(Aspect.LIGHT, sky).add(Aspect.DARKNESS, sky);
                if(recipe instanceof TraitRecipe) {
                    Aspect star = this.getConstellationAspect(((TraitRecipe) recipe).getRequiredConstellation());
                    if(star != null) {
                        tuple.getSecond().add(star, sky);
                    }
                }
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        }));
        AltarRecipeRegistry.mtRecipes.forEach((altarLevel, recipes) -> recipes.forEach(recipe -> {
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutputForMatching(), recipe.getNativeRecipe().getIngredients().toArray(new Ingredient[0]));
            if(!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                int sky = (int) Math.pow(2, altarLevel.ordinal());
                tuple.getSecond().add(Aspect.LIGHT, sky).add(Aspect.DARKNESS, sky);
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        }));
    }

    @Nullable
    public Aspect getConstellationAspect(@Nullable IConstellation constellation) {
        if(constellation == Constellations.discidia) {
            return Aspect.AVERSION;
		} else if(constellation == Constellations.armara) {
            return Aspect.PROTECT;
		} else if(constellation == Constellations.vicio) {
            return Aspect.MOTION;
		} else if(constellation == Constellations.aevitas) {
            return Aspect.PLANT;
        } else if(constellation == Constellations.evorsio) {
            return Aspect.MAN;
		} else if(constellation == Constellations.mineralis) {
            return Aspect.EARTH;
		} else if(constellation == Constellations.lucerna) {
            return Aspect.VOID;
        } else if(constellation == Constellations.bootes) {
            return Aspect.BEAST;
		} else if(constellation == Constellations.octans) {
            return Aspect.WATER;
		} else if(constellation == Constellations.horologium) {
            return Aspect.ELDRITCH;
        } else if(constellation == Constellations.fornax) {
            return Aspect.FIRE;
		} else if(constellation == Constellations.pelotrio) {
            return Aspect.LIFE;
		} else if(constellation == Constellations.gelu) {
            return Aspect.COLD;
        } else if(constellation == Constellations.ulteria) {
            return Aspect.DESIRE;
		} else if(constellation == Constellations.alcara) {
            return Aspect.FLUX;
		} else if(constellation == Constellations.vorux) {
            return Aspect.ENERGY;
		} else {
            return null;
        }
    }

}
