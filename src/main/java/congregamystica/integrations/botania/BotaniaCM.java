package congregamystica.integrations.botania;

import congregamystica.api.IProxy;
import congregamystica.integrations.botania.blocks.BlockSpecialFlowerCM;
import congregamystica.registry.RegistrarCM;

public class BotaniaCM implements IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(BlockSpecialFlowerCM.BLOCK_SPECIAL_FLOWER);
    }
}
