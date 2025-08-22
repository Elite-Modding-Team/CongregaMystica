package congregamystica.integrations.botania;

import congregamystica.api.IProxy;
import congregamystica.integrations.botania.blocks.BlockSpecialFlowerCM;
import congregamystica.integrations.botania.blocks.subtiles.SubTileTainthistle;
import congregamystica.integrations.botania.blocks.subtiles.SubTileWhisperweed;
import congregamystica.registry.RegistrarCM;

public class BotaniaCM implements IProxy {
    public static final String TAINTHISTLE = "tainthistle";
    public static final String WHISPERWEED = "whisperweed";

    public static BlockSpecialFlowerCM<SubTileTainthistle> tainthistle;
    public static BlockSpecialFlowerCM<SubTileWhisperweed> whisperweed;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(tainthistle = new BlockSpecialFlowerCM<>(new SubTileTainthistle(), TAINTHISTLE));
        RegistrarCM.addAdditionToRegister(whisperweed = new BlockSpecialFlowerCM<>(new SubTileWhisperweed(), WHISPERWEED));
    }
}
