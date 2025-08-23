package congregamystica.utils.helpers;

import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.utils.libs.OreAspects;
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
        if(stack.isEmpty()) return new AspectList();
        AspectList stackAspects = AspectHelper.getObjectAspects(stack);
        return stackAspects != null ? stackAspects : new AspectList();
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

    public static AspectList getOreAspects(String oreOreDict) {
        return OreAspects.getOreDictAspects(oreOreDict);
    }

    public static AspectList getNativeClusterAspects(ClusterData clusterData) {
        AspectList nativeAspects = new AspectList().add(Aspect.ORDER, 5);
        if(!clusterData.associatedIngot.startsWith("gem")) {
            nativeAspects.add(Aspect.EARTH, 5);
        }
        getOreAspects(clusterData.associatedOre).aspects.forEach((aspect, amount) -> {
            if(aspect != Aspect.EARTH) {
                amount = Math.min(Math.max(amount, aspect == Aspect.METAL ? 15 : 10), amount + 5);
                nativeAspects.add(aspect, amount);
            }
            if(aspect == Aspect.CRYSTAL) {
                nativeAspects.remove(Aspect.EARTH);
            }
        });
        return nativeAspects;
    }

    public static AspectList getEldritchClusterAspects(ClusterData clusterData) {
        AspectList eldritchAspects = new AspectList().add(Aspect.FLUX, 10);
        getNativeClusterAspects(clusterData).aspects.forEach(((aspect, amount) -> {
            if(aspect != Aspect.ORDER) {
                eldritchAspects.add(aspect, amount + 5);
            }
        }));
        return eldritchAspects;
    }
}
