package congregamystica.integrations.bloodmagic.additions;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.core.registry.OrbRegistry;
import WayofTime.bloodmagic.orb.BloodOrb;
import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.items.ItemsTC;

public class BloodOrbCM implements IAddition, IProxy {
    public BloodOrb ORB_ELDRITCH = new BloodOrb(
            "bloodmagic:eldritch",
            ConfigHandlerCM.blood_magic.eldritchOrb.tier,
            Math.max(1000, ConfigHandlerCM.blood_magic.eldritchOrb.capacity),
            ConfigHandlerCM.blood_magic.eldritchOrb.drainRate);

    @SubscribeEvent
    public void registerBloodOrb(RegistryEvent.Register<BloodOrb> event) {
        event.getRegistry().register(ORB_ELDRITCH
                .withModel(new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, "eldritch_blood_orb"), "inventory"))
                .setRegistryName("eldritch"));
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().addBloodAltar(
                Ingredient.fromStacks(new ItemStack(ItemsTC.primordialPearl)),
                OrbRegistry.getOrbStack(this.ORB_ELDRITCH),
                ConfigHandlerCM.blood_magic.eldritchOrb.tier - 1,
                500000,
                200,
                400
        );
    }

    @Override
    public void registerResearchLocation() {
        //TODO: Register any associated research here
    }

    @Override
    public boolean isEnabled() {
        //TODO: Maybe require Tier 6 altar enabled.
        return ConfigHandlerCM.blood_magic.eldritchOrb.enable;
    }

}
