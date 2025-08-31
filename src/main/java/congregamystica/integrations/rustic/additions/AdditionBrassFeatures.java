package congregamystica.integrations.rustic.additions;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.RusticCM;
import congregamystica.integrations.rustic.blocks.furniture.*;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AdditionBrassFeatures implements IAddition, IProxy {
    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new BlockChainCM("chain_brass"));
        RegistrarCM.addAdditionToRegister(new BlockCandleCM("candle_brass"));
        //Candelabra - Rustic 1.2.0+
        if(RusticCM.isNewRustic) {
            //TODO: See if this causes crashes with earlier versions of rustic
            RegistrarCM.addAdditionToRegister(new BlockCandleDoubleCM("candle_double_brass"));
        }
        RegistrarCM.addAdditionToRegister(new BlockCandleLeverCM("candle_lever_brass"));
        RegistrarCM.addAdditionToRegister(new BlockChandelierCM("chandelier_brass"));
        RegistrarCM.addAdditionToRegister(new BlockLanternCM("lantern_brass"));
        //RegistrarCM.addAdditionToRegister(new BlockLatticeCM("lattice_brass"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {

    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableBrassFeatures;
    }
}
