package congregamystica.integrations.congregamystica.items;

import org.jetbrains.annotations.NotNull;

import congregamystica.CongregaMystica;
import congregamystica.api.item.IItemAddition;
import congregamystica.api.util.EnumSortType;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IVisDiscountGear;

public class ItemGoggledHelmet extends ItemArmor implements IItemAddition, IGoggles, IVisDiscountGear {
    public ItemGoggledHelmet() {
        super(ThaumcraftMaterials.ARMORMAT_THAUMIUM, 4, EntityEquipmentSlot.HEAD);
        this.setRegistryName(CongregaMystica.MOD_ID, "goggled_thaumium_helmet");
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return ConfigHandlerCM.congrega_mystica.goggledThaumiumHelmet.visDiscount;
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return CongregaMystica.MOD_ID + ":" + "textures/models/congregamystica/goggled_thaumium_helmet.png";
    }
 
    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }

    //##########################################################
    // IItemAddition

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.CONGREGA_MYSTICA;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.congrega_mystica.goggledThaumiumHelmet.enable;
    }
}
