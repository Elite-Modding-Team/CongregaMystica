package congregamystica.integrations.botania.blocks.subtiles;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.botania.BotaniaCM;
import congregamystica.utils.libs.ModIds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXPollute;
import thecodex6824.thaumcraftfix.api.research.ResearchCategoryTheorycraftFilter;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;

import java.awt.*;
import java.util.Map;

public class SubTileWhisperweed extends SubTileFunctional implements IAddition, IProxy {
    private static final int MANA_COST = 600;
    //TODO: Increase PROG_REQ so it is more than just 1 brain per use.
    private static final int PROG_REQ = 1;
    private static final int RANGE = 2;
    public static LexiconEntry WHISPERWEED_ENTRY;

    public int progress = 0;

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        boolean did = false;
        if(this.supertile.getWorld().isRemote) {
            //whisper to nearby players
        } else {
            if (this.redstoneSignal <= 0 && this.progress < PROG_REQ && this.mana >= MANA_COST) {
                //Consuming Mana to eat Zombie Brains
                if (this.ticksExisted % 180 == 0) {
                    for (EntityItem entityItem : this.supertile.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.getPos()).grow(RANGE))) {
                        if (entityItem.getItem().getItem() == ItemsTC.brain && entityItem.isEntityAlive() && entityItem.getItem().getCount() > 0) {
                            this.progress++;
                            this.mana -= MANA_COST;
                            ItemStack stack = entityItem.getItem();
                            this.supertile.getWorld().playSound(null, this.getPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 0.5F, 1.0F);
                            Vec3d offset = this.getWorld().getBlockState(this.getPos()).getOffset(this.getWorld(), this.getPos()).add(0.4, 0.6, 0.4);
                            ((WorldServer)this.supertile.getWorld()).spawnParticle(EnumParticleTypes.ITEM_CRACK,
                                    (double)this.supertile.getPos().getX() + offset.x,
                                    (double)this.supertile.getPos().getY() + offset.y,
                                    (double)this.supertile.getPos().getZ() + offset.z,
                                    10, 0.1, 0.1, 0.1, 0.03, Item.getIdFromItem(ItemsTC.brain));
                            stack.shrink(1);
                            did = true;
                            break;
                        }
                    }
                }
            }
        }
        if(did) {
            this.supertile.getWorld().updateComparatorOutputLevel(this.getPos(), this.supertile.getBlockType());
            this.sync();
        }
    }

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.progress = cmp.getInteger("progress");
    }

    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger("progress", this.progress);
    }

    @Override
    public void renderHUD(Minecraft mc, ScaledResolution res) {
        super.renderHUD(mc, res);
        //TODO: Adjust progress bar color and item here
        int color = Aspect.MIND.getColor();
        this.drawComplexProgressHUD(color, res, new ItemStack(ItemsTC.thaumonomicon));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);
        if(heldItem.getItem() == ModItems.twigWand) {
            return false;
        }
        if(!(player instanceof FakePlayer) && this.progress >= PROG_REQ) {
            ResearchCategory[] categories = ResearchCategoryTheorycraftFilter.getAllowedTheorycraftCategories().toArray(new ResearchCategory[0]);
            int theoryProgress = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();

            //10% chance to add between 1 and 3 warp
            if(!world.isRemote && world.rand.nextInt(10) == 0) {
                ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1 + player.world.rand.nextInt(3), IPlayerWarp.EnumWarpType.TEMPORARY);
                for(int i = 0; i < 40; i++) {
                    PacketHandler.INSTANCE.sendToAllAround(new PacketFXPollute(pos, 2f), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 12.0));
                }
            }
            //TODO: Adjust sound effect volume and pitch
            world.playSound(player, pos, SoundsTC.whispers, SoundCategory.BLOCKS, 0.6f, 1.0f);

            //TODO: Randomize and increase the research given.
            ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.THEORY,
                    categories[player.getRNG().nextInt(categories.length)],
                    MathHelper.getInt(player.getRNG(), theoryProgress / 12, theoryProgress / 6));

            this.progress -= PROG_REQ;
            this.supertile.getWorld().updateComparatorOutputLevel(this.getPos(), this.supertile.getBlockType());
            this.sync();
            return true;
        }
        return false;
    }

    @Override
    public int getComparatorInputOverride() {
        return this.progress > 0 ? Math.max(1, (int) (15f * (float) this.progress / (float) PROG_REQ)) : 0;
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

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Circle(this.toBlockPos(), RANGE);
    }

    public void renderProgressBar(int x, int y, int color, float alpha) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
        mc.renderEngine.bindTexture(HUDHandler.manaBar);
        RenderHelper.drawTexturedModalRect(x, y, 0.0f, 0, 0, 102, 5);
        int progressPercentage = MathHelper.clamp((int) ((double) this.progress / (double) PROG_REQ * 100.0), 0, 100);
        RenderHelper.drawTexturedModalRect(x + 1, y + 1, 0.0f, 0, 5, 100, 3);
        Color color_ = new Color(color);
        GL11.glColor4ub((byte) color_.getRed(), (byte) color_.getGreen(), (byte) color_.getBlue(), (byte) ((int) (255.0F * alpha)));
        RenderHelper.drawTexturedModalRect(x + 1, y + 1, 0F, 0, 5, Math.min(100, progressPercentage), 3);
        GL11.glColor4ub((byte) -1, (byte) -1, (byte) -1, (byte) -1);
    }

    public void drawSimpleProgressHUD(int color, ScaledResolution res) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        Minecraft mc = Minecraft.getMinecraft();
        int x = res.getScaledWidth() / 2 - 51;
        int y = res.getScaledHeight() / 2 + 36;
        renderProgressBar(x, y, color, this.progress < 0 ? 0.5F : 1.0F);
        if (this.progress < 0) {
            String text = I18n.format("botaniamisc.statusUnknown");
            x = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(text) / 2;
            --y;
            mc.fontRenderer.drawString(text, x, y, color);
        }
        GlStateManager.disableBlend();
    }

    public void drawComplexProgressHUD(int color, ScaledResolution res, ItemStack bindDisplay) {
        this.drawSimpleProgressHUD(color, res);
        Minecraft mc = Minecraft.getMinecraft();
        int x = res.getScaledWidth() / 2 + 55;
        int y = res.getScaledHeight() / 2 + 30;
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        mc.getRenderItem().renderItemAndEffectIntoGUI(bindDisplay, x, y);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }

    //##########################################################
    // IAddition

    @Override
    public void preInit() {
        BotaniaAPI.addSubTileToCreativeMenu(BotaniaCM.WHISPERWEED);
        BotaniaAPI.registerSubTile(BotaniaCM.WHISPERWEED, SubTileWhisperweed.class);
    }

    @Override
    public void postInit() {
        //TODO: Write botania research
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
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //TODO: Add recipe
    }

    @Override
    public void registerResearchLocation() {
        //TODO: Add research
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        //TODO: Flower aspects (check out Botania to see how this works)
    }

    @Override
    public boolean isEnabled() {
        //TODO: Add config disable
        return ModIds.thaumcraft_fix.isLoaded;
    }
}
