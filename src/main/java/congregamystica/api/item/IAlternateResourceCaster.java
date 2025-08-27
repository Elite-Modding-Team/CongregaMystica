package congregamystica.api.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.casters.ICaster;

import java.util.List;

public interface IAlternateResourceCaster extends ICaster {
    /**
     * The modifier that will reduce the amount of Vis consumed for each cast by this gauntlet.
     *
     * @param world the world object
     * @param player the player casting the spell
     * @param casterStack the caster gauntlet ItemStack
     * @return Must return a value less than 1.0. A value less than 0 will increase the gauntlet Vis casting cost.
     */
    float getAltResourceModifier(World world, EntityPlayer player, ItemStack casterStack);

    /**
     * The base modifier that will reduce the amount of Vis consumed for each cast by this gauntlet. For a player-sensitive
     * version, use {@link IAlternateResourceCaster#getAltResourceModifier(World, EntityPlayer, ItemStack)}.
     *
     * @return Must return a value less than 1.0. A value less than 0 will increase the gauntlet Vis casting cost.
     */
    float getAltResourceBaseModifier();

    /**
     * Consumes the alternate resource during a casting action.
     *
     * @param world the world object
     * @param player the player casting the spell
     * @param casterStack the caster gauntlet ItemStack
     * @param alternateResourceVis the amount of vis that will be converted into the alternate resource. This value can be less than 0.
     * @param simulate whether this resource consumption should be simulated
     * @return true if the consuming action was a success, false otherwise
     */
    boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float baseVisCost, float alternateResourceVis, boolean simulate);

    /**
     * Adds custom alternate resource information. This information is added to the tooltip before the spell focus information.
     */
    void addAltResourceTooltip(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn);

    /**
     * Gets the radius this gauntlet can drain Vis from when casting a spell. The number of chunks drained is
     * equal to ((range * 2) + 1)^2. 0 = 1 chunk, 1 = 9 chunks, 2 = 25 chunks...
     *
     * @param player the player casting the spell
     * @param stack the caster gauntlet ItemStack
     * @return The vis chunk drain radius
     */
    int getChunkDrainRange(EntityPlayer player, ItemStack stack);
}
