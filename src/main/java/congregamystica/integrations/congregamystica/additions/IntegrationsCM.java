package congregamystica.integrations.congregamystica.additions;

import congregamystica.api.IAddition;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class IntegrationsCM implements IAddition {

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        //TODO: Register any general ore dictionary aspects here.
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
