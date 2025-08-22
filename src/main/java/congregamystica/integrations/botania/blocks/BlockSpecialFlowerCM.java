package congregamystica.integrations.botania.blocks;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.block.BlockSpecialFlower;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import java.util.Map;

public class BlockSpecialFlowerCM<T extends SubTileEntity & IAddition & IProxy> extends BlockSpecialFlower implements IAddition, IProxy {
    private final T subTile;
    private final String name;

    public BlockSpecialFlowerCM(T subTile, String subTileName) {
        this.subTile = subTile;
        this.name = subTileName;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, @NotNull NonNullList<ItemStack> stacks) {
        if (this.isEnabled()) {
            stacks.add(ItemBlockSpecialFlower.ofType(this.name));
            stacks.add(ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), this.name));
        }
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        this.subTile.preInit();
    }

    @Override
    public void preInitClient() {
        this.subTile.preInitClient();
    }

    @Override
    public void init() {
        this.subTile.init();
    }

    @Override
    public void initClient() {
        this.subTile.initClient();
    }

    @Override
    public void postInit() {
        this.subTile.postInit();
    }

    @Override
    public void postInitClient() {
        this.subTile.postInitClient();
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        this.subTile.registerRecipe(registry);
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        this.subTile.registerModel(event);
    }

    @Override
    public void registerResearchLocation() {
        this.subTile.registerResearchLocation();
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        this.subTile.registerAspects(registry, aspectMap);
    }

    @Override
    public void registerOreDicts() {
        this.subTile.registerOreDicts();
    }

    @Override
    public boolean isEnabled() {
        return this.subTile.isEnabled();
    }
}
