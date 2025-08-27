package congregamystica.integrations.botania.items;

import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.casters.ItemFocus;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.List;
import java.util.Map;

public class ItemManaCaster extends AbstractItemCasterCM implements IManaUsingItem {
    public ItemManaCaster() {
        super("caster_elementium");
    }

    @Override
    public float getAltResourceModifier(World world, EntityPlayer player, ItemStack casterStack) {
        return this.getAltResourceBaseModifier();
    }

    @Override
    public float getAltResourceBaseModifier() {
        return (float) ConfigHandlerCM.casters.elementium.consumptionModifier;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float baseVisCost, float alternateResourceVis, boolean simulate) {
        if(alternateResourceVis <= 0)
            return true;

        int manaCost = (int) Math.ceil(alternateResourceVis * ConfigHandlerCM.casters.elementium.conversionRate);
        if(ManaItemHandler.requestManaExactForTool(casterStack, player, manaCost, false)) {
            if(!simulate) {
                ManaItemHandler.requestManaExactForTool(casterStack, player, manaCost, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public void addAltResourceTooltip(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        if(this.hasFocusStack(stack)) {
            ItemStack focusStack = this.getFocusStack(stack);
            ItemFocus focus = (ItemFocus) focusStack.getItem();
            float altVisCost = focus.getVisCost(focusStack) * this.getAltResourceBaseModifier();
            int manaCost = (int) Math.ceil(altVisCost * ConfigHandlerCM.casters.elementium.conversionRate);
            tooltip.add(I18n.format(StringHelper.getTranslationKey("caster_elementium", "tooltip", "cost"), manaCost));
        }
    }
    
    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.elementium.visDrainRadius;
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
