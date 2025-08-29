package congregamystica.integrations.naturesaura.items;

import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class ItemAuraCaster extends AbstractItemCasterCM {
    public ItemAuraCaster() {
        super("caster_aura");
    }

    @Override
    public float getAltResourceBaseModifier() {
        return (float) ConfigHandlerCM.casters.botanistsCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.botanistsCaster.conversionRate;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        int cost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return NaturesAuraAPI.instance().extractAuraFromPlayer(player, cost, simulate);
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        int cost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return I18n.format(StringHelper.getTranslationKey("caster_aura", "tooltip", "cost"), cost);
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.botanistsCaster.visDrainRadius;
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
        return ConfigHandlerCM.casters.botanistsCaster.enable;
    }
}
