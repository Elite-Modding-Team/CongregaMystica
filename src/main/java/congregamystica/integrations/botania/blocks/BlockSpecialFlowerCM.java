package congregamystica.integrations.botania.blocks;

import org.jetbrains.annotations.NotNull;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.botania.blocks.subtiles.SubTileWhisperweed;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.common.block.BlockSpecialFlower;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;

// TODO: Whisperweed will need to depend on Thaumcraft Fix to filter the research category points
public class BlockSpecialFlowerCM extends BlockSpecialFlower implements IAddition, IProxy {
    public static final BlockSpecialFlowerCM BLOCK_SPECIAL_FLOWER = new BlockSpecialFlowerCM();

    public BlockSpecialFlowerCM() {
        //isAuromeriaEnabled = ModIds.thaumcraft.isLoaded && SubTileAuromeria.AUROMERIA.isEnabled();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, @NotNull NonNullList<ItemStack> stacks) {
        stacks.add(ItemBlockSpecialFlower.ofType(SubTileWhisperweed.NAME));
        stacks.add(ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), SubTileWhisperweed.NAME));
    }

    @Override
    public void postInit() {
        SubTileWhisperweed.WHISPERWEED_ENTRY = new BasicLexiconEntry("whisperweed", BotaniaAPI.categoryFunctionalFlowers);
        SubTileWhisperweed.WHISPERWEED_ENTRY.setLexiconPages(
                new PageText("0"));
    }
    
    //##########################################################
    // IItemAddition
    
    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //SubTileAuromeria.auromeriaRecipe = new RecipePetals(ItemBlockSpecialFlower.ofType(SubTileAuromeria.NAME), ModPetalRecipes.green, ModPetalRecipes.red, ModPetalRecipes.red, ModPetalRecipes.purple, "runeManaB", new ItemStack(ItemsTC.visResonator), "redstoneRoot");
        //BotaniaAPI.registerPetalRecipe(SubTileAuromeria.auromeriaRecipe.getOutput(), SubTileAuromeria.auromeriaRecipe.getInputs().toArray());
    }
    
    @Override
    public void registerModel(ModelRegistryEvent event) {
        BotaniaAPIClient.registerSubtileModel(SubTileWhisperweed.class, new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, SubTileWhisperweed.NAME), "normal"));
    }
    
    @Override
    public void registerResearchLocation() {
        //Register any associated research here
    }
    
    @Override
    public boolean isEnabled() {
        BotaniaAPI.addSubTileToCreativeMenu(SubTileWhisperweed.NAME);
        BotaniaAPI.registerSubTile(SubTileWhisperweed.NAME, SubTileWhisperweed.class);
        return true;
    }
}
