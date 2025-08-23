package congregamystica.integrations.congregamystica.items;

import congregamystica.utils.helpers.PlayerHelper;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemMimicForkRanged extends ItemMimicFork {
    public ItemMimicForkRanged() {
        super("mimic_fork_ranged");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World world, @NotNull List<String> tooltip, @NotNull ITooltipFlag flag) {
        int tone = this.getTone(stack);
        byte note = this.getNote(stack);
        if(stack.hasTagCompound() && stack.getTagCompound() != null) {
            BlockPos pos = this.getPosition(stack);
            if(pos != null){
                String dimension = WordUtils.capitalizeFully(this.getDimesionString(stack).replace("_", " "));
                tooltip.add(I18n.format(StringHelper.getTranslationKey("mimic_fork", "tooltip", "dimension"), dimension));
                tooltip.add(I18n.format(StringHelper.getTranslationKey("mimic_fork", "tooltip", "position"), pos.getX(), pos.getY(), pos.getZ()));
            }
        }
        tooltip.add(I18n.format("mimic_fork.instrument", I18n.format("mimic_fork.instrument." + getInstrumentNameFromInt(tone))));
        tooltip.add(I18n.format("mimic_fork.note", note));
    }

    @Override
    public @NotNull EnumActionResult onItemUseFirst(EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing side, float hitX, float hitY, float hitZ, @NotNull EnumHand hand)  {
        EnumActionResult actionResult = super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        if(actionResult != EnumActionResult.SUCCESS && player.isSneaking() && !world.isAirBlock(pos)) {
            ItemStack heldStack = player.getHeldItem(hand);
            this.setPositionAndDimension(heldStack, world, pos);
            return EnumActionResult.SUCCESS;
        }
        return actionResult;
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        if(player.isSneaking()) {
            RayTraceResult trace = PlayerHelper.rayTrace(player, 0);
            if(trace == null || trace.typeOfHit == RayTraceResult.Type.MISS) {
                this.clearPositionAndDimension(heldStack);
                return ActionResult.newResult(EnumActionResult.SUCCESS, heldStack);
            }
        } else {
            World targetWorld = this.getDimensionWorld(heldStack);
            BlockPos targetPos = this.getPosition(heldStack);
            if (targetWorld != null && targetPos != null && !player.isSneaking()) {
                int note = getNote(heldStack);
                int tone = getTone(heldStack);
                if(this.playSoundAt(targetWorld, targetPos, note, tone)) {
                    player.swingArm(hand);
                    return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    public void setPositionAndDimension(ItemStack stack, World world, BlockPos pos) {
        this.setDimension(stack, world.provider.getDimension());
        this.setPosition(stack, pos);
    }

    public void clearPositionAndDimension(ItemStack stack) {
        if(stack.getTagCompound() != null) {
            stack.getTagCompound().removeTag("dim");
            stack.getTagCompound().removeTag("pos");
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void setDimension(ItemStack stack, int dimension) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("dim", dimension);
    }

    public int getDimension(ItemStack stack) {
        if(stack.hasTagCompound() && stack.getTagCompound() != null && stack.getTagCompound().hasKey("dim")) {
            return stack.getTagCompound().getInteger("dim");
        }
        return 0;
    }

    @Nullable
    public World getDimensionWorld(ItemStack stack) {
        int dimId = this.getDimension(stack);
        World world = DimensionManager.getWorld(dimId);
        if(world == null) {
            // If the dimension isn't loaded (e.g. the Nether when nobody's there), then force-load it and try again
            DimensionManager.initDimension(dimId);
            world = DimensionManager.getWorld(dimId);
        }
        return world;
    }

    public String getDimesionString(ItemStack stack) {
        int dimId = this.getDimension(stack);
        return StringHelper.getDimensionName(dimId);
    }

    @SuppressWarnings("ConstantConditions")
    public void setPosition(ItemStack stack, BlockPos pos) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setLong("pos", pos.toLong());
    }

    @Nullable
    public BlockPos getPosition(ItemStack stack) {
        if(stack.hasTagCompound() && stack.getTagCompound() != null && stack.getTagCompound().hasKey("pos")) {
            return BlockPos.fromLong(stack.getTagCompound().getLong("pos"));
        }
        return null;
    }

    //##########################################################
    // IItemAddition

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
        //TODO: Aspects may not be needed depending on the crafting recipe
    }
}
