package congregamystica.utils.helpers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.Aspect;
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
        AspectList stackAspects = AspectHelper.getObjectAspects(stack);
        return stackAspects != null ? stackAspects : new AspectList();
    }

    @NotNull
    public static AspectList getOreDictAspects(String oreDict) {
        AspectList aspects = AspectHelper.getObjectAspects(OreDictionary.getOres(oreDict).stream().findFirst().orElse(ItemStack.EMPTY));
        return aspects != null ? aspects : new AspectList();
    }

    public static void doFancyOreDictAspectRegistration(AspectEventProxy registry) {
        for(String oreDict : OreDictionary.getOreNames()) {
            if(oreDict.startsWith("ingot")) {
                handleIngotOrGemOreDict(registry, oreDict, "ingot");
            } else if(oreDict.startsWith("gem")) {
                handleIngotOrGemOreDict(registry, oreDict, "gem");
            } else if(oreDict.startsWith("dust")) {
                handleDustOreDict(registry, oreDict);
            } else if(oreDict.startsWith("nugget")) {
                handleNuggetOreDict(registry, oreDict);
            }
        }
    }

    private static void handleIngotOrGemOreDict(AspectEventProxy registry, String oreDict, String type) {
        String oreOreDict = oreDict.replaceFirst("^" + type, "ore");
        if(!OreDictionary.getOres(oreOreDict).isEmpty()) {
            AspectList oreAspects = AspectHelperCM.getOreDictAspects(oreOreDict);
            if(!oreAspects.aspects.isEmpty()) {
                AspectHelperCM.registerNonOverridingOreDictTags(registry, oreDict, new AspectList().add(oreAspects).remove(Aspect.EARTH));
            }
        }
    }

    private static void handleDustOreDict(AspectEventProxy registry, String dustOreDict) {
        String matchedOreDict = getAssociatedIngotOrGem(dustOreDict, "dust");
        if(!matchedOreDict.isEmpty()) {
            AspectList matchedAspects = AspectHelperCM.getOreDictAspects(matchedOreDict);
            if (!matchedAspects.aspects.isEmpty()) {
                AspectHelperCM.registerNonOverridingOreDictTags(registry, dustOreDict, new AspectList().add(matchedAspects).add(Aspect.ENTROPY, 1));
            }
        }
    }

    private static void handleNuggetOreDict(AspectEventProxy registry, String nuggetOreDict) {
        String matchedOreDict = getAssociatedIngotOrGem(nuggetOreDict, "nugget");
        if(!matchedOreDict.isEmpty()) {
            AspectList matchedAspects = AspectHelperCM.getOreDictAspects(matchedOreDict);
            if(!matchedAspects.aspects.isEmpty()) {
                AspectList nuggetAspects = new AspectList();
                matchedAspects.aspects.keySet().forEach(aspect -> nuggetAspects.add(aspect, 1));
                AspectHelperCM.registerNonOverridingOreDictTags(registry, nuggetOreDict, nuggetAspects);
            }
        }
    }

    private static String getAssociatedIngotOrGem(String oreDict, String type) {
        String associatedOreDict = oreDict.replaceFirst("^" + type, "ingot");
        if(OreDictionary.getOres(associatedOreDict).isEmpty()) {
            associatedOreDict = oreDict.replaceFirst("^" + type, "gem");
            if(OreDictionary.getOres(associatedOreDict).isEmpty()) {
                return "";
            }
        }
        return associatedOreDict;
    }


}
