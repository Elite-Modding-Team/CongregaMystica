package congregamystica.integrations.appliedenergistics2;

import congregamystica.api.IProxy;
import congregamystica.utils.helpers.RegistryHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
            OreDictionary.registerOre("gemQuartzCertus", new ItemStack(ae2Material, 1, 0));
            OreDictionary.registerOre("gemChargedQuartzCertus", new ItemStack(ae2Material, 1, 1));
        }
        Block certusOre = RegistryHelper.getRegisteredBlock(new ResourceLocation(ModIds.ConstIds.applied_energistics, "quartz_ore"));
        if(certusOre != Blocks.AIR) {
            OreDictionary.registerOre("oreQuartzCertus", certusOre);
        }
        Block chargedCertusOre = RegistryHelper.getRegisteredBlock(new ResourceLocation(ModIds.ConstIds.applied_energistics, "charged_quartz_ore"));
        if(chargedCertusOre != Blocks.AIR) {
            OreDictionary.registerOre("oreChargedQuartzCertus", chargedCertusOre);
        }
    }
}
