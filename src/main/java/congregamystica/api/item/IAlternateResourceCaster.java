package congregamystica.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.casters.ICaster;

public interface IAlternateResourceCaster extends ICaster {
    /**
     * The modifier that will reduce the amount of Vis consumed for each cast by this gauntlet.
     *
     * @param world the world object
     * @param player the player casting the spell
     * @param casterStack the caster gauntlet ItemStack
     * @return Must return a value less than 1.0. A value less than 0 will increase the gauntlet Vis casting cost.
     */
    default double getAltResourceModifier(World world, EntityPlayer player, ItemStack casterStack) {
        return this.getAltResourceBaseModifier();
    }

    /**
     * The base modifier that will reduce the amount of Vis consumed for each cast by this gauntlet. For a player-sensitive
     * version, use {@link IAlternateResourceCaster#getAltResourceModifier(World, EntityPlayer, ItemStack)}.
     *
     * @return Must return a value less than 1.0. A value less than 0 will increase the gauntlet Vis casting cost.
     */
    double getAltResourceBaseModifier();

    /**
     * Returns the alternate resource conversion rate per point of Vis.
     */
    double getAltResourceConversionRate();

    /**
     * Returns the tooltip string that will be used to display the alternate resource cost of the currently equipped spell.
     * This method should return a translated string with the format "XX Cost: Costs 100 XX per cast"
     *
     * @param altVisCost the amount of vis that should be converted into the alternate resource
     * @return A formatted and translated string that will be added to the caster stack tooltip
     */
    String getAltResourceInfoTooltip(float altVisCost);

    /**
     * Consumes the alternate resource during a casting action.
     *
     * @param world the world object
     * @param player the player casting the spell
     * @param casterStack the caster gauntlet ItemStack
     * @param altVisCost the amount of vis that will be converted into the alternate resource. This value can be less than 0.
     * @param simulate whether this resource consumption should be simulated
     * @return true if the consuming action was a success, false otherwise
     */
    boolean consumeAltResource(World world, EntityPlayer player, ItemStack casterStack, float altVisCost, boolean simulate);

    /**
     * Gets the radius this gauntlet can drain Vis from when casting a spell. The number of chunks drained is
     * equal to ((range * 2) + 1)^2. 0 = 1 chunk, 1 = 9 chunks, 2 = 25 chunks...
     *
     * @param player the player casting the spell
     * @param stack the caster gauntlet ItemStack
     * @return The vis chunk drain radius
     */
    int getChunkDrainRadius(EntityPlayer player, ItemStack stack);
}
