package congregamystica.integrations.congregamystica.items;

import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.api.item.AugmentableEnergyStorageItem;
import congregamystica.api.item.EnergyStorageItemWrapper;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.List;
import java.util.Map;

public class ItemFluxCaster extends AbstractItemCasterCM {

    public ItemFluxCaster() {
        super("caster_flux");
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(ModIds.thaumic_augmentation.isLoaded) {
            return new AugmentableEnergyStorageItem(stack, this.getMaxEnergyStored(stack), 3);
        } else {
            return new EnergyStorageItemWrapper(stack, this.getMaxEnergyStored(stack));
        }
    }

    @Override
    public boolean showDurabilityBar(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(@NotNull ItemStack stack) {
        double maxEnergy = this.getMaxEnergyStored(stack);
        return (maxEnergy - this.getEnergyStored(stack)) / maxEnergy;
    }

    public int getMaxEnergyStored(ItemStack stack) {
        return ConfigHandlerCM.casters.fluxCaster.energyCapacity;
    }

    public int getEnergyStored(ItemStack stack) {
        return EnergyStorageItemWrapper.getEnergyStored(stack);
    }

    public void setEnergyStored(ItemStack stack, int energy) {
        energy = Math.min(energy, this.getMaxEnergyStored(stack));
        stack.setTagInfo("energy", new NBTTagInt(energy));
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(I18n.format(StringHelper.getTranslationKey("rf", "tooltip", "info"), this.getEnergyStored(stack), this.getMaxEnergyStored(stack)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    //##########################################################
    // AbstractItemCasterCM

    @Override
    public double getAltResourceBaseModifier() {
        return ConfigHandlerCM.casters.fluxCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.fluxCaster.conversionRate;
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        int cost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return I18n.format(StringHelper.getTranslationKey("caster_flux", "tooltip", "cost"), cost);
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        int cost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        int energy = this.getEnergyStored(casterStack);
        if(cost <= energy) {
            if(!simulate) {
                this.setEnergyStored(casterStack, energy - cost);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.fluxCaster.visDrainRadius;
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {

    }

    @Override
    public void registerResearchLocation() {

    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.casters.fluxCaster.enable;
    }
}
