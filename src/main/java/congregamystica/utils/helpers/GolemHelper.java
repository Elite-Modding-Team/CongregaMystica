package congregamystica.utils.helpers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.golems.EnumGolemTrait;

public class GolemHelper {
    /**
     * Registers and returns a new EnumGolemTrait. This only registers the trait enum. Trait effects
     * will need to be handled separately.
     *
     * @param traitName The name of the enum trait
     * @param iconLocation The icon resource location. Must be the full texture path ending in .png
     * @return The new EnumGolemTrait. Must be stored so it can be accessed later.
     */
    public static EnumGolemTrait registerGolemTrait(String traitName, ResourceLocation iconLocation) {
        return EnumHelper.addEnum(EnumGolemTrait.class, traitName, new Class[]{ResourceLocation.class}, iconLocation);
    }
}
