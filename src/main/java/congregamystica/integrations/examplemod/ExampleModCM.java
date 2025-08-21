package congregamystica.integrations.examplemod;

import congregamystica.api.IProxy;
import congregamystica.integrations.examplemod.additions.GolemMaterialExample;
import congregamystica.integrations.examplemod.blocks.BlockExample;
import congregamystica.integrations.examplemod.events.EventHandlerExample;
import congregamystica.integrations.examplemod.items.ItemExample;
import congregamystica.registry.RegistrarCM;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class ExampleModCM implements IProxy {
    //Blocks
    public static final Block EXAMPLE_BLOCK = null;

    //Items
    public static final Item EXAMPLE_ITEM = null;

    @Override
    public void preInit() {
        RegistrarCM.addAdditionToRegister(new BlockExample());
        RegistrarCM.addAdditionToRegister(new ItemExample());
        RegistrarCM.addAdditionToRegister(new GolemMaterialExample());
        MinecraftForge.EVENT_BUS.register(new EventHandlerExample());
    }
}
