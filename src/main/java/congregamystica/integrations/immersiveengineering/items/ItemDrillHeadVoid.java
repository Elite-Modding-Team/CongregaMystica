package congregamystica.integrations.immersiveengineering.items;

import blusunrize.immersiveengineering.api.tool.IDrillHead;
import blusunrize.immersiveengineering.common.items.ItemDrill;
import congregamystica.CongregaMystica;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.immersiveengineering.util.DrillStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ItemDrillHeadVoid extends AbstractDrillHead {
    public static final DrillStats VOID_HEAD_STATS = new DrillStats(
            "ingotVoid",
            ConfigHandlerCM.immersive_engineering.voidDrillHead.drillSize,
            1,
            ConfigHandlerCM.immersive_engineering.voidDrillHead.drillLevel,
            ConfigHandlerCM.immersive_engineering.voidDrillHead.drillSpeed,
            ConfigHandlerCM.immersive_engineering.voidDrillHead.drillAttack,
            ConfigHandlerCM.immersive_engineering.voidDrillHead.drillDurability,
            CongregaMystica.MOD_ID + ":models/immersiveengineering/drill_void");

    public ItemDrillHeadVoid() {
        super("drill_head_void", VOID_HEAD_STATS);
    }

    @Override
    public void onUpdate(@NotNull ItemStack stack, World worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote && entityIn.ticksExisted % 20 == 0 && this.getHeadDamage(stack) < this.getMaximumHeadDamage(stack)) {
            this.damageHead(stack, -1);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if(!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().ticksExisted % 20 == 0) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            for(int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
                ItemStack stack = player.inventory.getStackInSlot(slot);
                if(stack.getItem() instanceof ItemDrill) {
                    ItemStack head = ((ItemDrill) stack.getItem()).getHead(stack);
                    if(head.getItem() instanceof IDrillHead) {
                        ((IDrillHead) head.getItem()).damageHead(head, -1);
                    }
                }
            }
        }
    }

    //##########################################################
    // IAddition

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void registerResearchLocation() {
        //TODO
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.immersive_engineering.thaumiumDrillHead.enable && ConfigHandlerCM.immersive_engineering.voidDrillHead.enable;
    }
}
