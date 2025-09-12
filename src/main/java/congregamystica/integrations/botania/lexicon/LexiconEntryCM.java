package congregamystica.integrations.botania.lexicon;

import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.common.lexicon.BasicLexiconEntry;

public class LexiconEntryCM extends BasicLexiconEntry implements IAddonEntry {

    public LexiconEntryCM(String unlocalizedName, LexiconCategory category) {
        super(unlocalizedName, category);
    }

    @Override
    public String getSubtitle() {
        return "[Congrega Mystica]";
    }
}
