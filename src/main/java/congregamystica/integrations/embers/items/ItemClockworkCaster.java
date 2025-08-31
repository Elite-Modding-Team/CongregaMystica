package congregamystica.integrations.embers.items;

import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import teamroots.embers.util.EmberInventoryUtil;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class ItemClockworkCaster extends AbstractItemCasterCM {
    public ItemClockworkCaster() {
        super("caster_clockwork");
    }

    @Override
    public double getAltResourceBaseModifier() {
        return ConfigHandlerCM.casters.clockworkCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.clockworkCaster.conversionRate;
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        double emberCost = altVisCost * getAltResourceConversionRate();
        return I18n.format(StringHelper.getTranslationKey("caster_clockwork", "tooltip", "cost"), emberCost);
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        double emberCost = altVisCost * this.getAltResourceConversionRate();
        if(EmberInventoryUtil.getEmberTotal(player) >= emberCost) {
            if(!simulate && !world.isRemote) {
                EmberInventoryUtil.removeEmber(player, emberCost);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.clockworkCaster.visDrainRadius;
    }


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
        return ConfigHandlerCM.casters.clockworkCaster.enable;
    }
}
