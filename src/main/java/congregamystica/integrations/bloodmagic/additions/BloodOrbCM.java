package congregamystica.integrations.bloodmagic.additions;

import WayofTime.bloodmagic.core.registry.OrbRegistry;
import WayofTime.bloodmagic.orb.BloodOrb;
import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.bloodmagic.BloodMagicCM;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class BloodOrbCM implements IAddition, IProxy {
    @SubscribeEvent
    public void registerBloodOrb(RegistryEvent.Register<BloodOrb> event) {
        event.getRegistry().register(new BloodOrb("bloodmagic:eldritch", ConfigHandlerCM.blood_magic.eldritchOrb.tier, Math.max(1000, ConfigHandlerCM.blood_magic.eldritchOrb.capacity), 100)
                .withModel(new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, "eldritch_blood_orb"), "inventory"))
                .setRegistryName("eldritch"));
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {

    }

    @Override
    public void registerResearchLocation() {
        //Register any associated research here
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(OrbRegistry.getOrbStack(BloodMagicCM.ORB_ELDRITCH), new AspectList().add(Aspect.LIFE, 10).add(Aspect.SENSES, 3).add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.blood_magic.eldritchOrb.enable;
    }

}
