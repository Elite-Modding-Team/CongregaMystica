package congregamystica.integrations.botania.items;

import congregamystica.api.item.AbstractItemCasterCM;
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
import java.util.List;
import java.util.Map;

public class ItemManaCaster extends AbstractItemCasterCM {
    public ItemManaCaster() {
        super("mana_caster");
    }

    @Override
    public float getAltResourceModifier(World world, EntityPlayer player, ItemStack casterStack) {
        return this.getAltResourceBaseModifier();
    }

    @Override
    public float getAltResourceBaseModifier() {
        return 0;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float baseVisCost, float alternateResourceVis, boolean simulate) {
        return true;
    }

    @Override
    public void addAltResourceTooltip(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
    }
    
    @Override
    public int getChunkDrainRange(EntityPlayer player, ItemStack stack) {
        return 0;
    }
    
    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
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
