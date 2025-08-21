package congregamystica.integrations.botania;

import congregamystica.api.IProxy;
import congregamystica.integrations.botania.blocks.BlockSpecialFlowerCM;
import congregamystica.integrations.botania.blocks.subtiles.SubTileWhisperweed;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BotaniaCM implements IProxy {
    public static final String WHISPERWEED = "whisperweed";

    public static BlockSpecialFlowerCM<SubTileWhisperweed> whisperweed;

    @Override
    public void preInit() {
        if (whisperweed.isEnabled()) {
            whisperweed.preInit();
        }
    }

    @Override
    public void init() {
        if (whisperweed.isEnabled()) {
            whisperweed.init();
        }
    }

    @Override
    public void postInit() {
        if (whisperweed.isEnabled()) {
            whisperweed.postInit();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preInitClient() {
        if (whisperweed.isEnabled()) {
            whisperweed.registerModel(null);
        }
    }

    static {
        whisperweed = new BlockSpecialFlowerCM<>(new SubTileWhisperweed(), WHISPERWEED);

    }
}
