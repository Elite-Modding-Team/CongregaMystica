package congregamystica.integrations.bloodmagic.items;

import WayofTime.bloodmagic.item.ItemBloodOrb;
import WayofTime.bloodmagic.orb.BloodOrb;
import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.item.IItemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

import javax.annotation.Nullable;

public class ItemBloodOrbCM extends ItemBloodOrb implements IItemAddition, IProxy {
    public ItemBloodOrbCM() {
        this.setRegistryName(CongregaMystica.MOD_ID, "eldritch_blood_orb");
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(creativeTab))
        {
            list.add(new ItemStack(this));
        }
    }

    @Nullable
    @Override
    public BloodOrb getOrb(ItemStack stack) {
        if (!stack.hasTagCompound())
            return null;

        return new BloodOrb("bloodmagic:transcendent", 6, 50000000, 100);
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
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
