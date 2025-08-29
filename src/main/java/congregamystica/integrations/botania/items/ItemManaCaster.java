package congregamystica.integrations.botania.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.Map;

public class ItemManaCaster extends AbstractItemCasterCM implements IManaUsingItem {
    public ItemManaCaster() {
        super("caster_elementium");
    }

    @Override
    public float getAltResourceBaseModifier() {
        return (float) ConfigHandlerCM.casters.elementiumCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.elementiumCaster.conversionRate;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        int manaCost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return ManaItemHandler.requestManaExactForTool(casterStack, player, manaCost, simulate);
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        int manaCost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return I18n.format(StringHelper.getTranslationKey("caster_elementium", "tooltip", "cost"), manaCost);
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.elementiumCaster.visDrainRadius;
    }
    
    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }

    //##########################################################
    // IManaUsingItem

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {

    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/botania/caster_elementium"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        // TODO: Add aspects
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
