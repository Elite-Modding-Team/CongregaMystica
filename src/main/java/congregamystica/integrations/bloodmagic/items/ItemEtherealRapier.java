package congregamystica.integrations.bloodmagic.items;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.item.ItemSlate;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import congregamystica.CongregaMystica;
import congregamystica.api.IProxy;
import congregamystica.api.item.IItemAddition;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.bloodmagic.BloodMagicCM;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.AttributeModifierOperation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.items.RechargeHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemEtherealRapier extends ItemSword implements IItemAddition, IRechargable, IBindable, IProxy {

    public ItemEtherealRapier() {
        super(BloodMagicCM.BOUND_VIS);
        this.setRegistryName(CongregaMystica.MOD_ID, "ethereal_rapier");
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    @Override
    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)) {
            items.add(Utils.setUnbreakable(new ItemStack(this)));
        }
    }

    @Override
    public @NotNull Multimap<String, AttributeModifier> getAttributeModifiers(@NotNull EntityEquipmentSlot slot, @NotNull ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if(slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.getAttackDamage(), AttributeModifierOperation.ADD));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.0f, AttributeModifierOperation.ADD));
        }
        return multimap;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return new IRarity() {
            @Override
            public TextFormatting getColor() {
                return TextFormatting.DARK_RED;
            }

            @Override
            public String getName() {
                return "Bloody";
            }
        };
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        Binding binding = this.getBinding(stack);
        if (binding != null) {
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));
        }
    }

    public boolean hasCharge(ItemStack stack) {
        return this.getBinding(stack) != null && this.hasVisCharge(stack);
    }

    public boolean hasVisCharge(ItemStack stack) {
        int energy = this.getEnergy(stack);
        int vis = this.getVisCharge(stack);
        return energy > 0 || vis > 0;
    }

    public void consumeCharge(ItemStack stack, EntityPlayer player) {
        Binding binding = this.getBinding(stack);
        if(binding != null) {
            NetworkHelper.getSoulNetwork(binding).syphonAndDamage(player, SoulTicket.item(stack, ConfigHandlerCM.blood_magic.etherealRapier.lpCost));
        }
        int energy = this.getEnergy(stack);
        int vis = this.getVisCharge(stack);
        if(energy > 0) {
            this.setEnergy(stack, energy - 1);
        } else {
            energy += 5;
            this.setEnergy(stack, energy - 1);
            this.setVisCharge(stack, vis - 1);
        }
    }

    public int getEnergy(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getInteger("tc.energy") : 0;
    }

    public void setEnergy(ItemStack stack, int energy) {
        stack.setTagInfo("tc.energy", new NBTTagInt(energy));
    }

    public int getVisCharge(ItemStack stack) {
        return RechargeHelper.getCharge(stack);
    }

    public void setVisCharge(ItemStack stack, int visCharge) {
        stack.setTagInfo("tc.charge", new NBTTagInt(visCharge));
    }

    @Override
    public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return 200;
    }

    @Override
    public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return EnumChargeDisplay.NORMAL;
    }

    //##########################################################
    // IProxy

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if(event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack heldStack = player.getHeldItemMainhand();
            if(!heldStack.isEmpty() && heldStack.getItem() == this && this.hasCharge(heldStack)) {
                EntityLivingBase target = event.getEntityLiving();
                float absorption = target.getAbsorptionAmount();
                if(absorption > 0) {
                    float damage = event.getAmount();
                    float newDamage = Math.max(damage, Math.min(absorption, damage * 2.0f));
                    event.setAmount(newDamage);
                } else {
                    event.getSource().setDamageBypassesArmor();
                }
                if(!player.isCreative()) {
                    this.consumeCharge(heldStack, player);
                }
            }
        }
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(CongregaMystica.MOD_ID, "ethereal_rapier"), new InfusionRecipe(
                "CM_ETHEREAL_RAPIER",
                Utils.setUnbreakable(new ItemStack(this)),
                8,
                new AspectList().add(Aspect.LIFE, 100).add(Aspect.DEATH, 100).add(Aspect.ENTROPY, 100).add(Aspect.AURA, 50),
                Ingredient.fromItem(RegistrarBloodMagicItems.BOUND_SWORD),
                Ingredient.fromStacks(ThaumcraftApiHelper.makeCrystal(Aspect.VOID)),
                Ingredient.fromStacks(ThaumcraftApiHelper.makeCrystal(Aspect.SOUL)),
                Ingredient.fromStacks(ItemSlate.SlateType.DEMONIC.getStack()),
                Ingredient.fromStacks(ThaumcraftApiHelper.makeCrystal(Aspect.MAN)),
                Ingredient.fromStacks(ThaumcraftApiHelper.makeCrystal(Aspect.LIFE)),
                Ingredient.fromItem(ItemsTC.visResonator)
        ));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/bloodmagic/ethereal_rapier"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.casters.boundCaster.enable && ConfigHandlerCM.blood_magic.etherealRapier.enable;
    }
}
