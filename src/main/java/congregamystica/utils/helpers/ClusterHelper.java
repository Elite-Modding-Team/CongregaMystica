package congregamystica.utils.helpers;

import congregamystica.integrations.congregamystica.util.ClusterData;
import congregamystica.utils.libs.OreAspects;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ClusterHelper {
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
