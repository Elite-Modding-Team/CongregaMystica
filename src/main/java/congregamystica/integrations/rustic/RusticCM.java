package congregamystica.integrations.rustic;

import congregamystica.CongregaMystica;
import congregamystica.api.IModModule;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.additions.*;
import congregamystica.integrations.rustic.blocks.fluids.BlockFluidRusticCM;
import congregamystica.integrations.rustic.blocks.furniture.*;
import congregamystica.integrations.rustic.golems.GolemMaterialIronwood;
import congregamystica.registry.RegistrarCM;
import congregamystica.utils.helpers.ModHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rustic.common.blocks.fluids.FluidBooze;
import rustic.common.blocks.fluids.FluidDrinkable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.potions.PotionVisExhaust;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.entities.monster.EntityMindSpider;
import thaumcraft.common.lib.events.WarpEvents;
import thaumcraft.common.lib.potions.*;

import java.util.List;

public class RusticCM implements IModModule {
    public static boolean isNewRustic = ModHelper.isModLoaded(ModIds.rustic.modId, ModIds.ConstVersions.rustic_new, true, false);

    public static @Nullable Fluid CINDERFIRE_WHISKEY;
    public static @Nullable Fluid CINDERFIRE_WORT;
    public static @Nullable Fluid SHIMMERDEW_SPIRITS;
    public static @Nullable Fluid SHIMMERDEW_WORT;
    public static @Nullable Fluid VISCOUS_BREW;
    public static @Nullable Fluid VISCOUS_WORT;

    @Override
    public void preInit() {
        this.initFluids();

        //Golem Materials
        RegistrarCM.addAdditionToRegister(new GolemMaterialIronwood());

        //Herbs and Brews
        RegistrarCM.addAdditionToRegister(new AdditionCinderfireWhsikey());
        RegistrarCM.addAdditionToRegister(new AdditionShimmerdewSpirits());
        RegistrarCM.addAdditionToRegister(new AdditionViscousBrew());

        //Tables and Chairs
        RegistrarCM.addAdditionToRegister(new AdditionFurniture());

        //Candles and Decorations
        RegistrarCM.addAdditionToRegister(new AdditionBrassFeatures());

        //TODO: Remaining stuff
        //  Brass lattice
        //  Vis Crystal Sconces - See about making them one block with a dynamic appearance
    }

    private void initFluids() {
        if(ConfigHandlerCM.rustic.enableCinderfireWhiskey) {
            CINDERFIRE_WORT = new FluidDrinkable("cinderfirewort",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/cinderfirewort_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/cinderfirewort_flow")) {
                @Override
                public void onDrank(@NotNull World world, @NotNull EntityPlayer player, @NotNull ItemStack itemStack, @NotNull FluidStack fluidStack) {
                    if(world.rand.nextInt(3) == 0) {
                        player.setFire(6);
                    }
                    player.getFoodStats().addStats(1, 0.4f);
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
                }
            }.setDensity(1004).setViscosity(2000);

            CINDERFIRE_WHISKEY = new FluidBooze("cinderfirewhiskey",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/cinderfirewhiskey_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/cinderfirewhiskey_flow")) {
                @Override
                protected void affectPlayer(World world, EntityPlayer player, float quality) {
                    if(world.rand.nextInt(3) == 0) {
                        player.setFire(15);
                    }
                    if(quality >= 0.5f) {
                        float saturation = 4f * quality;
                        player.getFoodStats().addStats(2, saturation);
                        int duration = 1200 + (int)(10800.0F * Math.max(Math.abs((quality - 0.5F) * 2.0F), 0.0F));
                        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, duration));
                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, duration));
                    } else {
                        int duration = (int) (6000 * (Math.max(1.0f - quality, 0.25f)));
                        player.addPotionEffect(new PotionEffect(PotionSunScorned.instance, duration));
                        player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, duration));
                        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                    }
                }
            }.setInebriationChance(0.5f).setDensity(1034).setViscosity(1500);

            RegistrarCM.registerFluid(RusticCM.CINDERFIRE_WORT, true);
            RegistrarCM.registerFluid(RusticCM.CINDERFIRE_WHISKEY, false);
            RegistrarCM.addAdditionToRegister(new BlockFluidRusticCM("cinderfire_wort", RusticCM.CINDERFIRE_WORT, 4));
            RegistrarCM.addAdditionToRegister(new BlockFluidRusticCM("cinderfire_whiskey", RusticCM.CINDERFIRE_WHISKEY, 4));
        }

        if(ConfigHandlerCM.rustic.enableShimmerdewSpirits) {
            SHIMMERDEW_WORT = new FluidDrinkable("shimmerdewwort",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewwort_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewwort_flow")) {
                @Override
                public void onDrank(@NotNull World world, @NotNull EntityPlayer player, @NotNull ItemStack itemStack, @NotNull FluidStack fluidStack) {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
                }
            }.setDensity(1004).setViscosity(2000);

            SHIMMERDEW_SPIRITS = new FluidBooze("shimmerdewspirits",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewspirits_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewspirits_flow")) {
                @Override
                protected void affectPlayer(World world, EntityPlayer player, float quality) {
                    if(quality >= 0.5f) {
                        int duration = 1200 + (int)(10800.0F * Math.max(Math.abs((quality - 0.5F) * 2.0F), 0.0F));
                        player.addPotionEffect(new PotionEffect(PotionWarpWard.instance, duration));
                    } else {
                        int duration = (int)(800.0F * Math.max((1.0F - quality), 0.25F));
                        player.addPotionEffect(new PotionEffect(PotionThaumarhia.instance, duration));
                        duration = (int) (6000 * Math.max(1.0f - quality, 0));
                        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                    }
                }
            }.setInebriationChance(0.5f).setDensity(1034).setViscosity(2000);

            RegistrarCM.registerFluid(SHIMMERDEW_WORT, true);
            RegistrarCM.registerFluid(SHIMMERDEW_SPIRITS, false);
            RegistrarCM.addAdditionToRegister(new BlockFluidRusticCM("shimmerdew_wort", SHIMMERDEW_WORT, 4));
            RegistrarCM.addAdditionToRegister(new BlockFluidRusticCM("shimmerdew_spirits", SHIMMERDEW_SPIRITS, 4));
        }

        if(ConfigHandlerCM.rustic.enableViscousBrew) {
            VISCOUS_WORT = new FluidDrinkable("viscouswort",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscouswort_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscouswort_flow")) {
                @Override
                public void onDrank(@NotNull World world, @NotNull EntityPlayer player, @NotNull ItemStack itemStack, @NotNull FluidStack fluidStack) {
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
                    player.addPotionEffect(new PotionEffect(PotionBlurredVision.instance, 400));
                    ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1, IPlayerWarp.EnumWarpType.TEMPORARY);
                }
            }.setDensity(1004).setViscosity(2000);

            VISCOUS_BREW = new FluidBooze("viscousbrew",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscousbrew_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscousbrew_flow")) {
                @Override
                protected void affectPlayer(World world, EntityPlayer player, float quality) {
                    if(quality >= 0.5f) {
                        AxisAlignedBB area = new AxisAlignedBB(player.getPosition()).grow(24);
                        //Banish Eldritch Guardians
                        if(quality >= 0.9f) {
                            for(EntityEldritchGuardian guardian : world.getEntitiesWithinAABB(EntityEldritchGuardian.class, area)) {
                                guardian.setDropItemsWhenDead(false);
                                guardian.setDead();
                            }
                        }
                        //Banish Mind Spiders
                        if(quality >= 0.7f) {
                            for(EntityMindSpider spider : world.getEntitiesWithinAABB(EntityMindSpider.class, area)) {
                                spider.setDropItemsWhenDead(false);
                                spider.setDead();
                            }
                        }
                        //Remove Warp Effects
                        player.removeActivePotionEffect(PotionVisExhaust.instance);
                        player.removeActivePotionEffect(PotionThaumarhia.instance);
                        player.removeActivePotionEffect(PotionUnnaturalHunger.instance);
                        player.removeActivePotionEffect(PotionBlurredVision.instance);
                        player.removeActivePotionEffect(PotionSunScorned.instance);
                        player.removeActivePotionEffect(PotionDeathGaze.instance);
                    } else {
                        IPlayerWarp warp = ThaumcraftCapabilities.getWarp(player);
                        int counter = warp.getCounter();
                        warp.setCounter(10000);
                        WarpEvents.checkWarpEvent(player);
                        warp.setCounter(counter);
                    }
                }
            }.setInebriationChance(0.5f).setDensity(1034).setViscosity(1700);

            RegistrarCM.registerFluid(VISCOUS_WORT, true);
            RegistrarCM.registerFluid(VISCOUS_BREW, false);
            RegistrarCM.addAdditionToRegister(new BlockFluidRusticCM("viscous_wort", VISCOUS_WORT, 4));
            RegistrarCM.addAdditionToRegister(new BlockFluidRusticCM("viscous_brew", VISCOUS_BREW, 4));
        }

    }

    @Override
    public void registerResearchNode() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/rustic/rustic"));
    }
}
