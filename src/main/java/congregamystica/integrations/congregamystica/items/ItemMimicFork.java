package congregamystica.integrations.congregamystica.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.AbstractItemAddition;
import congregamystica.api.util.EnumSortType;
import congregamystica.network.PacketHandlerCM;
import congregamystica.network.packets.PacketParticleToClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent.Instrument;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.tiles.devices.TileArcaneEar;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemMimicFork extends AbstractItemAddition {
    public ItemMimicFork(String unlocName) {
        super(unlocName);
        this.setMaxStackSize(1);
    }

    public ItemMimicFork() {
        this("mimic_fork");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World world, @NotNull List<String> tooltip, @NotNull ITooltipFlag flag) {
        int tone = this.getTone(stack);
        byte note = this.getNote(stack);
        tooltip.add(I18n.format("mimic_fork.instrument", I18n.format("mimic_fork.instrument." + getInstrumentNameFromInt(tone))));
        tooltip.add(I18n.format("mimic_fork.note", note));
    }

    @Override
    public @NotNull EnumActionResult onItemUseFirst(EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing side, float hitX, float hitY, float hitZ, @NotNull EnumHand hand)  {
        ItemStack heldStack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);

        if(player.isSneaking()) {
            if (tile instanceof TileEntityNote) {
                IBlockState down = world.getBlockState(pos.down());
                this.setNote(heldStack, ((TileEntityNote) tile).note);
                this.setTone(heldStack, getToneFromState(down));
                return EnumActionResult.SUCCESS;
            } else if (tile instanceof TileArcaneEar) {
                this.setNote(heldStack, ((TileArcaneEar) tile).note);
                this.setTone(heldStack, ((TileArcaneEar) tile).tone);
                return EnumActionResult.SUCCESS;
            }
        } else if(heldStack.getItem() == this) {
            if (tile instanceof TileArcaneEar) {
                ((TileArcaneEar) tile).note = this.getNote(heldStack);
                tile.markDirty();
                return EnumActionResult.SUCCESS;
            }
            if (tile instanceof TileEntityNote) {
                ((TileEntityNote) tile).note = this.getNote(heldStack);
                tile.markDirty();
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand hand) {
        if(!player.isSneaking()) {
            ItemStack heldStack = player.getHeldItem(hand);
            int note = getNote(heldStack);
            int tone = getTone(heldStack);
            if(this.playSoundAt(world, player.getPosition(), note, tone)) {
                player.swingArm(hand);
                return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    public boolean playSoundAt(World world, BlockPos pos, int note, int tone) {
        NoteBlockEvent.Play event = new NoteBlockEvent.Play(world, pos, null, note, tone);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            pos = pos.up();
            float pitch = (float) Math.pow(2.0D, (note - 12) / 12.0D);
            world.playSound(null, pos, getInstrumentSound(event.getInstrument()), SoundCategory.PLAYERS, 3.0F, pitch);
            if(world.isRemote) {
                world.spawnParticle(EnumParticleTypes.NOTE,
                        pos.getX() + (0.5D * (pos.getX() < 0 ? -1 : 1)),
                        pos.getY() + 0.2D,
                        pos.getZ() + (0.5D * (pos.getZ() < 0 ? -1 : 1)),
                        (double) note / 24.0D,
                        0.0D,
                        0.0D);
            } else {
                //TODO: Figure out why this fires twice
                PacketHandlerCM.INSTANCE.sendToAllAround(
                        new PacketParticleToClient(
                                EnumParticleTypes.NOTE,
                                pos.getX() + 0.5D,
                                pos.getY() + 0.2D,
                                pos.getZ() + 0.5D,
                                (double) note / 24.0D,
                                0.0D,
                                0.0D
                        ),
                        new NetworkRegistry.TargetPoint(
                                world.provider.getDimension(),
                                pos.getX() + 0.5D,
                                pos.getY() + 0.2D,
                                pos.getZ() + 0.5D,
                                32
                        ));
            }
            return true;
        }
        return false;
    }

    public int getToneFromState(IBlockState state) {
        Material material = state.getMaterial();
        int i = 0;

        if (material == Material.ROCK) {
            i = 1;
        } else if (material == Material.SAND) {
            i = 2;
        } else if (material == Material.GLASS) {
            i = 3;
        } else if (material == Material.WOOD) {
            i = 4;
        }

        Block block = state.getBlock();
        if (block == Blocks.CLAY) {
            i = 5;
        } else if (block == Blocks.GOLD_BLOCK) {
            i = 6;
        } else if (block == Blocks.WOOL) {
            i = 7;
        } else if (block == Blocks.PACKED_ICE) {
            i = 8;
        } else if (block == Blocks.BONE_BLOCK) {
            i = 9;
        }

        return i;
    }

    public String getInstrumentNameFromInt(int toneIn) {
        return Instrument.values()[toneIn].name().toLowerCase();
    }

    public SoundEvent getInstrumentSound(Instrument instrumentIn) {
        switch (instrumentIn) {
            case BELL:
                return SoundEvents.BLOCK_NOTE_BELL;
            case CHIME:
                return SoundEvents.BLOCK_NOTE_CHIME;
            case FLUTE:
                return SoundEvents.BLOCK_NOTE_FLUTE;
            case PIANO:
                return SoundEvents.BLOCK_NOTE_HARP;
            case SNARE:
                return SoundEvents.BLOCK_NOTE_SNARE;
            case CLICKS:
                return SoundEvents.BLOCK_NOTE_HAT;
            case GUITAR:
                return SoundEvents.BLOCK_NOTE_GUITAR;
            case BASSDRUM:
                return SoundEvents.BLOCK_NOTE_BASEDRUM;
            case XYLOPHONE:
                return SoundEvents.BLOCK_NOTE_XYLOPHONE;
            default:
                return SoundEvents.BLOCK_NOTE_BASS;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void setNote(ItemStack stack, byte note) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setByte("note", note);
    }

    public byte getNote(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound() != null ? stack.getTagCompound().getByte("note") : 0;
    }

    @SuppressWarnings("ConstantConditions")
    public void setTone(ItemStack stack, int tone) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("tone", tone);
    }

    public int getTone(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound() != null ? stack.getTagCompound().getByte("tone") : 0;
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation(CongregaMystica.MOD_ID, "mimic_fork_fake"), new ShapedOreRecipe(
                new ResourceLocation(""),
                new ItemStack(this),
                "I I",
                "IMI",
                " S ",
                'I', "ingotIron",
                'M', new ItemStack(ItemsTC.mechanismSimple),
                'S', "stickWood"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        AspectList aspectList = new AspectList()
                .add(Aspect.METAL, 15)
                .add(Aspect.SENSES, 10)
                .add(Aspect.AIR, 10)
                .add(Aspect.TOOL, 8);
        aspectMap.put(new ItemStack(this), aspectList);
    }

    @Override
    public EnumSortType getRegistryOrderType() {
        return EnumSortType.CONGREGA_MYSTICA;
    }

    @Override
    public boolean isEnabled() {
        //Mimic fork is always enabled
        return true;
    }
}
