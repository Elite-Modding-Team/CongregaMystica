package congregamystica.utils.libs;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper library class used to register aspects for ores used by default supported native clusters. The methods used
 * in this class have a bit of overhead, so it is better to only use it for cluster handling.
 */
public class OreAspects {
    private static final Map<String, AspectList> ORE_ASPECTS = new HashMap<>();

    /**
     * Gets the registered AspectList associated with the passed ore dictionary value. Returns an empty AspectList if there
     * are no aspects registered for the ore dictionary value or if there are no items that match the passed ore dictionary.
     */
    public static AspectList getOreDictAspects(String oreDict) {
        if(ORE_ASPECTS.containsKey(oreDict)) {
            return ORE_ASPECTS.get(oreDict);
        } else {
            AspectList oreAspects = AspectHelper.getObjectAspects(OreDictionary.getOres(oreDict).stream().findFirst().orElse(ItemStack.EMPTY));
            if(oreAspects != null && oreAspects.size() > 0) {
                addOreAspect(oreDict, oreAspects, true);
                return oreAspects;
            } else {
                return new AspectList();
            }
        }
    }

    /**
     * Adds an ore dictionary linked aspect list.
     *
     * @param oreDict The ore dictionary value to add
     * @param aspectList The linked aspect list for the passed ore dictionary value
     * @param overwrite If this value will overwrite any existing values for this ore dictionary. Setting this to true will
     *                  also overwrite any existing value in Thaumcraft's aspect cache.
     */
    public static void addOreAspect(String oreDict, AspectList aspectList, boolean overwrite) {
        if(overwrite) {
            ORE_ASPECTS.put(oreDict, aspectList);
        } else if(getOreDictAspects(oreDict).size() <= 0) {
            ORE_ASPECTS.put(oreDict, aspectList);
        }
    }

    /**
     * Adds an ore dictionary linked aspect list.
     *
     * @param oreDict The ore dictionary value to add
     * @param aspectList The linked aspect list for the passed ore dictionary value
     */
    public static void addOreAspect(String oreDict, AspectList aspectList) {
        addOreAspect(oreDict, aspectList, false);
    }

    public static Map<String, AspectList> getOreAspects() {
        if(ORE_ASPECTS.isEmpty()) {
            initOreAspects();
        }
        return ORE_ASPECTS;
    }

    public static void initOreAspects() {
        //TODO: Double check these values.
        addOreAspect("oreAluminum", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.AIR, 5));
        addOreAspect("oreAstralStarmetal", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.LIGHT, 5).add(Aspect.DARKNESS, 5));
        addOreAspect("oreArdite", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.FIRE, 5));
        addOreAspect("oreChargedQuartzCertus", new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.EARTH, 5).add(Aspect.ENERGY, 10));
        addOreAspect("oreCobalt", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.EXCHANGE, 5));
        addOreAspect("oreIridium", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 5));
        addOreAspect("oreMithril", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.MAGIC, 5));
        addOreAspect("oreNickel", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.MECHANISM, 5));
        addOreAspect("oreOsmium", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.ALCHEMY, 5));
        addOreAspect("orePlatinum", new AspectList().add(Aspect.METAL, 10).add(Aspect.EARTH, 5).add(Aspect.DESIRE, 10).add(Aspect.CRYSTAL, 5));
        addOreAspect("oreQuartzCertus", new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.EARTH, 5));
    }
}
