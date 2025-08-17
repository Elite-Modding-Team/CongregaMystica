package congregamystica.proxy;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class CommonProxy {
    public void preInit() {
        InitIntegrations.getModAdditions().forEach(IProxy::preInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInit);
    }

    public void init() {
        InitIntegrations.getModAdditions().forEach(IProxy::init);
        RegistrarCM.getProxyAdditions().forEach(IProxy::init);
        RegistrarCM.getAdditions().forEach(IAddition::registerResearchLocation);
    }

    public void postInit() {
        InitIntegrations.getModAdditions().forEach(IProxy::postInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInit);
        this.registerAspects();
    }

    private void registerAspects() {
        RegistrarCM.getAdditions().forEach(addition -> {
            addition.registerAspects().forEach(this::appendAspects);
        });
    }

    private void appendAspects(ItemStack stack, AspectList toAdd) {
        toAdd = toAdd.copy();
        AspectList existing = ThaumcraftCraftingManager.getObjectTags(stack);
        if (existing != null) {
            toAdd = toAdd.add(existing);
        }
        CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), toAdd);
    }
}
