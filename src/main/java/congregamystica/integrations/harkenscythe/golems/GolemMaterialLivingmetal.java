package congregamystica.integrations.harkenscythe.golems;

import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

public class GolemMaterialLivingmetal implements IGolemAddition, IProxy {
    @Override
    public String getGolemMaterialKey() {
        return "CM_LIVINGMETAL";
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        World world = event.getEntityLiving().world;
        EntityLivingBase deadEntity = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();
        if(source instanceof EntityThaumcraftGolem && this.isLivingMetalGolem((EntityThaumcraftGolem) source)) {
            EntityThaumcraftGolem golem = (EntityThaumcraftGolem) event.getSource().getTrueSource();
            //Livingmetal golem full heals on kill
            if(!world.isRemote && !golem.isDead) {
                golem.heal(golem.getMaxHealth());
            }
            //Livingmetal golem spawns Soul Essence on kill
            //HSEventLivingDeath.spawnSoul(deadEntity.world, deadEntity);
        }
    }

    public boolean isLivingMetalGolem(EntityThaumcraftGolem golem) {
        IGolemProperties properties = golem.getProperties();
        return properties.getMaterial().key.equals(this.getGolemMaterialKey());
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void registerGolemMaterial() {
        GolemMaterial.register(new GolemMaterial(
                this.getGolemMaterialKey(),
                new String[] {"CM_GOLEM_MAT_LIVINGMETAL"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/harkenscythe/mat_livingmetal.png"),
                46030,
                ConfigHandlerCM.golems.livingmetal.statHealth,
                ConfigHandlerCM.golems.livingmetal.statArmor,
                ConfigHandlerCM.golems.livingmetal.statDamage,
                ConfigHandlerCM.golems.livingmetal.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FIREPROOF, EnumGolemTrait.HEAVY}
        ));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.livingmetal.getMaterialStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            ScanningManager.addScannableThing(new ScanBlockState("f_CM_LIVINGMETAL", block.getStateFromMeta(stack.getMetadata()), false));
        }
        ScanningManager.addScannableThing(new ScanItem("f_CM_LIVINGMETAL", stack));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/harkenscythe/golem_mat_livingmetal"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.golems.livingmetal.enable;
    }
}
