package congregamystica.utils.helpers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
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

    public static AspectList getStackAspects(ItemStack stack) {
        AspectList stackAspects = AspectHelper.getObjectAspects(stack);
        return stackAspects != null ? stackAspects : new AspectList();
    }
}
