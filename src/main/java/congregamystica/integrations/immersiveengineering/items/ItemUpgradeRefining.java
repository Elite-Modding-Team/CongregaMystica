package congregamystica.integrations.immersiveengineering.items;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.tool.IUpgrade;
import blusunrize.immersiveengineering.common.items.ItemDrill;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.item.AbstractItemAddition;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.IngredientNBTTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.utils.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ItemUpgradeRefining extends AbstractItemAddition implements IUpgrade, IProxy {
    public static final String UPGRADE_REFINING = "refining";

    public ItemUpgradeRefining() {
        super("upgrade_refining");
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getItemStackLimit() {
        return ConfigHandlerCM.immersive_engineering.refiningUpgrade.maxUpgrades;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        String[] flavorText = ImmersiveEngineering.proxy.splitStringOnWidth(
                I18n.format(StringHelper.getTranslationKey(UPGRADE_REFINING, "tooltip", "info"), this.getItemStackLimit()), 200);
        tooltip.addAll(Arrays.asList(flavorText));
    }

    @SubscribeEvent
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        if(!event.getWorld().isRemote && event.getHarvester() != null) {
            ItemStack heldStack = event.getHarvester().getHeldItemMainhand();
            if(heldStack.getItem() instanceof ItemDrill) {
                int refining = ((ItemDrill) heldStack.getItem()).getUpgrades(heldStack).getInteger(UPGRADE_REFINING);
                if(refining > 0) {
                    float chance = (float) refining * 0.1f;
                    boolean did = false;
                    for(int i = 0; i < event.getDrops().size(); i++) {
                        ItemStack drop = event.getDrops().get(i);
                        ItemStack refined = Utils.findSpecialMiningResult(drop, chance, event.getWorld().rand);
                        if(!ItemStack.areItemsEqual(drop, refined)) {
                            event.getDrops().set(i, refined);
                            did = true;
                        }
                    }

                    if(did) {
                        event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2f, 0.7f + event.getWorld().rand.nextFloat() * 0.2f);
                    }
                }
            }
        }
    }

    //##########################################################
    // IUpgrade

    @Override
    public Set<String> getUpgradeTypes(ItemStack itemStack) {
        return Collections.singleton("DRILL");
    }

    @Override
    public boolean canApplyUpgrades(ItemStack target, ItemStack upgrade) {
        return true;
    }

    @Override
    public void applyUpgrades(ItemStack target, ItemStack upgrade, NBTTagCompound modifications) {
        ItemNBTHelper.modifyInt(modifications, UPGRADE_REFINING, upgrade.getCount());
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ThaumcraftApi.addInfusionCraftingRecipe(this.getRegistryName(), new InfusionRecipe(
                "CM_UPGRADE_REFINING",
                new ItemStack(this),
                2,
                new AspectList().add(Aspect.ORDER, 80).add(Aspect.EXCHANGE, 60),
                new ItemStack(ItemsTC.mechanismSimple),
                new IngredientNBTTC(new ItemStack(Items.ENCHANTED_BOOK)),
                new ItemStack(ItemsTC.salisMundus),
                new ItemStack(ItemsTC.nuggets, 1, 10),
                new ItemStack(ItemsTC.nuggets, 1, 10)
        ));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/immersiveengineering/upgrade_refining"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.immersive_engineering.refiningUpgrade.enable;
    }
}
