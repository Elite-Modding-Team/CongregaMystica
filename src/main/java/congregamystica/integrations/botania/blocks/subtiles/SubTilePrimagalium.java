package congregamystica.integrations.botania.blocks.subtiles;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.botania.BotaniaCM;
import congregamystica.integrations.botania.lexicon.LexiconEntryCM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.resources.ItemCrystalEssence;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.crafting.ModPetalRecipes;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class SubTilePrimagalium extends SubTileGenerating implements IAddition, IProxy {
    public static final int MANA_PRODUCED = ConfigHandlerCM.botania.primalFlowers.primagaliumManaProduced;
    public static LexiconEntry PRIMAGALIUM_ENTRY;
    public static RecipePetals PRIMAGALIUM_RECIPE;
    private static final Aspect[] PRIMAL_ASPECTS = new Aspect[] {
            Aspect.WATER,
            Aspect.FIRE,
            Aspect.EARTH,
            Aspect.AIR,
            Aspect.ORDER,
            Aspect.ENTROPY
    };

    private int nextAspect;

    public SubTilePrimagalium() {
        this.nextAspect = this.getNextAspect();
    }

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.nextAspect = cmp.getInteger("nextAspect");
    }

    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger("nextAspect", this.nextAspect);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.getWorld().isRemote) {
            AxisAlignedBB area = new AxisAlignedBB(this.getPos().add(-1, -1, -1), this.getPos().add(2, 2, 2));
            List<EntityItem> items = this.getWorld().getEntitiesWithinAABB(EntityItem.class, area, this::isEntityValid);
            for(EntityItem item : items) {
                ItemStack stack = item.getItem();
                if(this.doesAspectMatch(stack)) {
                    this.mana = Math.min(this.getMaxMana(), this.mana + MANA_PRODUCED);
                    this.nextAspect = this.getNextAspect();
                    this.sync();
                    ((WorldServer) this.getWorld()).spawnParticle(
                            EnumParticleTypes.ITEM_CRACK,
                            false,
                            item.posX, item.posY, item.posZ,
                            20,
                            0.1, 0.1, 0.1,
                            0.05,
                            Item.getIdFromItem(stack.getItem()), stack.getItemDamage()
                    );
                }
                item.setDead();
            }
        }
    }

    public boolean isEntityValid(EntityItem entityItem) {
        ItemStack stack = entityItem.getItem();
        return entityItem.isEntityAlive() && entityItem.age >= this.getSlowdownFactor() && !stack.isEmpty() && stack.getItem() instanceof ItemCrystalEssence;
    }

    public boolean doesAspectMatch(ItemStack stack) {
        if(!stack.isEmpty() && stack.getItem() instanceof ItemCrystalEssence) {
            AspectList aspectList = ((ItemCrystalEssence) stack.getItem()).getAspects(stack);
            if(aspectList != null && aspectList.size() > 0) {
                return aspectList.getAspects()[0] == this.getAspect();
            }
        }
        return false;
    }

    public Aspect getAspect() {
        return PRIMAL_ASPECTS[this.nextAspect];
    }

    public int getNextAspect() {
        return (this.nextAspect + 1) % PRIMAL_ASPECTS.length;
    }

    @Override
    public int getMaxMana() {
        return 16000;
    }

    @Override
    public int getColor() {
        return Color.HSBtoRGB((float) this.ticksExisted / 100.0F, 1.0F, 1.0F);
    }

    @Override
    public LexiconEntry getEntry() {
        return PRIMAGALIUM_ENTRY;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(this.toBlockPos(), 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUD(Minecraft mc, ScaledResolution res) {
        super.renderHUD(mc, res);
        Aspect aspect = this.getAspect();
        ItemStack crystalStack = ThaumcraftApiHelper.makeCrystal(aspect);
        int color = aspect.getColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        if(!crystalStack.isEmpty()) {
            String stackName = crystalStack.getDisplayName();
            int width = 16 + mc.fontRenderer.getStringWidth(stackName) / 2;
            int x = res.getScaledWidth() / 2 - width;
            int y = res.getScaledHeight() / 2 + 30;
            mc.fontRenderer.drawStringWithShadow(stackName, x + 20, y + 5, color);
            RenderHelper.enableStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(crystalStack, x, y);
            RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    //##########################################################
    // IAddition

    @Override
    public void preInit() {
        BotaniaAPI.addSubTileToCreativeMenu(BotaniaCM.PRIMAGALIUM);
        BotaniaAPI.registerSubTile(BotaniaCM.PRIMAGALIUM, SubTilePrimagalium.class);
    }

    @Override
    public void postInit() {
        PRIMAGALIUM_ENTRY = new LexiconEntryCM(BotaniaCM.PRIMAGALIUM, BotaniaAPI.categoryGenerationFlowers);
        PRIMAGALIUM_ENTRY.setIcon(ItemBlockSpecialFlower.ofType(BotaniaCM.PRIMAGALIUM));
        PRIMAGALIUM_ENTRY.setLexiconPages(
                new PageText("0"),
                new PageText("1"),
                new PagePetalRecipe<>("2", PRIMAGALIUM_RECIPE)
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(ModelRegistryEvent event) {
        BotaniaAPIClient.registerSubtileModel(SubTilePrimagalium.class, new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, BotaniaCM.PRIMAGALIUM), "normal"));
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        PRIMAGALIUM_RECIPE = new RecipePetals(ItemBlockSpecialFlower.ofType(BotaniaCM.PRIMAGALIUM),
                ModPetalRecipes.lightBlue,
                ModPetalRecipes.red,
                ModPetalRecipes.green,
                ModPetalRecipes.brown,
                ModPetalRecipes.black,
                ModPetalRecipes.white,
                new ItemStack(ModItems.rune, 1, 0),
                new ItemStack(ModItems.rune, 1, 1),
                new ItemStack(ModItems.rune, 1, 2),
                new ItemStack(ModItems.rune, 1, 3)
        );
        BotaniaAPI.petalRecipes.add(PRIMAGALIUM_RECIPE);
    }

    @Override
    public void registerResearchLocation() {
        //Primal Bloom handles the research location registry.
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        AspectList flowerAspects = new AspectList()
                .add(Aspect.PLANT, 15)
                .add(Aspect.VOID, 20)
                .add(Aspect.DESIRE, 10)
                .add(Aspect.BEAST, 5)
                .add(Aspect.MAGIC, 5);
        aspectMap.put(ItemBlockSpecialFlower.ofType(BotaniaCM.PRIMAGALIUM), flowerAspects);
        AspectList floatingAspects =  new AspectList().add(flowerAspects).add(Aspect.FLIGHT, 5).add(Aspect.LIGHT, 5);
        aspectMap.put(ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), BotaniaCM.PRIMAGALIUM), floatingAspects);
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.botania.primalFlowers.enable;
    }
}
