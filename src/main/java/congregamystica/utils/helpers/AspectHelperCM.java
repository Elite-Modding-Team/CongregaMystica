package congregamystica.utils.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;

public class AspectHelperCM {
    public static void registerNonOverridingOreDictTags(AspectEventProxy registry, String oreDict, AspectList aspectList) {
        for(ItemStack stack : OreDictionary.getOres(oreDict)) {
            AspectList stackAspects = getStackAspects(stack);
            if(stackAspects.aspects.isEmpty()) {
                registry.registerObjectTag(stack.copy(), aspectList);
            }
        }
    }

    public static void registerNonOverridingOreDictTypeTags(AspectEventProxy registry, String regexMatch, AspectList aspectList) {
        for(String oreDict : OreDictionary.getOreNames()) {
            if(oreDict.matches(regexMatch)) {
                registerNonOverridingOreDictTags(registry, oreDict, aspectList);
            }
        }
    }

    @NotNull
    public static AspectList getStackAspects(ItemStack stack) {
        if(stack.isEmpty()) return new AspectList();
        AspectList stackAspects = AspectHelper.getObjectAspects(stack);
        return stackAspects != null ? stackAspects : new AspectList();
    }

    @NotNull
    public static AspectList getIngredientAspects(Ingredient ingredient) {
        for(ItemStack stack : ingredient.getMatchingStacks()) {
            AspectList aspectList = getStackAspects(stack);
            if(!aspectList.aspects.isEmpty()) {
                return aspectList;
            }
        }
        return new AspectList();
    }

    @NotNull
    public static AspectList getDefaultingOreDictAspects(String oreDict) {
        AspectList aspectList = new AspectList();
        for(ItemStack stack : OreDictionary.getOres(oreDict)) {
            AspectList oreAspects = getStackAspects(stack);
            if(!oreAspects.aspects.isEmpty()) {
                aspectList.add(oreAspects);
                break;
            }
        }
        return aspectList;
    }

    public static ItemStack getIngredientStack(Ingredient ingredient) {
        for(ItemStack stack : ingredient.getMatchingStacks()) {
            AspectList aspectList = getStackAspects(stack);
            if(!aspectList.aspects.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }









}
