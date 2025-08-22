package congregamystica.api;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public interface IAspectProvider {
    void getAspects(Map<ItemStack, AspectList> aspects);
}
