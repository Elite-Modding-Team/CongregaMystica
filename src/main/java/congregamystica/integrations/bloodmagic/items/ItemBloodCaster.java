package congregamystica.integrations.bloodmagic.items;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

public class ItemBloodCaster extends AbstractItemCasterCM {
    public ItemBloodCaster() {
        super("caster_blood");
    }

    @Override
    public float getAltResourceBaseModifier() {
        return 0;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack,
                                      float alternateResourceVis, boolean simulate) {
        return true;
    }

    @Override
    public void addAlternateResourceTooltip(@NotNull ItemStack stack, @Nullable World worldIn,
                                            @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
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
        return ConfigHandlerCM.blood_magic.bloodyScribingTools.enable;
    }

}
