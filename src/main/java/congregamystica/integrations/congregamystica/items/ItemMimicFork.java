package congregamystica.integrations.congregamystica.items;

import congregamystica.CongregaMystica;
import congregamystica.api.IItemAddition;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent.Instrument;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.tiles.devices.TileArcaneEar;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ItemMimicFork extends Item implements IItemAddition {
    public ItemMimicFork() {
        this.setRegistryName(CongregaMystica.MOD_ID, "mimic_fork");
        this.setTranslationKey(this.getRegistryName().toString());
        this.setMaxStackSize(1);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //Register any recipes associated with the item here
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
    	ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public void registerResearchLocation() {
        //Register any associated research here
    }

    @Override
    public Map<ItemStack, AspectList> registerAspects() {
        return IItemAddition.super.registerAspects();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        super.onCreated(stack, world, player);

        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setByte("note", (byte) 1);
        tagCompound.setInteger("tone", 1);

        stack.setTagCompound(tagCompound);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        if (stack.hasTagCompound() && stack.getTagCompound() != null) {
            NBTTagCompound tagCompound = stack.getTagCompound();

            if (tagCompound.hasKey("tone"))
                tooltip.add(I18n.format("mimic_fork.instrument", I18n.format("mimic_fork.instrument." + getInstrumentNameFromInt(tagCompound.getInteger("tone")))));
            if (tagCompound.hasKey("note")) {
                tooltip.add(I18n.format("mimic_fork.note", tagCompound.getByte("note")));
            }
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        NBTTagCompound tagCompound = heldStack.getTagCompound();

        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            heldStack.setTagCompound(tagCompound);
        }

        TileEntity tile;
        tile = world.getTileEntity(pos);

        if (!world.isRemote && player.isSneaking()) {

            if (tile != null && tile instanceof TileEntityNote) {
                IBlockState iblockstate = world.getBlockState(pos.down());

                tagCompound.setByte("note", ((TileEntityNote) tile).note);
                tagCompound.setInteger("tone", getToneFromState(iblockstate));

                return EnumActionResult.SUCCESS;
            } else if (tile != null && tile instanceof TileArcaneEar) {
                tagCompound.setByte("note", ((TileArcaneEar) tile).note);
                tagCompound.setInteger("tone", ((TileArcaneEar) tile).tone);
            }
        } else if (!world.isRemote && !player.isSneaking()) {
            if (tile != null && tile instanceof TileArcaneEar) {
                ((TileArcaneEar) tile).note = tagCompound.getByte("note");
                tile.markDirty();
                return EnumActionResult.SUCCESS;
            }

            if (tile != null && tile instanceof TileEntityNote) {
                ((TileEntityNote) tile).note = tagCompound.getByte("note");
                tile.markDirty();
                return EnumActionResult.SUCCESS;
            }
        }


        return EnumActionResult.PASS;
    }

    private int getToneFromState(IBlockState state) {
        Material material = state.getMaterial();
        int i = 0;

        if (material == Material.ROCK) {
            i = 1;
        }

        if (material == Material.SAND) {
            i = 2;
        }

        if (material == Material.GLASS) {
            i = 3;
        }

        if (material == Material.WOOD) {
            i = 4;
        }

        Block block = state.getBlock();

        if (block == Blocks.CLAY) {
            i = 5;
        }

        if (block == Blocks.GOLD_BLOCK) {
            i = 6;
        }

        if (block == Blocks.WOOL) {
            i = 7;
        }

        if (block == Blocks.PACKED_ICE) {
            i = 8;
        }

        if (block == Blocks.BONE_BLOCK) {
            i = 9;
        }

        return i;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 100;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
        if (stack.hasTagCompound()) {
            NBTTagCompound tagCompound = stack.getTagCompound();

            int note = 0;
            int tone = 0;

            note = tagCompound.getByte("note");
            tone = tagCompound.getInteger("tone");

            NoteBlockEvent.Play event = new NoteBlockEvent.Play(world, entity.getPosition(), null, note, tone);

            if (!MinecraftForge.EVENT_BUS.post(event)) {
                float f = (float) Math.pow(2.0D, (note - 12) / 12.0D);


                world.playSound(null, entity.getPosition().up(1), getInstrumentSound(event.getInstrument()), SoundCategory.PLAYERS, 3.0F, f);
                world.spawnParticle(EnumParticleTypes.NOTE, entity.posX + 0.5D, entity.posY + 0.5D, entity.posZ + 0.5D, (double) note / 24.0D, 0.0D, 0.0D);
            }

        }
    }

    private String getInstrumentNameFromInt(int toneIn) {
        return Instrument.values()[toneIn].name().toLowerCase();
    }

    private SoundEvent getInstrumentSound(Instrument instrumentIn) {
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
}
