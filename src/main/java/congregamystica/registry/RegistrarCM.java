package congregamystica.registry;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.api.block.IBlockAddition;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.api.item.IColoredItem;
import congregamystica.api.item.IItemAddition;
import congregamystica.utils.helpers.LogHelper;
import congregamystica.utils.libs.OreAspects;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

import java.awt.*;
import java.util.*;
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
        /*
        //Sorting items so clusters are first.
        //It's not efficient, but IDGAF. It works and it only runs once.
        List<IItemAddition> itemAdditions = new LinkedList<>();
        int clusterIndex = 0;
        int eldritchIndex = 0;
        for(IItemAddition addition : getItemAdditions()) {
            if(addition instanceof ItemNativeCluster) {
                itemAdditions.add(clusterIndex, addition);
                clusterIndex++;
                eldritchIndex++;
            } else if(addition instanceof ItemEldritchCluster) {
                itemAdditions.add(eldritchIndex, addition);
                eldritchIndex++;
            } else {
                itemAdditions.add(addition);
            }
        }
        itemAdditions.forEach(item -> item.registerItem(registry));
         */
        List<IItemAddition> itemAdditions = new ArrayList<>(getItemAdditions());
        Collections.sort(itemAdditions);
        itemAdditions.forEach(item -> item.registerItem(registry));
        getAdditions().forEach(IAddition::registerOreDicts);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        getAdditions().forEach(addition -> addition.registerModel(event));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        getItemAdditions().stream().filter(item -> item instanceof IColoredItem).forEach(item -> {
            IItemColor itemColor = (stack, tintIndex) -> {
                if(stack.getItem() instanceof IColoredItem) {
                    switch (tintIndex) {
                        case 0:
                            return Color.WHITE.getRGB();
                        case 1:
                            return ((IColoredItem) stack.getItem()).getOverlayColor();
                    }
                }
                return Color.BLACK.getRGB();
            };
            event.getItemColors().registerItemColorHandler(itemColor, (Item) item);
        });
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        getAdditions().forEach(addition -> addition.registerRecipe(registry));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerAspects(AspectRegistryEvent event) {
        long launchTime = System.currentTimeMillis();
        AspectEventProxy registry = event.register;
        //Ore Aspects register first so cluster aspects register correctly.
        OreAspects.getOreAspects().forEach((oreDict, aspectList) -> {
            if(!oreDict.isEmpty() && aspectList.size() > 0) {
                registry.registerObjectTag(oreDict, aspectList);
            }
        });
        Map<ItemStack, AspectList> aspectMap = new HashMap<>();
        RegistrarCM.getAdditions().forEach(addition -> addition.registerAspects(registry, aspectMap));
        aspectMap.forEach((stack, list) -> {
            if (!stack.isEmpty()) {
                registry.registerObjectTag(stack, list);
            }
        });
        LogHelper.info(String.format("Congrega Mystica aspect registry finished in approximately %dms", (int) (System.currentTimeMillis() - launchTime)));
    }

    public static List<IAddition> getAdditions() {
        return ADDITIONS;
    }

    public static List<IBlockAddition> getBlockAdditions() {
        return getAdditions().stream().filter(addition -> addition instanceof IBlockAddition)
                .map(addition -> (IBlockAddition) addition).collect(Collectors.toList());
    }

    public static List<IGolemAddition> getGolemAdditions() {
        return getAdditions().stream().filter(addition -> addition instanceof IGolemAddition)
                .map(addition -> (IGolemAddition) addition).collect(Collectors.toList());
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
