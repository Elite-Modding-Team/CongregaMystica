package congregamystica.aspects.mods;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import congregamystica.api.IAspectProvider;
import congregamystica.utils.helpers.AspectHelperCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AspectsBM implements IAspectProvider {
    @Override
    public void getAspects(Map<ItemStack, AspectList> aspects) {

    }

    private void handleAltarRecipes(AspectEventProxy registry) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAltarRecipes().forEach(recipe -> {
            int life = Math.max(10, (int) Math.sqrt(recipe.getSyphon() / 100.0));
            int death = Math.max(1, (int) Math.sqrt(life));
            AspectList inputAspects = new AspectList()
                    .add(AspectHelperCM.getIngredientAspects(recipe.getInput()))
                    .add(Aspect.LIFE, life)
                    .add(Aspect.DEATH, death);
            registry.registerObjectTag(recipe.getOutput(), inputAspects);
        });
    }

    private void handleAlchemyArrayRecipes(AspectEventProxy registry) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAlchemyArrayRecipes().forEach(recipe -> {
            AspectList inputAspects = new AspectList()
                    .add(AspectHelperCM.getIngredientAspects(recipe.getInput()))
                    .add(AspectHelperCM.getIngredientAspects(recipe.getCatalyst()));
            registry.registerObjectTag(recipe.getOutput(), AspectHelper.cullTags(inputAspects).add(Aspect.EXCHANGE, 10));
        });
    }

    private void handleAlchemyRecipes(AspectEventProxy registry) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAlchemyRecipes().stream()
                .filter(recipe -> AspectHelperCM.getStackAspects(recipe.getOutput()).aspects.isEmpty())
                .forEach(recipe -> {
                    AspectList aspectList = new AspectList();
                    int life = Math.max(1, (int) Math.sqrt(recipe.getSyphon() / 10.0));
                    for(Ingredient ingredient : recipe.getInput()) {
                        ItemStack stack = AspectHelperCM.getIngredientStack(ingredient);
                        AspectList ingredientAspects = new AspectList().add(AspectHelperCM.getStackAspects(stack));
                        if(stack.getItem().hasContainerItem(stack)) {
                            ingredientAspects.remove(AspectHelperCM.getStackAspects(stack.getItem().getContainerItem(stack)));
                        }
                        aspectList.add(ingredientAspects);
                    }
                    int count = recipe.getOutput().getCount();
                    if(count > 1) {
                        aspectList.aspects.replaceAll((aspect, amount) -> aspectList.getAmount(aspect) / count);
                    }
                    registry.registerObjectTag(recipe.getOutput(), AspectHelper.cullTags(aspectList).add(Aspect.LIFE, life).add(Aspect.EXCHANGE, 10).add(Aspect.ALCHEMY, 10));
                });
    }

    private void handleForgeRecipes(AspectEventProxy registry) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().getTartaricForgeRecipes().forEach(recipe -> {
            AspectList aspectList = new AspectList();
            for(Ingredient ingredient : recipe.getInput()) {
                ItemStack stack = AspectHelperCM.getIngredientStack(ingredient);
                AspectList ingredientAspects = new AspectList().add(AspectHelperCM.getStackAspects(stack));
                if(stack.getItem().hasContainerItem(stack)) {
                    ingredientAspects.remove(AspectHelperCM.getStackAspects(stack.getItem().getContainerItem(stack)));
                }
                aspectList.add(ingredientAspects);
            }
            int soulEssence = (int) Math.max(1, Math.sqrt(recipe.getSoulDrain()) * 2);
            int count = recipe.getOutput().getCount();
            if(count > 1) {
                aspectList.aspects.replaceAll((aspect, amount) -> aspectList.getAmount(aspect) / count);
            }
            registry.registerObjectTag(recipe.getOutput(), AspectHelper.cullTags(aspectList).add(Aspect.SOUL, soulEssence));
        });
    }
}
