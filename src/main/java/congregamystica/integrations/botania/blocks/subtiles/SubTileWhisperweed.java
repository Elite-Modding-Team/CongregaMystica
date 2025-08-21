package congregamystica.integrations.botania.blocks.subtiles;

import java.util.List;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.botania.BotaniaCM;
import congregamystica.utils.libs.ModIds;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.research.ResearchCategory;
import thecodex6824.thaumcraftfix.api.research.ResearchCategoryTheorycraftFilter;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;

// TODO: Need to see if this is balanced properly
public class SubTileWhisperweed extends SubTileFunctional implements IAddition, IProxy {
    private static final int MANA_COST = 6000;
    private static final int RANGE = 3;
    public static LexiconEntry WHISPERWEED_ENTRY;

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        ResearchCategory[] rc = ResearchCategoryTheorycraftFilter.getAllowedTheorycraftCategories().toArray(new ResearchCategory[0]);
        int tProg = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();

        if (redstoneSignal > 0)
            return;

        if (!supertile.getWorld().isRemote && mana >= MANA_COST && this.ticksExisted % 300 == 0) {
            List<EntityPlayer> players = supertile.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(supertile.getPos().getX() - RANGE, supertile.getPos().getY() - RANGE, supertile.getPos().getZ() - RANGE, supertile.getPos().getX() + RANGE + 1, supertile.getPos().getY() + RANGE + 1, supertile.getPos().getZ() + RANGE + 1));
            if (players.size() > 0) {
                EntityPlayer player = players.get(supertile.getWorld().rand.nextInt(players.size()));
                int amt = 1 + player.world.rand.nextInt(3);
                if (player.world.rand.nextInt(10) < 2) {
                    amt += 1 + player.world.rand.nextInt(3);
                    ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1 + player.world.rand.nextInt(5), IPlayerWarp.EnumWarpType.TEMPORARY);
                }

                for (int a = 0; a < amt; ++a) {
                    ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[player.getRNG().nextInt(rc.length)], MathHelper.getInt(player.getRNG(), tProg / 12, tProg / 6));
                }
                mana -= MANA_COST;
            }

        }
    }

    @Override
    public int getMaxMana() {
        return MANA_COST;
    }

    @Override
    public int getColor() {
        return 0x745380;
    }

    @Override
    public LexiconEntry getEntry() {
        return WHISPERWEED_ENTRY;
    }

    //##########################################################
    // IItemAddition

    @Override
    public void preInit() {
        BotaniaAPI.addSubTileToCreativeMenu(BotaniaCM.WHISPERWEED);
        BotaniaAPI.registerSubTile(BotaniaCM.WHISPERWEED, SubTileWhisperweed.class);
    }

    @Override
    public void postInit() {
        SubTileWhisperweed.WHISPERWEED_ENTRY = new BasicLexiconEntry(BotaniaCM.WHISPERWEED, BotaniaAPI.categoryFunctionalFlowers);
        SubTileWhisperweed.WHISPERWEED_ENTRY.setIcon(ItemBlockSpecialFlower.ofType(BotaniaCM.WHISPERWEED));
        SubTileWhisperweed.WHISPERWEED_ENTRY.setLexiconPages(
                new PageText("0")
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        BotaniaAPIClient.registerSubtileModel(SubTileWhisperweed.class, new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, BotaniaCM.WHISPERWEED), "normal"));
    }

    @Override
    public boolean isEnabled() {
        return ModIds.thaumcraft_fix.isLoaded;
    }
}
