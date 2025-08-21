package congregamystica.integrations.congregamystica.additions;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import congregamystica.integrations.congregamystica.items.ItemNativeCluster;
import congregamystica.utils.helpers.AspectHelperCM;
import congregamystica.utils.helpers.PechHelper;
import congregamystica.utils.misc.EnumPechType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class IntegrationsCM implements IAddition, IProxy {
    @Override
    public void init() {
        if(ConfigHandlerCM.clusters.registerPechTrades) {
            for (ItemNativeCluster cluster : CongregaMysticaCM.getNativeClusters()) {
                if (!OreDictionary.getOres(cluster.getAssociatedOre()).isEmpty()) {
                    PechHelper.addPechTrade(EnumPechType.MINER, 1, cluster.getDefaultInstance());
                }
            }
        }
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        if(!ConfigHandlerCM.aspects.generalOreDict)
            return;

        //TODO: Register any general ore dictionary aspects here.
        //Seeds
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllseed", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 1));
        //Crops
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllfruit", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllgrain", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllmushroom", new AspectList().add(Aspect.PLANT, 5).add(Aspect.EARTH, 2).add(Aspect.DARKNESS, 2));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllnut", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5).add(Aspect.EARTH, 1));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllveggie", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5).add(Aspect.EARTH, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllspice", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5).add(Aspect.SENSES, 1));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllfiber", new AspectList().add(Aspect.PLANT, 5).add(Aspect.EARTH, 2).add(Aspect.CRAFT, 1));
        //Meat - listAllmeatraw needs to be registered last so it doesn't override other values
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllchickenraw", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.AIR, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllduckraw", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.AIR, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllturkeyraw", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.AIR, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllfishraw", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.WATER, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllmeatraw", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.EARTH, 5));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllfishcooked", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllmeatcooked", new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        //Misc Stuff
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "listAllmilk", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5).add(Aspect.LIFE, 10));


        /*
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
         */

        //Late register oredict aspects. These are last resort for generating item aspects.
        registerLateAspectOreDictTypes(registry);
    }

    private void registerLateAspectOreDictTypes(AspectEventProxy registry) {
        AspectHelperCM.registerNonOverridingOreDictTypeTags(registry, "^crop.+", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
