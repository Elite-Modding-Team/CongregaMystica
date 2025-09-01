package congregamystica.integrations.botania.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import thecodex6824.thaumicaugmentation.api.TAItems;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.ModItems;

public class ItemManaCaster extends AbstractItemCasterCM implements IManaUsingItem {
    public ItemManaCaster() {
        super("caster_elementium");
    }

    @Override
    public double getAltResourceBaseModifier() {
        return ConfigHandlerCM.casters.elementiumCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.elementiumCaster.conversionRate;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        int manaCost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return ManaItemHandler.requestManaExactForTool(casterStack, player, manaCost, simulate);
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        int manaCost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return I18n.format(StringHelper.getTranslationKey("caster_elementium", "tooltip", "cost"), manaCost);
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.elementiumCaster.visDrainRadius;
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
        ItemStack casterStack;
        if(ModIds.thaumic_augmentation.isLoaded) {
            casterStack = new ItemStack(TAItems.GAUNTLET, 1, 0);
        } else {
            casterStack = new ItemStack(ItemsTC.casterBasic);
        }
        ThaumcraftApi.addInfusionCraftingRecipe(this.getRegistryName(), new InfusionRecipe(
                "CM_CASTER_ELEMENTIUM",
                new ItemStack(this),
                5,
                new AspectList().add(Aspect.ELDRITCH, 75).add(Aspect.MAGIC, 75).add(Aspect.EXCHANGE, 50),
                Ingredient.fromStacks(casterStack),
                new OreIngredient("clothManaweave"),
                new OreIngredient("ingotElvenElementium"),
                new OreIngredient("ingotElvenElementium"),
                new OreIngredient("elvenDragonstone"),
                Ingredient.fromItem(ModItems.manaTablet),
                Ingredient.fromItem(ItemsTC.salisMundus)
        ));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/botania/caster_elementium"));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
