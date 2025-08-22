package congregamystica.integrations.botania.blocks.subtiles;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.botania.BotaniaCM;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;

// TODO: Should clean up nearby flux goo around it
public class SubTileTainthistle extends SubTileFunctional implements IAddition, IProxy {
    private static final int MANA_COST = 300;
    private static final int RANGE = 2;
    public static LexiconEntry TAINTHISTLE_ENTRY;

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
    }

    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
    }

    @Override
    public int getMaxMana() {
        return MANA_COST;
    }

    @Override
    public int getColor() {
        return 0x4D00FF;
    }

    @Override
    public LexiconEntry getEntry() {
        return TAINTHISTLE_ENTRY;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Circle(this.toBlockPos(), RANGE);
    }

    //##########################################################
    // IAddition

    @Override
    public void preInit() {
        BotaniaAPI.addSubTileToCreativeMenu(BotaniaCM.TAINTHISTLE);
        BotaniaAPI.registerSubTile(BotaniaCM.TAINTHISTLE, SubTileTainthistle.class);
    }

    @Override
    public void postInit() {
        SubTileTainthistle.TAINTHISTLE_ENTRY = new BasicLexiconEntry(BotaniaCM.TAINTHISTLE, BotaniaAPI.categoryFunctionalFlowers);
        SubTileTainthistle.TAINTHISTLE_ENTRY.setIcon(ItemBlockSpecialFlower.ofType(BotaniaCM.TAINTHISTLE));
        SubTileTainthistle.TAINTHISTLE_ENTRY.setLexiconPages(
                new PageText("0")
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        BotaniaAPIClient.registerSubtileModel(SubTileTainthistle.class, new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, BotaniaCM.TAINTHISTLE), "normal"));
    }

    @Override
    public boolean isEnabled() {
        //TODO: Add config disable
        return true;
    }
}
