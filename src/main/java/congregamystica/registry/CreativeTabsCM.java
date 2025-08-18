package congregamystica.registry;

import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsCM extends CreativeTabs {
    public CreativeTabsCM(int length, String name) {
        super(length, name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack createIcon() {
        return new ItemStack(CongregaMysticaCM.MIMIC_FORK);
    }
}
