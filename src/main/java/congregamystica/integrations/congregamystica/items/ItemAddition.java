package congregamystica.integrations.congregamystica.items;

import congregamystica.api.item.AbstractItemAddition;

@SuppressWarnings("unused")
public class ItemAddition extends AbstractItemAddition {
    private final boolean isEnabled;

    public ItemAddition(String unlocName, boolean isEnabled) {
        super(unlocName);
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
