package congregamystica.integrations.harkenscythe.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import mod.emt.harkenscythe.event.HSEventLivingHurt;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

public class GolemMaterialBiomass extends GolemMaterial implements IAddition, IProxy {
	
    public GolemMaterialBiomass() {
        //TODO: Increase golem damage/armor as these are combat-oriented
        super(
                "CM_BIOMASS",
                new String[] {"CM_GOLEM_MAT_BIOMASS"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/harkenscythe/mat_biomass.png"),
                8588557,
                16,
                6,
                3,
                ConfigHandlerCM.golems.biomass.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {}
        );
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase hurt = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();
        if(source instanceof EntityThaumcraftGolem && this.isBiomassGolem((EntityThaumcraftGolem) source)) {

            //TODO: RNG check for successful soul reap
            //Biomass golem spawns blood on hit
            HSEventLivingHurt.spawnBlood(hurt.world, hurt);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entityLiving = event.getEntityLiving();
        World world = entityLiving.world;
        if(!entityLiving.isDead && entityLiving instanceof EntityThaumcraftGolem && this.isBiomassGolem((EntityThaumcraftGolem) entityLiving)) {
            //Every 5 seconds the golem heals itself or gains half a heart of absorption
            if(!world.isRemote && entityLiving.ticksExisted % 100 == 0) {
                if(entityLiving.getHealth() >= entityLiving.getMaxHealth()) {
                    entityLiving.heal(1.0f);
                } else if(entityLiving.getAbsorptionAmount() < entityLiving.getMaxHealth()){
                    entityLiving.setAbsorptionAmount(entityLiving.getAbsorptionAmount() + 1.0f);
                }
            }
        }
    }

    public boolean isBiomassGolem(EntityThaumcraftGolem golem) {
        IGolemProperties properties = golem.getProperties();
        //TODO: Add biomass trait check
        return properties.getMaterial().key.equals(this.key);// || properties.getTraits().contains();
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        //Golem registry is retarded so you can't use the pre-init
    }

    @Override
    public void init() {
        //TODO: Register material property. Remember to use GolemHelper#registerGolemTrait()
        MinecraftForge.EVENT_BUS.register(this);
    	register(this);
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
                "research/harkenscythe/golem_mat_biomass"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.golems.biomass.enable;
    }
}
