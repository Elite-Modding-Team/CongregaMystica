package congregamystica.integrations.bloodmagic.items;

import java.util.List;
import java.util.Map;

import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import congregamystica.CongregaMystica;
import congregamystica.api.IItemAddition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.IScribeTools;
import WayofTime.bloodmagic.iface.IBindable;

public class ItemBloodScribingTools extends Item implements IItemAddition, IScribeTools, IBindable {
    public ItemBloodScribingTools() {
        this.setRegistryName(CongregaMystica.MOD_ID, "blood_scribing_tools");
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setHasSubtypes(false);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //Register any recipes associated with the item here
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public void registerResearchLocation() {
        //Register any associated research here
    }

    @Override
    public Map<ItemStack, AspectList> registerAspects() {
        return IItemAddition.super.registerAspects();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        if (!stack.hasTagCompound())
            return;

        Binding binding = getBinding(stack);
        if (binding != null) {
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity holder, int itemSlot, boolean isSelected) {
        if (stack.getItemDamage() > 0) {
            Binding binding = getBinding(stack);

            if (binding != null) {
                if (NetworkHelper.getSoulNetwork(binding).syphon(SoulTicket.item(stack, world, holder, 25)) > 0) {
                    stack.setItemDamage(stack.getItemDamage() - 1);
                }
            }
        }
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        Binding binding = getBinding(stack);

        if (binding != null) {
            if (NetworkHelper.getSoulNetwork(binding).syphon(SoulTicket.item(stack, 25 * damage)) > 0) {
                super.setDamage(stack, 0);
            } else {
                super.setDamage(stack, damage);
            }
        } else {
            super.setDamage(stack, damage);
        }
    }
}
