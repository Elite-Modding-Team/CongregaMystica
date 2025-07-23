package congregamystica.items;

import congregamystica.api.IAddition;
import net.minecraft.item.Item;

public class ItemAddition extends Item implements IAddition {
    private final boolean isEnabled;

    public ItemAddition(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
