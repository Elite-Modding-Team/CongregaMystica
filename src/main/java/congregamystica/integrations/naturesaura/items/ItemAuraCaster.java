package congregamystica.integrations.naturesaura.items;

import WayofTime.bloodmagic.core.RegistrarBloodMagic;
import WayofTime.bloodmagic.core.recipe.IngredientBloodOrb;
import WayofTime.bloodmagic.item.ItemSlate;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.StringHelper;
import congregamystica.utils.libs.ModIds;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import thecodex6824.thaumicaugmentation.api.TAItems;

import java.util.Map;

public class ItemAuraCaster extends AbstractItemCasterCM {
    public ItemAuraCaster() {
        super("caster_aura");
    }

    @Override
    public double getAltResourceBaseModifier() {
        return ConfigHandlerCM.casters.botanistsCaster.consumptionModifier;
    }

    @Override
    public double getAltResourceConversionRate() {
        return ConfigHandlerCM.casters.botanistsCaster.conversionRate;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate) {
        if(altVisCost <= 0)
            return true;

        int cost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return NaturesAuraAPI.instance().extractAuraFromPlayer(player, cost, simulate);
    }

    @Override
    public String getAltResourceInfoTooltip(float altVisCost) {
        int cost = (int) Math.ceil(altVisCost * this.getAltResourceConversionRate());
        return I18n.format(StringHelper.getTranslationKey("caster_aura", "tooltip", "cost"), cost);
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCM.casters.botanistsCaster.visDrainRadius;
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
                "CM_CASTER_CLOCKWORK",
                new ItemStack(this),
                5,
                new AspectList().add(Aspect.AURA, 100).add(Aspect.PLANT, 100).add(Aspect.EXCHANGE, 50),
                Ingredient.fromStacks(casterStack),
                //TODO: Recipe
                Ingredient.fromItem(ItemsTC.fabric),
                Ingredient.fromStacks(ItemSlate.SlateType.IMBUED.getStack()),
                Ingredient.fromStacks(ItemSlate.SlateType.IMBUED.getStack()),
                new IngredientBloodOrb(RegistrarBloodMagic.ORB_APPRENTICE),
                Ingredient.fromStacks(ComponentTypes.REAGENT_BINDING.getStack()),
                Ingredient.fromItem(ItemsTC.salisMundus)
        ));
    }

    @Override
    public void registerResearchLocation() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/naturesaura/caster_aura"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        // TODO: Add aspects
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.casters.botanistsCaster.enable;
    }
}
