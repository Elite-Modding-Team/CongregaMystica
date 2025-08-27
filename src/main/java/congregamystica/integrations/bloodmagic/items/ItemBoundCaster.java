package congregamystica.integrations.bloodmagic.items;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.core.registry.AlchemyArrayRecipeRegistry;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import congregamystica.api.item.AbstractItemCasterCM;
import congregamystica.utils.libs.ModIds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thecodex6824.thaumicaugmentation.api.TAItems;

import java.util.List;
import java.util.Map;

public class ItemBoundCaster extends AbstractItemCasterCM {
    public ItemBoundCaster() {
        super("bound_caster");
    }

    @Override
    public float getAltResourceModifier(World world, EntityPlayer player, ItemStack casterStack) {
        return this.getAltResourceBaseModifier();
    }

    @Override
    public float getAltResourceBaseModifier() {
        return 0;
    }

    @Override
    public boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float baseVisCost, float alternateResourceVis, boolean simulate) {
        return true;
    }

    @Override
    public void addAltResourceTooltip(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
    }
    
    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
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
        ItemStack casterStack;
        if(ModIds.thaumic_augmentation.isLoaded) {
            casterStack = new ItemStack(TAItems.GAUNTLET, 1, 0);
        } else {
            casterStack = new ItemStack(ItemsTC.casterBasic);
        }
        if(!casterStack.isEmpty()) {
            AlchemyArrayRecipeRegistry.registerRecipe(
                    ComponentTypes.REAGENT_BINDING.getStack(),
                    casterStack,
                    new AlchemyArrayEffectBinding("boundSword", new ItemStack(this)));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
