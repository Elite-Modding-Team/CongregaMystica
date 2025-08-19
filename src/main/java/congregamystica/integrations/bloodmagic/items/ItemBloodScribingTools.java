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
import congregamystica.api.item.IItemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.IScribeTools;
import thaumcraft.api.items.ItemsTC;

import java.util.List;
import java.util.Map;

public class ItemBloodScribingTools extends Item implements IItemAddition, IScribeTools, IBindable {
    public ItemBloodScribingTools() {
        this.setRegistryName(CongregaMystica.MOD_ID, "blood_scribing_tools");
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setMaxStackSize(1);
        this.setMaxDamage(20);
        this.setHasSubtypes(false);
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
    public void setDamage(ItemStack stack, int damage) {
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
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

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
    public void registerModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public void registerResearchLocation() {
        //Register any associated research here
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
