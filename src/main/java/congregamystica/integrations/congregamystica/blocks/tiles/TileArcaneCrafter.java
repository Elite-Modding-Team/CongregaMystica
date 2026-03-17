package congregamystica.integrations.congregamystica.blocks.tiles;

import congregamystica.integrations.congregamystica.blocks.inventories.ArcaneCrafterStackHandler;
import congregamystica.utils.helpers.PlayerHelper;
import congregamystica.utils.helpers.StringHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.casters.IInteractWithCaster;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.common.lib.SoundsTC;

import java.util.UUID;

public class TileArcaneCrafter extends TileEntity implements ITickable, IInteractWithCaster {
    public ArcaneCrafterStackHandler stackHandler = new ArcaneCrafterStackHandler(this);
    protected UUID playerId;
    private EntityPlayer boundPlayer;
    public int progress;
    public int progressMax = 20;
    public boolean shouldEject;
    public float rotation;
    public double rp;
    public int rotTicks;

    @Nullable
    public EntityPlayer getBoundPlayer() {
        if(this.boundPlayer == null) {
            this.boundPlayer = PlayerHelper.getPlayerFromUUID(this.playerId);
        }
        return this.boundPlayer;
    }

    public void setPlayer(EntityPlayer player) {
        this.boundPlayer = player;
        this.playerId = PlayerHelper.getUUIDFromPlayer(player);
        this.markDirty();
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.playerId = UUID.fromString(compound.getString("playerId"));
        this.stackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        this.progress = compound.getInteger("progress");
        this.shouldEject = compound.getBoolean("shouldEject");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("playerId", this.playerId.toString());
        compound.setTag("inventory", this.stackHandler.serializeNBT());
        compound.setInteger("progress", this.progress);
        compound.setBoolean("shouldEject", this.shouldEject);
        return compound;
    }

    @Override
    public void update() {
        boolean did = false;
        if(!this.world.isRemote) {
            if(!this.world.isBlockPowered(this.pos)) {
                did |= this.handleProgress();
                did |= this.attemptCraft();
                did |= this.attemptEject();
            }
        } else {
            this.handleGearRotation();
        }

        if(did) {
            this.markDirty();
        }
    }

    public boolean handleProgress() {
        if(this.canCraft()) {
            if(this.progress > 0) {
                this.progress--;
            } else {
                this.progress = this.progressMax;
            }
            return true;
        } else if(this.progress > 0) {
            this.progress = 0;
            return true;
        }
        return false;
    }

    public boolean attemptCraft() {
        if(this.progress <= 0) {
            EntityPlayer player = this.getBoundPlayer();
            if(player != null) {
                if(this.stackHandler.attemptCraft(player, this.world, this.pos)) {
                    this.shouldEject = true;
                    this.world.addBlockEvent(this.pos, this.getBlockType(), 1, 0);
                    return true;
                } else if(this.stackHandler.isInventoryFull()) {
                    this.shouldEject = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean attemptEject() {
        if(this.shouldEject) {
            if (!this.stackHandler.isEmpty()) {
                if (this.world.isAirBlock(this.pos.offset(EnumFacing.DOWN))) {
                    this.shouldEject = false;
                    return this.ejectIntoWorld();
                } else {
                    IItemHandler handler = this.getHandlerBelow();
                    if (handler != null && this.ejectIntoTarget(handler)) {
                        this.shouldEject = !this.stackHandler.isEmpty();
                        return true;
                    }
                }
            } else {
                this.shouldEject = false;
                return true;
            }
        }
        return false;
    }

    public boolean ejectIntoWorld() {
        if(!this.world.isRemote) {
            for (int slot = 0; slot < this.stackHandler.getSlots(); slot++) {
                ItemStack stack = this.stackHandler.getStackInSlot(slot);
                if(!stack.isEmpty()) {
                    EntityItem entityItem = new EntityItem(this.world, this.pos.getX() + 0.5, this.pos.getY() - 0.5, this.pos.getZ() + 0.5, stack.copy());
                    this.world.spawnEntity(entityItem);
                    this.stackHandler.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
        }
        return true;
    }

    public boolean ejectIntoTarget(IItemHandler target) {
        boolean didEject = false;
        for(int slot = 0; slot < this.stackHandler.getSlots(); slot++) {
            ItemStack slotStack = this.stackHandler.getStackInSlot(slot);
            if(!slotStack.isEmpty()) {
                ItemStack rem = ItemHandlerHelper.insertItem(target, slotStack.copy(), false);
                if(rem.isEmpty() || rem.getCount() != slotStack.getCount()) {
                    this.stackHandler.setStackInSlot(slot, rem);
                    didEject = true;
                }
            }
        }
        return didEject;
    }

    public void handleGearRotation() {
        if (this.rotTicks > 0) {
            this.rotTicks--;
            if ((double) this.rotTicks % Math.floor(Math.max(1.0, this.rp)) == 0.0) {
                this.world.playSound(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5,
                        SoundsTC.clack, SoundCategory.BLOCKS, 0.2F, 1.7F, false);
            }

            this.rp++;
        } else {
            this.rp *= 0.8F;
        }

        this.rotation += (float) this.rp;
    }

    @Override
    public boolean onCasterRightClick(World world, ItemStack itemStack, EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing, EnumHand enumHand) {
        if(!world.isRemote) {
            if (entityPlayer.isSneaking() || this.getBoundPlayer() == null) {
                this.setPlayer(entityPlayer);
                entityPlayer.sendMessage(new TextComponentTranslation(StringHelper.getTranslationKey("arcane_crafter", "chat", "bind_player"), entityPlayer.getDisplayName()));
            } else {
                this.shouldEject = true;
                this.attemptEject();
                this.markDirty();
            }
        }
        return true;
    }












    @Nullable
    public IArcaneRecipe getRecipe() {
        EntityPlayer player = this.getBoundPlayer();
        return player != null ? this.stackHandler.getMatchingArcaneRecipe(player) : null;
    }

    public int getFilledCraftingSlots() {
        return this.stackHandler.getFilledCraftingSlots();
    }

    public boolean canCraft() {
        EntityPlayer player = this.getBoundPlayer();
        return player != null && this.stackHandler.canCraft(player, this.world, this.pos);
    }

    @Nullable
    public IItemHandler getHandlerBelow() {
        TileEntity tile = this.world.getTileEntity(pos.down());
        if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
            return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        }
        return null;
    }


    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return facing != EnumFacing.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != EnumFacing.DOWN) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.stackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(this.pos, state, state, Constants.BlockFlags.DEFAULT);
        world.markBlockRangeForRenderUpdate(this.pos, this.pos);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if(id == 1) {
            if(this.world.isRemote) {
                this.rotTicks = 10;
            }
            return true;
        }
        return super.receiveClientEvent(id, type);
    }

    @Override
    public boolean shouldRefresh(@NotNull World world, @NotNull BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, Constants.BlockFlags.DEFAULT, this.getUpdateTag());
    }

    @Override
    public @NotNull NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(@NotNull NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }
}
