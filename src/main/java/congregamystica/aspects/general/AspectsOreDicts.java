package congregamystica.aspects.general;

import congregamystica.api.IAspectProvider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AspectsOreDicts implements IAspectProvider {


    @Override
    public void getAspects(Map<ItemStack, AspectList> aspects) {
        //TODO: When running this be sure to get the OreDict values from the OreAspects class used for Cluster generation

        OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL, 1, 0));
        OreDictionary.registerOre("gemCharcoal", new ItemStack(Items.COAL, 1, 1));
        /*
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
        //Misc
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "bookshelf", new AspectList().add(Aspect.PLANT, 27).add(Aspect.MIND, 17).add(Aspect.WATER, 9).add(Aspect.BEAST, 6).add(Aspect.PROTECT, 6).add(Aspect.AIR, 4));
        registry.registerObjectTag("itemSilicon", new AspectList().add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.SENSES, 1));

        //Ores


        //Ingots


        //Dust
        registry.registerObjectTag("dustWheat", new AspectList().add(Aspect.PLANT, 5).add(Aspect.ENTROPY, 1));
        registry.registerObjectTag("dustEnder", new AspectList().add(Aspect.MOTION, 15).add(Aspect.ELDRITCH, 10).add(Aspect.ENTROPY, 1));
        registry.registerObjectTag("dustEnderPearl", new AspectList().add(Aspect.MOTION, 15).add(Aspect.ELDRITCH, 10).add(Aspect.ENTROPY, 1));

        //Nuggets
        registry.registerObjectTag("nuggetEnderpearl", new AspectList().add(Aspect.MOTION, 1).add(Aspect.ELDRITCH, 1));

        /*
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());
        AspectHelperCM.registerNonOverridingOreDictTags(registry, "", new AspectList());


        //Late register oredict aspects. These are last resort for generating item aspects.
        registerLateAspectOreDictTypes(registry);
         */
    }

    private void registerLateAspectOreDictTypes(AspectEventProxy registry) {
        //AspectHelperCM.registerNonOverridingOreDictTypeTags(registry, "^crop.+", new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
    }

}