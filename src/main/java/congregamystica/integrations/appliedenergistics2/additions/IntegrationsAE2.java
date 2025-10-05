package congregamystica.integrations.appliedenergistics2.additions;

import appeng.core.Api;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.utils.helpers.RegistryHelper;
import congregamystica.utils.libs.ModIds;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class IntegrationsAE2 implements IAddition, IProxy {
    private boolean certusClusterEnabled;
    private boolean chargedClusterEnabled;

    @Override
    public void preInit() {
        for(String str : ConfigHandlerCM.clusters.additionalClusters) {
            if(str.startsWith("oreQuartzCertus;")) {
                this.certusClusterEnabled = true;
            } else if(str.startsWith("oreChargedQuartzCertus;")) {
                this.chargedClusterEnabled = true;
            }
        }
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        if(this.certusClusterEnabled) {
            registry.registerObjectTag("crystalCertusQuartz", new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 2));
        }
        if(this.chargedClusterEnabled) {
            registry.registerObjectTag("gemChargedQuartzCertus", new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.ENERGY, 10));
        }
    }

    @Override
    public void registerOreDicts() {
        Item ae2Material = RegistryHelper.getRegisteredItem(new ResourceLocation(ModIds.ConstIds.applied_energistics, "material"));
        if(ae2Material != Items.AIR) {
            if(this.certusClusterEnabled) {
                OreDictionary.registerOre("gemQuartzCertus", new ItemStack(ae2Material, 1, 0));
            }
            if(this.chargedClusterEnabled) {
                OreDictionary.registerOre("gemChargedQuartzCertus", new ItemStack(ae2Material, 1, 1));
            }
        }
        if(this.certusClusterEnabled) {
            Block certusOre = Api.INSTANCE.definitions().blocks().quartzOre().maybeBlock().orElse(Blocks.AIR);
            if (certusOre != Blocks.AIR) {
                OreDictionary.registerOre("oreQuartzCertus", certusOre);
            }
        }
        if(this.chargedClusterEnabled) {
            Block chargedCertusOre = Api.INSTANCE.definitions().blocks().quartzOreCharged().maybeBlock().orElse(Blocks.AIR);
            if (chargedCertusOre != Blocks.AIR) {
                OreDictionary.registerOre("oreChargedQuartzCertus", chargedCertusOre);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        //Always enabled as this is required for AE2 clusters to generate correctly
        return true;
    }
}
