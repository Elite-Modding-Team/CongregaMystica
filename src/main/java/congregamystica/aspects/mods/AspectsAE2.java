package congregamystica.aspects.mods;

import appeng.api.definitions.IBlockDefinition;
import appeng.api.definitions.IItemDefinition;
import appeng.core.Api;
import appeng.core.api.definitions.ApiBlocks;
import appeng.core.api.definitions.ApiItems;
import appeng.core.api.definitions.ApiMaterials;
import appeng.core.api.definitions.ApiParts;
import congregamystica.api.IAspectProvider;
import congregamystica.aspects.AspectCalculator;
import congregamystica.utils.helpers.AspectHelperCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Tuple;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AspectsAE2 implements IAspectProvider {
    @Override
    public void getAspects(Map<ItemStack, AspectList> aspects) {
        ApiItems items = Api.INSTANCE.definitions().items();
        ApiMaterials materials = Api.INSTANCE.definitions().materials();
        ApiBlocks blocks = Api.INSTANCE.definitions().blocks();
        ApiParts parts = Api.INSTANCE.definitions().parts();

        //Ore Dictionary Values
        AspectCalculator.addAllOreDictItems(aspects,"crystalPureCertusQuartz",  new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ORDER, 5).add(Aspect.ENERGY, 2));
        AspectCalculator.addAllOreDictItems(aspects,"crystalPureNetherQuartz",  new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ORDER, 5));
        AspectCalculator.addAllOreDictItems(aspects,"dustCertusQuartz",         new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENTROPY, 1));
        AspectCalculator.addAllOreDictItems(aspects,"crystalFluix",             new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 5));
        AspectCalculator.addAllOreDictItems(aspects,"crystalPureFluix",         new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 5).add(Aspect.ORDER, 5));
        AspectCalculator.addAllOreDictItems(aspects,"dustFluix",                new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 5).add(Aspect.ENTROPY, 1));
        //Blocks
        aspects.put(getItemStack(blocks.skyStoneBlock()),               new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1));
        aspects.put(getItemStack(blocks.smoothSkyStoneBlock()),         new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ORDER, 1));
        aspects.put(getItemStack(blocks.skyStoneBrick()),               new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ORDER, 1));
        aspects.put(getItemStack(blocks.skyStoneSmallBrick()),          new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ORDER, 1));
        /*
        //Materials
        handleAspectRegister(registry, materials.calcProcessorPress(),  new AspectList().add(Aspect.METAL, 75).add(Aspect.CRAFT, 20));
        handleAspectRegister(registry, materials.calcProcessorPrint(),  new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.ORDER, 5).add(Aspect.CRYSTAL, 5));
        handleAspectRegister(registry, materials.calcProcessor(),       new AspectList().add(Aspect.MECHANISM, 15).add(Aspect.ORDER, 11).add(Aspect.CRYSTAL, 3).add(Aspect.ENERGY, 7).add(Aspect.CRAFT, 20)); //Calculation Processor
        handleAspectRegister(registry, materials.engProcessorPress(),   new AspectList().add(Aspect.METAL, 75).add(Aspect.CRAFT, 20)); //Engineering Press
        handleAspectRegister(registry, materials.engProcessorPrint(),   new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.CRYSTAL, 10).add(Aspect.DESIRE, 10)); //Engineering Circuit
        handleAspectRegister(registry, materials.engProcessor(),        new AspectList().add(Aspect.MECHANISM, 15).add(Aspect.CRYSTAL, 7).add(Aspect.DESIRE, 7).add(Aspect.ENERGY, 7).add(Aspect.ORDER, 7).add(Aspect.CRAFT, 20)); //Engineering Processor
        handleAspectRegister(registry, materials.logicProcessorPress(), new AspectList().add(Aspect.METAL, 75).add(Aspect.CRAFT, 20)); //Logic Press
        handleAspectRegister(registry, materials.logicProcessorPrint(), new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.METAL, 8).add(Aspect.DESIRE, 8)); //Logic Circuit
        handleAspectRegister(registry, materials.logicProcessor(),      new AspectList().add(Aspect.MECHANISM, 15).add(Aspect.METAL, 6).add(Aspect.DESIRE, 6).add(Aspect.ENERGY, 7).add(Aspect.ORDER, 7).add(Aspect.CRAFT, 20)); //Logic Processor
        handleAspectRegister(registry, materials.siliconPress(),        new AspectList().add(Aspect.METAL, 75).add(Aspect.CRAFT, 20)); //Silicon Press
        handleAspectRegister(registry, materials.siliconPrint(),        new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.ORDER, 10)); //Printed Silicon
        handleAspectRegister(registry, materials.namePress(),           new AspectList().add(Aspect.METAL, 75).add(Aspect.CRAFT, 20)); //Name Press
        handleAspectRegister(registry, materials.skyDust(),             new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ENTROPY, 1)); //Sky Stone Dust
        */
        //Parts - Shared Values
        aspects.put(getItemStack(parts.p2PTunnelRedstone()),                AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.p2PTunnelItems()),                   AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.p2PTunnelFluids()),                  AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.p2PTunnelLight()),                   AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.p2PTunnelFE()),                      AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.p2PTunnelEU()),                      AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.p2PTunnelGTEU()),                    AspectHelperCM.getStackAspects(getItemStack(parts.p2PTunnelME())));
        aspects.put(getItemStack(parts.interfaceTerminal()),                AspectHelperCM.getStackAspects(getItemStack(blocks.iface())));
        aspects.put(getItemStack(parts.fluidIface()),                       AspectHelperCM.getStackAspects(getItemStack(blocks.fluidIface())));
        aspects.put(getItemStack(parts.monitor()),                          AspectHelperCM.getStackAspects(getItemStack(parts.semiDarkMonitor())));
        aspects.put(getItemStack(parts.darkMonitor()),                      AspectHelperCM.getStackAspects(getItemStack(parts.semiDarkMonitor())));
        aspects.put(getItemStack(parts.interfaceConfigurationTerminal()),   AspectHelperCM.getStackAspects(getItemStack(parts.interfaceTerminal())));

        /*
        //Standardize Cable Values
        registry.registerObjectTag(parts.cableGlass().stack(AEColor.TRANSPARENT, 1),        new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.ENERGY, 1));
        registry.registerObjectTag(parts.cableCovered().stack(AEColor.TRANSPARENT, 1),      new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.ENERGY, 1).add(Aspect.BEAST, 8).add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.CRAFT, 2));
        registry.registerObjectTag(parts.cableSmart().stack(AEColor.TRANSPARENT, 1),        new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.ENERGY, 8).add(Aspect.BEAST, 6).add(Aspect.LIGHT, 7).add(Aspect.SENSES, 4));
        registry.registerObjectTag(parts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1), new AspectList().add(Aspect.CRYSTAL, 9).add(Aspect.ENERGY, 24).add(Aspect.BEAST, 18).add(Aspect.LIGHT, 21).add(Aspect.SENSES, 12));
        registry.registerObjectTag(parts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1),   new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.ENERGY, 29).add(Aspect.BEAST, 12).add(Aspect.LIGHT, 22).add(Aspect.SENSES, 13));

        for(AEColor color : AEColor.values()) {
            if(color == AEColor.TRANSPARENT) continue;
            registry.registerObjectTag(parts.cableGlass().stack(color, 1),          AspectHelperCM.getStackAspects(parts.cableGlass().stack(AEColor.TRANSPARENT, 1)));
            registry.registerObjectTag(parts.cableCovered().stack(color, 1),        AspectHelperCM.getStackAspects(parts.cableCovered().stack(AEColor.TRANSPARENT, 1)));
            registry.registerObjectTag(parts.cableSmart().stack(color, 1),          AspectHelperCM.getStackAspects(parts.cableSmart().stack(AEColor.TRANSPARENT, 1)));
            registry.registerObjectTag(parts.cableDenseCovered().stack(color, 1),   AspectHelperCM.getStackAspects(parts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1)));
            registry.registerObjectTag(parts.cableDenseSmart().stack(color, 1),     AspectHelperCM.getStackAspects(parts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1)));
        }
         */


        handleInscriberRecipes(aspects);
    }

    private ItemStack getItemStack(IItemDefinition itemDefinition) {
        return itemDefinition.maybeStack(1).orElse(ItemStack.EMPTY);
    }

    private ItemStack getItemStack(IBlockDefinition itemDefinition) {
        return itemDefinition.maybeStack(1).orElse(ItemStack.EMPTY);
    }

    private void handleInscriberRecipes(Map<ItemStack, AspectList> aspects) {
        Api.INSTANCE.registries().inscriber().getRecipes().forEach(recipe -> {
            Ingredient[] ingredients = recipe.getInputs().stream().map(Ingredient::fromStacks).toArray(Ingredient[]::new);
            Tuple<ItemStack, AspectList> tuple = AspectCalculator.generateAspectsFromIngredients(recipe.getOutput(), ingredients);
            if(!tuple.getFirst().isEmpty() && !tuple.getSecond().aspects.isEmpty()) {
                tuple.getSecond().aspects.put(Aspect.MECHANISM, 10);
                aspects.put(tuple.getFirst(), tuple.getSecond());
            }
        });
    }

}
