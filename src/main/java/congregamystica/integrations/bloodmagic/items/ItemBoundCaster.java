package congregamystica.integrations.bloodmagic.items;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.core.registry.AlchemyArrayRecipeRegistry;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thecodex6824.thaumicaugmentation.api.TAItems;

import java.util.Map;

public class ItemBoundCaster extends AbstractItemCasterCM implements IBindable {
    public ItemBoundCaster() {
        super("caster_bound");
    }

    @Override
    public float getAltResourceBaseModifier() {
        return (float) ConfigHandlerCM.casters.boundCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.boundCaster.conversionRate;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        Binding binding = this.getBinding(casterStack);
        if(binding != null) {
            SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
            EntityPlayer boundPlayer = network.getPlayer();
            int lpCost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
            if(lpCost <= network.getCurrentEssence()) {
                if (!simulate && !world.isRemote) {
                    network.syphonAndDamage(boundPlayer, SoulTicket.item(casterStack, world, player, lpCost));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        int lpCost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return I18n.format(StringHelper.getTranslationKey("caster_bound", "tooltip", "cost"), lpCost);
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.boundCaster.visDrainRadius;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull EnumHand hand) {
        //Need to apply the binding for LP usage
        ItemStack heldStack = player.getHeldItem(hand);
        Binding binding = this.getBinding(heldStack);
        if(binding == null) {
            return new ActionResult<>(EnumActionResult.PASS, heldStack);
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ItemStack casterStack;
        if(ModIds.thaumic_augmentation.isLoaded) {
            casterStack = new ItemStack(TAItems.GAUNTLET, 1, 0);
        } else {
            casterStack = new ItemStack(ItemsTC.casterBasic);
        }
        if(!casterStack.isEmpty()) {
            AlchemyArrayRecipeRegistry.registerRecipe(
                    ComponentTypes.REAGENT_BINDING.getStack(),
                    casterStack,
                    new AlchemyArrayEffectBinding("boundSword", new ItemStack(this)));
        }
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/bloodmagic/caster_bound"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.casters.boundCaster.enable;
    }

}
