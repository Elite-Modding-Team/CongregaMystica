package congregamystica.registry;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

//TODO: Modid
@Mod.EventBusSubscriber
public class RegistrarCM {
    private static final List<Block> BLOCK_ADDITIONS = new ArrayList<>();
    private static final List<Item> ITEM_ADDITIONS = new ArrayList<>();
    private static final List<IProxy> PROXY_ADDITIONS = new ArrayList<>();

    public static void addAdditionToRegister(IAddition addition) {
        if(addition != null && addition.isEnabled()) {
            if (addition instanceof Block) {
                BLOCK_ADDITIONS.add((Block) addition);
            } else if (addition instanceof Item) {
                ITEM_ADDITIONS.add((Item) addition);
            }

            if (addition instanceof IProxy) {
                PROXY_ADDITIONS.add((IProxy) addition);
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        BLOCK_ADDITIONS.stream().filter(block -> block instanceof IAddition).forEach(block -> ((IAddition) block).registerBlock(registry));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BLOCK_ADDITIONS.stream().filter(block -> block instanceof IAddition).forEach(block -> ((IAddition) block).registerItem(registry));
        ITEM_ADDITIONS.stream().filter(item -> item instanceof IAddition).forEach(item -> ((IAddition) item).registerItem(registry));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BLOCK_ADDITIONS.stream().filter(block -> block instanceof IAddition).forEach(block -> ((IAddition) block).registerModel(event));
        ITEM_ADDITIONS.stream().filter(item -> item instanceof IAddition).forEach(item -> ((IAddition) item).registerModel(event));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        BLOCK_ADDITIONS.stream().filter(block -> block instanceof IAddition).forEach(block -> ((IAddition) block).registerRecipe(registry));
        ITEM_ADDITIONS.stream().filter(item -> item instanceof IAddition).forEach(item -> ((IAddition) item).registerRecipe(registry));
        PROXY_ADDITIONS.stream().filter(proxy -> proxy instanceof IAddition).forEach(proxy -> ((IAddition) proxy).registerRecipe(registry));
    }

    public static List<IProxy> getProxyAdditions() {
        return PROXY_ADDITIONS;
    }
}
