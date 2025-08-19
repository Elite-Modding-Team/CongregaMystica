package congregamystica.integrations.appliedenergistics2;

import congregamystica.api.IProxy;
import congregamystica.utils.helpers.RegistryHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class AppliedEnergisticsCM implements IProxy {
    @Override
    public void init() {
        Item ae2Material = RegistryHelper.getRegisteredItem(new ResourceLocation(ModIds.ConstIds.applied_energistics, "material"));
        if(ae2Material != Items.AIR) {
            OreDictionary.registerOre("gemCertusQuartz", new ItemStack(ae2Material, 1, 0));
            OreDictionary.registerOre("gemChargedCerusQuartz", new ItemStack(ae2Material, 1, 1));
        }
    }
}
