package congregamystica.proxy;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.HashMap;
import java.util.Map;

public class CommonProxy {
    public void preInit() {
        InitIntegrations.getModAdditions().forEach(IProxy::preInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInit);
    }

    public void init() {
        InitIntegrations.getModAdditions().forEach(IProxy::init);
        RegistrarCM.getProxyAdditions().forEach(IProxy::init);
        registerResearch();
    }

    public void postInit() {
        InitIntegrations.getModAdditions().forEach(IProxy::postInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInit);
        registerAspects();
    }

    private void registerResearch() {
        //Set up research categories here. Make sure to do it before the IAddition research is fired.

        RegistrarCM.getAdditions().forEach(IAddition::registerResearchLocation);
    }

    private void registerAspects() {
        Map<ItemStack, AspectList> aspectMap = new HashMap<>();
        RegistrarCM.getAdditions().forEach(addition -> {
            addition.registerAspects(aspectMap);
        });
        aspectMap.forEach((stack, list) -> {
            if(!stack.isEmpty()) {
                this.appendAspects(stack, list);
            }
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
