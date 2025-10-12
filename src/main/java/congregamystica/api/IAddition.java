package congregamystica.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

/**
 * Interface used to help register
 */
public interface IAddition {
    /**
     * Registers a recipe during the IRecipe registry event. Always try to use this over proxy recipe
     * registries to avoid incompatibilities.
     */
    default void registerRecipe(IForgeRegistry<IRecipe> registry) {}

    /**
     * Registers the model for the item or block. This method must be called to register the model.
     */
    @SideOnly(Side.CLIENT)
    default void registerModel(ModelRegistryEvent event) {}

    /**
     * Registers a research location. This method will only be called if {@link IAddition#isEnabled()} returns true.
     * This method fires in the {@link net.minecraftforge.fml.common.event.FMLInitializationEvent} stage.
     */
    default void registerResearchLocation() {}

    /**
     * A helper method for registering item aspects. This event is fired in the {@link net.minecraftforge.fml.common.event.FMLPostInitializationEvent}
     * stage. You can either handle the aspect registration manually, or add them to the passed map.
     * <p>
     * For reference on how to register entity aspects, see {@link thaumcraft.common.config.ConfigAspects}.
     */
    default void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {}

    /**
     * A helper method for registering ore dictionary values. This fires right after the item registry event completes.
     */
    default void registerOreDicts() {}

    /**
     * Returns true if this item is enabled. Used for additions with configurable Enable/Disable or if they
     * are reliant on another feature.
     */
    boolean isEnabled();
}
