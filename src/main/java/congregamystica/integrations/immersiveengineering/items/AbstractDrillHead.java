package congregamystica.integrations.immersiveengineering.items;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.tool.IDrillHead;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.items.ItemDrill;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import blusunrize.immersiveengineering.common.util.Utils;
import com.google.common.collect.ImmutableList;
import congregamystica.CongregaMystica;
import congregamystica.api.IItemAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.immersiveengineering.util.DrillStats;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractDrillHead extends Item implements IDrillHead, IItemAddition, IProxy {
    public static final String HEAD_DAMAGE = "headDamage";

    protected DrillStats drillStats;

    public AbstractDrillHead(String unlocName, DrillStats drillStats) {
        this.setRegistryName(CongregaMystica.MOD_ID, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setMaxStackSize(1);
        this.drillStats = drillStats;
    }

    public DrillStats getDrillStats() {
        return this.drillStats;
    }

    @Override
    public boolean getIsRepairable(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return Utils.compareToOreName(repair, this.drillStats.repairMaterial);
    }

    @Override
    public boolean showDurabilityBar(@NotNull ItemStack stack) {
        return this.getHeadDamage(stack) > 0;
    }

    @Override
    public double getDurabilityForDisplay(@NotNull ItemStack stack) {
        return (double) this.getHeadDamage(stack) / (double) this.getMaximumHeadDamage(stack);
    }

    @Override
    public void addInformation(@NotNull ItemStack head, @Nullable World worldIn, List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(I18n.format(Lib.DESC_FLAVOUR + "drillhead.size", this.drillStats.drillSize, this.drillStats.drillDepth));
        tooltip.add(I18n.format(Lib.DESC_FLAVOUR + "drillhead.level", Utils.getHarvestLevelName(this.getMiningLevel(head))));
        tooltip.add(I18n.format(Lib.DESC_FLAVOUR + "drillhead.speed", Utils.formatDouble(this.getMiningSpeed(head), "0.###")));
        tooltip.add(I18n.format(Lib.DESC_FLAVOUR + "drillhead.damage", Utils.formatDouble(this.getAttackDamage(head), "0.###")));

        int maxDmg = getMaximumHeadDamage(head);
        int dmg = maxDmg-getHeadDamage(head);
        float quote = dmg / (float)maxDmg;
        String color = "" + (quote < .1 ? TextFormatting.RED : quote < .3 ? TextFormatting.GOLD: quote < .6 ? TextFormatting.YELLOW : TextFormatting.GREEN);
        String dura = color + (this.getMaximumHeadDamage(head) - this.getHeadDamage(head)) + "/" + this.getMaximumHeadDamage(head);
        tooltip.add(I18n.format(Lib.DESC_INFO + "durability", dura));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        this.drillStats.sprite = ApiUtils.getRegisterSprite(event.getMap(), this.drillStats.textureLoc);
    }

    //##########################################################
    // IDrillHead

    @Override
    public boolean beforeBlockbreak(ItemStack itemStack, ItemStack itemStack1, EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public void afterBlockbreak(ItemStack itemStack, ItemStack itemStack1, EntityPlayer entityPlayer) {}

    @Override
    public ImmutableList<BlockPos> getExtraBlocksDug(ItemStack itemStack, World world, EntityPlayer player, RayTraceResult trace) {
        int diameter = this.drillStats.drillSize;
        int depth = this.drillStats.drillDepth;

        EnumFacing side = trace.sideHit;
        BlockPos startPos = trace.getBlockPos();
        IBlockState state = world.getBlockState(startPos);
        Block block = state.getBlock();

        float maxHardness = 1.0F;
        if (block != null && !block.isAir(state, world, startPos)) {
            maxHardness = state.getPlayerRelativeBlockHardness(player, world, startPos) * 0.6F;
        }

        if (maxHardness < 0.0F) {
            maxHardness = 0.0F;
        }

        if (diameter % 2 == 0) {
            float hx = (float)trace.hitVec.x - (float)trace.getBlockPos().getX();
            float hy = (float)trace.hitVec.y - (float)trace.getBlockPos().getY();
            float hz = (float)trace.hitVec.z - (float)trace.getBlockPos().getZ();
            if (side.getAxis() == EnumFacing.Axis.Y && (double)hx < (double)0.5F || side.getAxis() == EnumFacing.Axis.Z && (double)hx < (double)0.5F) {
                startPos = startPos.add(-diameter / 2, 0, 0);
            }

            if (side.getAxis() != EnumFacing.Axis.Y && (double)hy < (double)0.5F) {
                startPos = startPos.add(0, -diameter / 2, 0);
            }

            if (side.getAxis() == EnumFacing.Axis.Y && (double)hz < (double)0.5F || side.getAxis() == EnumFacing.Axis.X && (double)hz < (double)0.5F) {
                startPos = startPos.add(0, 0, -diameter / 2);
            }
        } else {
            startPos = startPos.add(
                    -(side.getAxis() == EnumFacing.Axis.X ? 0 : diameter / 2),
                    -(side.getAxis() == EnumFacing.Axis.Y ? 0 : 1),
                    -(side.getAxis() == EnumFacing.Axis.Z ? 0 : diameter / 2));
        }

        ImmutableList.Builder<BlockPos> posBuilder = ImmutableList.builder();

        for(int dd = 0; dd < depth; ++dd) {
            for(int dw = 0; dw < diameter; ++dw) {
                for(int dh = 0; dh < diameter; ++dh) {
                    BlockPos pos = startPos.add(
                            side.getAxis() == EnumFacing.Axis.X ? dd : dw,
                            side.getAxis() == EnumFacing.Axis.Y ? dd : dh,
                            side.getAxis() == EnumFacing.Axis.Y ? dh : (side.getAxis() == EnumFacing.Axis.X ? dw : dd)
                    );
                    if (!pos.equals(trace.getBlockPos())) {
                        state = world.getBlockState(pos);
                        block = state.getBlock();
                        float h = state.getPlayerRelativeBlockHardness(player, world, pos);
                        boolean canHarvest = block.canHarvestBlock(world, pos, player);
                        boolean drillMat = ((ItemDrill) IEContent.itemDrill).isEffective(state.getMaterial());
                        boolean hardness = h > maxHardness;
                        if (canHarvest && drillMat && hardness) {
                            posBuilder.add(pos);
                        }
                    }
                }
            }
        }

        return posBuilder.build();
    }

    @Override
    public int getMiningLevel(ItemStack itemStack) {
        return this.drillStats.drillLevel;
    }

    @Override
    public float getMiningSpeed(ItemStack itemStack) {
        return this.drillStats.drillSpeed;
    }

    @Override
    public float getAttackDamage(ItemStack itemStack) {
        return this.drillStats.drillAttack;
    }

    @Override
    public int getHeadDamage(ItemStack itemStack) {
        return ItemNBTHelper.getInt(itemStack, HEAD_DAMAGE);
    }

    @Override
    public int getMaximumHeadDamage(ItemStack itemStack) {
        return this.drillStats.maxDamage;
    }

    @Override
    public void damageHead(ItemStack itemStack, int i) {
        ItemNBTHelper.setInt(itemStack, HEAD_DAMAGE, MathHelper.clamp(this.getHeadDamage(itemStack) + i, 0, this.getMaximumHeadDamage(itemStack)));
    }

    @Override
    public TextureAtlasSprite getDrillTexture(ItemStack itemStack, ItemStack itemStack1) {
        return this.drillStats.sprite;
    }

    //##########################################################
    // IItemAddition


    @Override
    public void preInitClient() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(this.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, loc);
    }

    @Override
    public abstract boolean isEnabled();
}
