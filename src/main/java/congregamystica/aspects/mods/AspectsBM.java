package congregamystica.aspects.mods;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import congregamystica.api.IAspectProvider;
import congregamystica.aspects.AspectCalculator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Tuple;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AspectsBM implements IAspectProvider {
    @Override
    public void getAspects(Map<ItemStack, AspectList> aspects) {


        handleAltarRecipes(aspects);
        handleForgeRecipes(aspects);
        handleAlchemyArrayRecipes(aspects);
        handleAlchemyRecipes(aspects);
    }

    private void handleAltarRecipes(Map<ItemStack, AspectList> aspects) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAltarRecipes().forEach(recipe -> {
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutput(), recipe.getInput());
            if(!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                int life = Math.max(2, (int) Math.sqrt(recipe.getSyphon() / 100.0));
                int death = Math.max(1, (int) Math.sqrt(life));
                tuple.getSecond().add(Aspect.LIFE, life).add(Aspect.DEATH, death);
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        });
    }

    private void handleAlchemyArrayRecipes(Map<ItemStack, AspectList> aspects) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAlchemyArrayRecipes().forEach(recipe -> {
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutput(), recipe.getCatalyst(), recipe.getInput());
            if(!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                tuple.getSecond().add(Aspect.EXCHANGE, 5);
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        });
    }

    private void handleAlchemyRecipes(Map<ItemStack, AspectList> aspects) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAlchemyRecipes().forEach(recipe -> {
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutput(), recipe.getInput().toArray(new Ingredient[0]));
            if (!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                int life = (int) Math.sqrt(recipe.getSyphon() / 100.0) + 2;
                tuple.getSecond().add(Aspect.ALCHEMY, 5).add(Aspect.LIFE, life);
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        });
    }

    private void handleForgeRecipes(Map<ItemStack, AspectList> aspects) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getTartaricForgeRecipes().forEach(recipe -> {
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutput(), recipe.getInput().toArray(new Ingredient[0]));
            if(!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                int soul = (int) Math.max(1, Math.sqrt(recipe.getSoulDrain()) * 2);
                tuple.getSecond().add(Aspect.SOUL, soul);
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        });
    }
}
