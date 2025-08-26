package congregamystica.api.block;

import congregamystica.CongregaMystica;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * A base class for adding blocks. Automatically handles block and item registration.
 */
public abstract class AbstractBlockAddition extends Block implements IBlockAddition {
    @SuppressWarnings("ConstantConditions")
    public AbstractBlockAddition(String unlocName, Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }

    public AbstractBlockAddition(String unlocName, Material materialIn) {
        this(unlocName, materialIn, materialIn.getMaterialMapColor());
    }
}
