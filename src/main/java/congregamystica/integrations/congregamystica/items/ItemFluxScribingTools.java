package congregamystica.integrations.congregamystica.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemAddition;
import congregamystica.api.item.EnergyStorageItemWrapper;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.IScribeTools;
import thaumcraft.api.items.ItemsTC;

import java.util.List;

public class ItemFluxScribingTools extends AbstractItemAddition implements IScribeTools {
    private static final int ENERGY_COST = ConfigHandlerCM.congrega_mystica.fluxScribingTools.energyCost;
    private static final int MAX_USES = ConfigHandlerCM.congrega_mystica.fluxScribingTools.maxUses;
    private static final int MAX_ENERGY = ENERGY_COST * MAX_USES; //1000 RF = 1 Vis

    public ItemFluxScribingTools() {
        super("flux_scribing_tools");
        this.setMaxStackSize(1);
        this.setMaxDamage(MAX_USES);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.addPropertyOverride(new ResourceLocation("depleted"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(@NotNull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if(stack.getTagCompound() != null) {
                    if (stack.getTagCompound().getInteger("energy") > 0) {
                        return 1.0F;
                    } else if (stack.getTagCompound().getInteger("energy") <= 0) {
                        return 2.0F;
                    }
                }
                return 0.0F;
            }
        });
    }

    @Override
    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if(isInCreativeTab(tab)) {
            items.add(new ItemStack(this, 1, MAX_USES));
            ItemStack base = new ItemStack(this);
            this.setEnergyStored(base, MAX_ENERGY);
            items.add(base);
        }
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        int currentDamage = stack.getItemDamage();

        if(damage > currentDamage) {
            int cost = (damage - currentDamage) * ENERGY_COST;
            int energy = this.getEnergyStored(stack);

            if(energy >= cost) {
                this.setEnergyStored(stack, energy - cost);
                return;
            }
        }

        super.setDamage(stack, damage);
    }

    @Override
    public int getDamage(@NotNull ItemStack stack) {
        float energy = this.getEnergyStored(stack);
        return MAX_USES - (int) (energy / this.getMaxEnergyStored(stack) * MAX_USES);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new EnergyStorageItemWrapper(stack, this.getMaxEnergyStored(stack));
    }

    @Override
    public boolean showDurabilityBar(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public double getDurabilityForDisplay(@NotNull ItemStack stack) {
        double maxEnergy = this.getMaxEnergyStored(stack);
        return (maxEnergy - this.getEnergyStored(stack)) / maxEnergy;
    }

    public int getMaxEnergyStored(ItemStack stack) {
        return MAX_ENERGY;
    }

    public int getEnergyStored(ItemStack stack) {
        return EnergyStorageItemWrapper.getEnergyStored(stack);
    }

    public void setEnergyStored(ItemStack stack, int energy) {
        energy = Math.min(energy, this.getMaxEnergyStored(stack));
        stack.setTagInfo("energy", new NBTTagInt(energy));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@NotNull ItemStack stack, World world, @NotNull List<String> tooltip, @NotNull ITooltipFlag flag) {
        tooltip.add(I18n.format(StringHelper.getTranslationKey("rf", "tooltip", "info"), this.getEnergyStored(stack), this.getMaxEnergyStored(stack)));
        super.addInformation(stack, world, tooltip, flag);
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(CongregaMystica.MOD_ID, "flux_scribing_tools"), new ShapelessArcaneRecipe(
                new ResourceLocation("CM_FLUX_SCRIBING_TOOLS"),
                "CM_FLUX_SCRIBING_TOOLS",
                25,
                new AspectList(),
                new ItemStack(this),
                new Object[]{new ItemStack(ItemsTC.scribingTools, 1, OreDictionary.WILDCARD_VALUE),
                        ItemsTC.mechanismSimple, new ItemStack(BlocksTC.inlay, 1)}
        ));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/congregamystica/flux_scribing_tools"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.congrega_mystica.fluxScribingTools.enable;
    }

}
