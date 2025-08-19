package congregamystica.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.IItemAddition;
import net.minecraft.item.Item;

public class ItemAddition extends Item implements IItemAddition {
    private final boolean isEnabled;

    public ItemAddition(String unlocName, boolean isEnabled) {
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
