package congregamystica.aspects.mods;

import appeng.api.definitions.IBlockDefinition;
import appeng.api.definitions.IItemDefinition;
import appeng.core.Api;
import appeng.core.api.definitions.ApiBlocks;
import appeng.core.api.definitions.ApiItems;
import appeng.core.api.definitions.ApiMaterials;
import appeng.core.api.definitions.ApiParts;
import congregamystica.api.IAspectProvider;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AspectsAE2 implements IAspectProvider {
    @Override
    public void getAspects(Map<ItemStack, AspectList> aspects) {
        ApiItems items = Api.INSTANCE.definitions().items();
        ApiMaterials materials = Api.INSTANCE.definitions().materials();
        ApiBlocks blocks = Api.INSTANCE.definitions().blocks();
        ApiParts parts = Api.INSTANCE.definitions().parts();
        /*
        registry.registerObjectTag("crystalPureCertusQuartz",   new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ORDER, 5).add(Aspect.ENERGY, 2));
        registry.registerObjectTag("crystalPureNetherQuartz",   new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ORDER, 5));
        registry.registerObjectTag("dustCertusQuartz",          new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENTROPY, 1));
        registry.registerObjectTag("crystalFluix",              new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 5));
        registry.registerObjectTag("crystalPureFluix",          new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 5).add(Aspect.ORDER, 5));
        registry.registerObjectTag("dustFluix",                 new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 5).add(Aspect.ENTROPY, 1));

        //Blocks
        handleAspectRegister(registry, blocks.skyStoneBlock(),          new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1));
        handleAspectRegister(registry, blocks.smoothSkyStoneBlock(),    new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ORDER, 1));
        handleAspectRegister(registry, blocks.skyStoneBrick(),          new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ORDER, 1));
        handleAspectRegister(registry, blocks.skyStoneSmallBrick(),     new AspectList().add(Aspect.EARTH, 5).add(Aspect.ELDRITCH, 1).add(Aspect.ORDER, 1));
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
        //Parts - Missing Values
        handleAspectRegister(registry, parts.p2PTunnelRedstone(),       AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.p2PTunnelItems(),          AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.p2PTunnelFluids(),         AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.p2PTunnelLight(),          AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.p2PTunnelFE(),             AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.p2PTunnelEU(),             AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.p2PTunnelGTEU(),           AspectHelperCM.getStackAspects(parts.p2PTunnelME().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.interfaceTerminal(),       AspectHelperCM.getStackAspects(blocks.iface().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.fluidIface(),              AspectHelperCM.getStackAspects(blocks.fluidIface().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.monitor(),                 AspectHelperCM.getStackAspects(parts.semiDarkMonitor().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.darkMonitor(),             AspectHelperCM.getStackAspects(parts.semiDarkMonitor().maybeStack(1).orElse(ItemStack.EMPTY)));
        handleAspectRegister(registry, parts.interfaceConfigurationTerminal(), AspectHelperCM.getStackAspects(parts.interfaceTerminal().maybeStack(1).orElse(ItemStack.EMPTY)));


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
    }

    private void handleAspectRegister(AspectEventProxy registry, IItemDefinition itemDefinition, AspectList aspectList) {
        ItemStack stack = itemDefinition.maybeStack(1).orElse(ItemStack.EMPTY);
        if(!stack.isEmpty()) {
            registry.registerObjectTag(stack, aspectList);
        }
    }

    private void handleAspectRegister(AspectEventProxy registry, IBlockDefinition blockDefinition, AspectList aspectList) {
        ItemStack stack = blockDefinition.maybeStack(1).orElse(ItemStack.EMPTY);
        if(!stack.isEmpty()) {
            registry.registerObjectTag(stack, aspectList);
        }
    }

}
