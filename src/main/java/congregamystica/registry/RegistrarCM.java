package congregamystica.registry;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IBlockAddition;
import congregamystica.api.IItemAddition;
import congregamystica.api.IProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = CongregaMystica.MOD_ID)
public class RegistrarCM {

    private static final List<IAddition> ADDITIONS = new ArrayList<>();

    public static void addAdditionToRegister(IAddition addition) {
        if (addition != null && addition.isEnabled()) {
            ADDITIONS.add(addition);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        getBlockAdditions().forEach(block -> block.registerBlock(registry));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        getItemAdditions().forEach(item -> item.registerItem(registry));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        getAdditions().forEach(addition -> addition.registerModel(event));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        getAdditions().forEach(addition -> addition.registerRecipe(registry));
    }

    public static List<IAddition> getAdditions() {
        return ADDITIONS;
    }

    public static List<IBlockAddition> getBlockAdditions() {
        return getAdditions().stream().filter(addition -> addition instanceof IBlockAddition)
                .map(addition -> (IBlockAddition) addition).collect(Collectors.toList());
    }

    public static List<IItemAddition> getItemAdditions() {
        return getAdditions().stream().filter(addition -> addition instanceof IItemAddition)
                .map(addition -> (IItemAddition) addition).collect(Collectors.toList());
    }

    public static List<IProxy> getProxyAdditions() {
        return getAdditions().stream().filter(addition -> addition instanceof IProxy)
                .map(addition -> (IProxy) addition).collect(Collectors.toList());
    }
}
