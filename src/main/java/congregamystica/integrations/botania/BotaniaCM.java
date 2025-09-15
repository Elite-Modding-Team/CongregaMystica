package congregamystica.integrations.botania;

import congregamystica.CongregaMystica;
import congregamystica.api.IModModule;
import congregamystica.integrations.botania.blocks.BlockSpecialFlowerCM;
import congregamystica.integrations.botania.blocks.subtiles.SubTileTaintthistle;
import congregamystica.integrations.botania.blocks.subtiles.SubTileWhisperweed;
import congregamystica.integrations.botania.items.ItemManaCaster;
import congregamystica.registry.RegistrarCM;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;

public class BotaniaCM implements IModModule {
    public static final String TAINTTHISTLE = "taintthistle";
    public static final String WHISPERWEED = "whisperweed_cm";

    public static BlockSpecialFlowerCM<SubTileTaintthistle> taintthistle;
    public static BlockSpecialFlowerCM<SubTileWhisperweed> whisperweed;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(taintthistle = new BlockSpecialFlowerCM<>(new SubTileTaintthistle(), TAINTTHISTLE));
        RegistrarCM.addAdditionToRegister(whisperweed = new BlockSpecialFlowerCM<>(new SubTileWhisperweed(), WHISPERWEED));
        
        RegistrarCM.addAdditionToRegister(new ItemManaCaster());
    }

    @Override
    public void registerResearchNode() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/botania/botania"));
    }
}
