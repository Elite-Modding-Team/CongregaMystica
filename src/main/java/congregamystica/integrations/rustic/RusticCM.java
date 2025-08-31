package congregamystica.integrations.rustic;

import congregamystica.CongregaMystica;
import congregamystica.api.IModModule;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.additions.AdditionCinderfireWhsikey;
import congregamystica.integrations.rustic.additions.AdditionFurniture;
import congregamystica.integrations.rustic.additions.AdditionShimmerdewSpirits;
import congregamystica.integrations.rustic.additions.AdditionViscousBrew;
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
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rustic.common.blocks.fluids.FluidBooze;
import rustic.common.blocks.fluids.FluidDrinkable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.lib.potions.PotionSunScorned;
import thaumcraft.common.lib.potions.PotionThaumarhia;
import thaumcraft.common.lib.potions.PotionWarpWard;

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
        RegistrarCM.addAdditionToRegister(new BlockChainCM("chain_brass"));
        RegistrarCM.addAdditionToRegister(new BlockCandleCM("candle_brass"));
        //Candelabra - Rustic 1.2.0+
        if(isNewRustic) {
            //TODO: See if this causes crashes with earlier versions of rustic
            RegistrarCM.addAdditionToRegister(new BlockCandleDoubleCM("candle_double_brass"));
        }
        RegistrarCM.addAdditionToRegister(new BlockCandleLeverCM("candle_lever_brass"));
        RegistrarCM.addAdditionToRegister(new BlockChandelierCM("chandelier_brass"));
        RegistrarCM.addAdditionToRegister(new BlockLanternCM("lantern_brass"));

        //TODO: Remaining stuff
        //  Brass frame thing
        //  Vis Crystal Sconces - See about making them one block with a dynamic appearance
        //  Tiny Pile of Blaze Dust - might remove and keep Cindermotes only for Cinderfire Wort
        //  Shimmerpetal - Shimmerdew Wort crushing tub item
        //  Viscap - Viscous Wort crushing tub item
        //  Cindermote Seeds - Cindermote herb seeds
        //  Shimmerpetal Bulb - Shimmerpetal herb seeds
        //  Viscap Spores - Viscap herb seeds
    }

    private void initFluids() {
        if(ConfigHandlerCM.rustic.enableCinderfireWhiskey) {//TODO: Config
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
                        player.setFire(12);
                    }
                    if(quality >= 0.5f) {
                        float saturation = 4f * quality;
                        player.getFoodStats().addStats(2, saturation);
                        //TODO: Adjustable duration
                        int duration = (int) (12000 * Math.max(Math.abs((quality - 0.5f) * 2f), 0));
                        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, duration));
                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, duration));
                    } else {
                        //TODO: Adjustable duration
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

        if(ConfigHandlerCM.rustic.enableShimmerdewSpirits) {//TODO: Config
            SHIMMERDEW_WORT = new FluidDrinkable("shimmerdewwort",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewwort_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewwort_flow")) {
                @Override
                public void onDrank(@NotNull World world, @NotNull EntityPlayer entityPlayer, @NotNull ItemStack itemStack, @NotNull FluidStack fluidStack) {

                }
            }.setDensity(1004).setViscosity(2000);

            SHIMMERDEW_SPIRITS = new FluidBooze("shimmerdewspirits",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewspirits_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/shimmerdewspirits_flow")) {
                @Override
                protected void affectPlayer(World world, EntityPlayer player, float quality) {
                    if(quality >= 0.5f) {
                        int duration = 1200 + (int) (6000.0f * Math.max(Math.abs(1.0f - quality), 0));
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

        if(ConfigHandlerCM.rustic.enableViscousBrew) {//TODO: Config
            VISCOUS_WORT = new FluidDrinkable("viscouswort",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscouswort_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscouswort_flow")) {
                @Override
                public void onDrank(@NotNull World world, @NotNull EntityPlayer entityPlayer, @NotNull ItemStack itemStack, @NotNull FluidStack fluidStack) {
                    //TODO
                }
            }.setDensity(1004).setViscosity(2000);

            VISCOUS_BREW = new FluidBooze("viscousbrew",
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscousbrew_still"),
                    new ResourceLocation(CongregaMystica.MOD_ID, "blocks/rustic/fluids/viscousbrew_flow")) {
                @Override
                protected void affectPlayer(World world, EntityPlayer player, float quality) {
                    //TODO
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
