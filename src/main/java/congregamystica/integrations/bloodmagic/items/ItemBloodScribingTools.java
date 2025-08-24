package congregamystica.integrations.bloodmagic.items;

import WayofTime.bloodmagic.altar.AltarTier;
import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.IScribeTools;
import thaumcraft.api.items.ItemsTC;

import java.util.List;
import java.util.Map;

public class ItemBloodScribingTools extends AbstractItemAddition implements IScribeTools, IBindable {
    public ItemBloodScribingTools() {
        super("blood_scribing_tools");
        this.setMaxStackSize(1);
        this.setMaxDamage(20);
        this.setHasSubtypes(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, @NotNull List<String> tooltip, @NotNull ITooltipFlag flag) {
        if (!stack.hasTagCompound())
            return;

        Binding binding = getBinding(stack);
        if (binding != null) {
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));
        }
    }

    @Override
    public void onUpdate(@NotNull ItemStack stack, World world, @NotNull Entity holder, int itemSlot, boolean isSelected) {
        if (!world.isRemote && holder.ticksExisted % 100 == 0 && stack.getItemDamage() > 0) {
            Binding binding = getBinding(stack);

            if (binding != null) {
                SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
                if (network.syphonAndDamage(network.getCachedPlayer(), SoulTicket.item(stack, world, holder, ConfigHandlerCM.blood_magic.bloodyScribingTools.lpCost)).isSuccess()) {
                    stack.setItemDamage(stack.getItemDamage() - 1);
                }
            }
        }
    }

    @Override
    public void setDamage(@NotNull ItemStack stack, int damage) {
        Binding binding = getBinding(stack);
        if (binding != null) {
            SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
            if (!network.syphonAndDamage(network.getCachedPlayer(), SoulTicket.item(stack, ConfigHandlerCM.blood_magic.bloodyScribingTools.lpCost * damage)).isSuccess()) {
                super.setDamage(stack, damage);
            }
        } else {
            super.setDamage(stack, damage);
        }
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        BloodMagicAPI.INSTANCE.getRecipeRegistrar().addBloodAltar(
                Ingredient.fromStacks(new ItemStack(ItemsTC.scribingTools)),
                new ItemStack(this),
                AltarTier.TWO.ordinal(),
                2000,
                5,
                5
        );
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/bloodmagic/blood_scribing_tools"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(new ItemStack(this),
                new AspectList().add(Aspect.LIFE, 10).add(Aspect.SENSES, 3).add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.blood_magic.bloodyScribingTools.enable;
    }

}
