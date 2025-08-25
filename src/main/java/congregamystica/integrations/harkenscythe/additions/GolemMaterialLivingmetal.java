package congregamystica.integrations.harkenscythe.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import mod.emt.harkenscythe.event.HSEventLivingDeath;
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

public class GolemMaterialLivingmetal extends GolemMaterial implements IAddition, IProxy {
    public GolemMaterialLivingmetal() {
        super(
                "CM_LIVINGMETAL",
                new String[] {"CM_GOLEM_MAT_LIVINGMETAL"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/harkenscythe/mat_livingmetal.png"),
                46030,
                20,
                8,
                5,
                ConfigHandlerCM.golems.livingmetal.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {}
        );
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

            //TODO: RNG check for successful soul reap
            //Livingmetal golem spawns Soul Essence on kill
            HSEventLivingDeath.spawnSoul(deadEntity.world, deadEntity);
        }
    }

    public boolean isLivingMetalGolem(EntityThaumcraftGolem golem) {
        IGolemProperties properties = golem.getProperties();
        return properties.getMaterial().key.equals(this.key);
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        //Golem registry is retarded so you can't use the pre-init
    }

    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
    	register(this);
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
