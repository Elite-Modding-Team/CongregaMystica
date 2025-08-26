package congregamystica.api.item;

import congregamystica.CongregaMystica;
import net.minecraft.item.Item;

/**
 * A base class for adding items. Automatically handles item registration.
 */
public abstract class AbstractItemAddition extends Item implements IItemAddition {
    @SuppressWarnings("ConstantConditions")
    public AbstractItemAddition(String unlocName) {
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CongregaMystica.tabCM);
    }
}
