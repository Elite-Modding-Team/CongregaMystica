package congregamystica.integrations.botania.blocks.subtiles;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.research.ResearchCategory;
import thecodex6824.thaumcraftfix.api.research.ResearchCategoryTheorycraftFilter;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileFunctional;

// TODO: Need to see if this is balanced properly
public class SubTileWhisperweed extends SubTileFunctional {
    public static final SubTileWhisperweed WHISPERWEED = new SubTileWhisperweed();
    public static final String NAME = "whisperweed";
    public static LexiconEntry WHISPERWEED_ENTRY;

    public static int manaCost = 6000;
    public static int range = 3;

    ResearchCategory[] rc = ResearchCategoryTheorycraftFilter.getAllowedTheorycraftCategories().toArray(new ResearchCategory[0]);

    int tProg = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (redstoneSignal > 0)
            return;

        if (!supertile.getWorld().isRemote && mana >= manaCost && this.ticksExisted % 300 == 0) {
            List<EntityPlayer> players = supertile.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(supertile.getPos().getX() - range, supertile.getPos().getY() - range, supertile.getPos().getZ() - range, supertile.getPos().getX() + range + 1, supertile.getPos().getY() + range + 1, supertile.getPos().getZ() + range + 1));
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
                mana -= manaCost;
            }

        }
    }

    @Override
    public int getColor() {
        return 0x745380;
    }

    @Override
    public int getMaxMana() {
        return manaCost;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public LexiconEntry getEntry() {
        return WHISPERWEED_ENTRY;
    }
}
