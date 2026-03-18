package congregamystica.integrations.congregamystica.blocks.tiles;

import congregamystica.integrations.congregamystica.blocks.inventories.handlers.PatternProviderStackHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileArcanePatternProvider extends TileEntity implements ITickable {
    public PatternProviderStackHandler stackHandler;
    public RangedWrapper craftingGridHandler;
    public RangedWrapper inventoryHandler;
    protected boolean matchMeta;
    protected boolean matchOreDict;

    public TileArcanePatternProvider() {
        this.stackHandler = new PatternProviderStackHandler(this);
        this.craftingGridHandler = new RangedWrapper(this.stackHandler, 0, PatternProviderStackHandler.SLOT_GRID_MAX);
        this.inventoryHandler = new RangedWrapper(this.stackHandler, 15, PatternProviderStackHandler.SLOT_MAX);
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        this.matchMeta = compound.getBoolean("matchMeta");
        this.matchOreDict = compound.getBoolean("matchOreDict");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inventory", this.stackHandler.serializeNBT());
        compound.setBoolean("matchMeta", this.matchMeta);
        compound.setBoolean("matchOreDict", this.matchOreDict);
        return compound;
    }

    @Override
    public void update() {
        boolean did = false;
        if(!this.world.isRemote && !this.world.isBlockPowered(this.pos)) {
            TileArcaneCrafter crafter = this.getArcaneCrafter();
            if(crafter != null) {
                did |= this.attemptTransferAndCraft(crafter);
            }
        }
        if(did) {
            this.markDirty();
        }
    }

    @Nullable
    public TileArcaneCrafter getArcaneCrafter() {
        TileEntity tile = this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN));
        return tile instanceof TileArcaneCrafter ? (TileArcaneCrafter) tile : null;
    }

    public boolean attemptTransferAndCraft(@NotNull TileArcaneCrafter crafter) {
        if(this.hasRecipeMaterials()) {
            for(int i = 0; i < this.craftingGridHandler.getSlots(); i++) {
                ItemStack gridStack = this.craftingGridHandler.getStackInSlot(i);
                if(!gridStack.isEmpty()) {
                    for(int invSlot = 0; invSlot < this.inventoryHandler.getSlots(); invSlot++) {
                        ItemStack invStack = this.inventoryHandler.extractItem(invSlot, gridStack.getCount(), true);
                        if(!invStack.isEmpty() && doItemStacksMatch(gridStack, invStack)) {
                            if(crafter.stackHandler.insertInternal(i, invStack, true).isEmpty()) {
                                crafter.stackHandler.insertInternal(i, this.inventoryHandler.extractItem(invSlot, 1, false), false);
                                break;
                            }
                        }
                    }
                }
            }
            if(!crafter.attemptCraft()) {
                crafter.ejectInventory();
            }
            return true;
        }
        return false;
    }

    public boolean hasRecipeMaterials() {
        int[] slotWithdraws = new int[this.craftingGridHandler.getSlots()];

        outer:
        for(int gridSlot = 0; gridSlot < this.craftingGridHandler.getSlots(); gridSlot++) {
            ItemStack gridStack = this.craftingGridHandler.getStackInSlot(gridSlot);
            if(!gridStack.isEmpty()) {
                int withdrawAmount = slotWithdraws[gridSlot] + gridStack.getCount();
                for(int invSlot = 0; invSlot < this.inventoryHandler.getSlots(); invSlot++) {
                    ItemStack invStack = this.inventoryHandler.extractItem(invSlot, withdrawAmount, true);
                    if(!invStack.isEmpty() && invStack.getCount() == withdrawAmount && doItemStacksMatch(gridStack, invStack)) {
                        slotWithdraws[gridSlot] = withdrawAmount;
                        continue outer;
                    }
                }
                return false;
            }
        }
        return true;
    }

    public NonNullList<ItemStack> getMissingRecipeMaterials() {
        NonNullList<ItemStack> missingStacks = NonNullList.create();
        int[] slotWithdraws = new int[this.craftingGridHandler.getSlots()];

        outer:
        for(int gridSlot = 0; gridSlot < this.craftingGridHandler.getSlots(); gridSlot++) {
            ItemStack gridStack = this.craftingGridHandler.getStackInSlot(gridSlot);
            if(!gridStack.isEmpty()) {
                int withdrawAmount = slotWithdraws[gridSlot] + gridStack.getCount();
                for(int invSlot = 0; invSlot < this.inventoryHandler.getSlots(); invSlot++) {
                    ItemStack invStack = this.inventoryHandler.extractItem(invSlot, withdrawAmount, true);
                    if(!invStack.isEmpty() && doItemStacksMatch(gridStack, invStack)) {
                        if(withdrawAmount == invStack.getCount()) {
                            slotWithdraws[gridSlot] = withdrawAmount;
                            continue outer;
                        } else {
                            withdrawAmount -= invStack.getCount();
                            slotWithdraws[gridSlot] = invStack.getCount();
                        }
                    }
                }
                gridStack = gridStack.copy();
                gridStack.setCount(withdrawAmount);
                missingStacks.add(gridStack);
            }
        }
        return missingStacks;
    }

    public boolean doItemStacksMatch(ItemStack gridStack, ItemStack invStack) {
        if(gridStack.hasTagCompound() || gridStack.getCount() > 1) {
            //Matching ItemStack
            return ItemStack.areItemStacksEqual(gridStack, invStack);
        } else if(OreDictionary.itemMatches(gridStack, invStack, this.matchMeta)) {
            //Matching Item
            return true;
        } else if(this.matchOreDict) {
            //Matching OreDict
            for(int oreId : OreDictionary.getOreIDs(gridStack)) {
                String oreDict = OreDictionary.getOreName(oreId);
                if(OreDictionary.containsMatch(this.matchMeta, OreDictionary.getOres(oreDict), invStack)) {
                    return true;
                }
            }
        }
        return false;
    }











    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventoryHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(this.pos, state, state, Constants.BlockFlags.DEFAULT);
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
