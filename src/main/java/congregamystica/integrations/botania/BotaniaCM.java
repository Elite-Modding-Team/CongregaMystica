package congregamystica.integrations.botania;

import congregamystica.api.IProxy;
import congregamystica.integrations.botania.blocks.BlockSpecialFlowerCM;
import congregamystica.integrations.botania.blocks.subtiles.SubTileWhisperweed;
import congregamystica.registry.RegistrarCM;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BotaniaCM implements IProxy {
    public static final String WHISPERWEED = "whisperweed";

    public static BlockSpecialFlowerCM<SubTileWhisperweed> whisperweed;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(whisperweed = new BlockSpecialFlowerCM<>(new SubTileWhisperweed(), WHISPERWEED));
    }
}
