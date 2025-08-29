package congregamystica.integrations.harkenscythe.golems;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.IGolemProperties;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ScanBlockState;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.golems.EntityThaumcraftGolem;

public class GolemMaterialBiomass implements IGolemAddition, IProxy {
    /*
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase hurt = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();
        if(source instanceof EntityThaumcraftGolem && this.isBiomassGolem((EntityThaumcraftGolem) source)) {

            //Biomass golem spawns blood on hit
            HSEventLivingHurt.spawnBlood(hurt.world, hurt);
        }
    }
     */

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entityLiving = event.getEntityLiving();
        World world = entityLiving.world;
        if(!entityLiving.isDead && entityLiving instanceof EntityThaumcraftGolem && this.isBiomassGolem((EntityThaumcraftGolem) entityLiving)) {
            //Every 5 seconds the golem heals itself or gains half a heart of absorption
            if(!world.isRemote && entityLiving.ticksExisted % 100 == 0) {
                if(entityLiving.getHealth() >= entityLiving.getMaxHealth()) {
                    entityLiving.heal(2.0f);
                } else if(entityLiving.getAbsorptionAmount() < entityLiving.getMaxHealth() / 2.0f){
                    entityLiving.setAbsorptionAmount(entityLiving.getAbsorptionAmount() + 1.0f);
                }
            }
        }
    }

    public boolean isBiomassGolem(EntityThaumcraftGolem golem) {
        IGolemProperties properties = golem.getProperties();
        return properties.getMaterial().key.equals(this.getGolemMaterialKey());
    }

    //##########################################################
    // IGolemAddition

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public String getGolemMaterialKey() {
        return "CM_BIOMASS";
    }

    @Override
    public void registerGolemMaterial() {
        GolemMaterial.register(new GolemMaterial(
                this.getGolemMaterialKey(),
                new String[] {"CM_GOLEM_MAT_BIOMASS"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/harkenscythe/mat_biomass.png"),
                8588557,
                ConfigHandlerCM.golems.biomass.statHealth,
                ConfigHandlerCM.golems.biomass.statArmor,
                ConfigHandlerCM.golems.biomass.statDamage,
                ConfigHandlerCM.golems.biomass.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FIREPROOF, EnumGolemTrait.LIGHT}
        ));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.biomass.getMaterialStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            ScanningManager.addScannableThing(new ScanBlockState("f_CM_BIOMASS", block.getStateFromMeta(stack.getMetadata()), false));
        }
        ScanningManager.addScannableThing(new ScanItem("f_CM_BIOMASS", stack));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/golems/golem_mat_biomass"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.golems.biomass.enable;
    }
}
