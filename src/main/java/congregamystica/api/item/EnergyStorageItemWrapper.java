package congregamystica.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyStorageItemWrapper implements IEnergyStorage, ICapabilityProvider {
    protected ItemStack stack;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorageItemWrapper(ItemStack stack, int capacity, int maxReceive, int maxExtract, int energy) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.setEnergyStored(energy);
    }

    public EnergyStorageItemWrapper(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
        this(stack, capacity, maxReceive, maxExtract, getEnergyStored(stack));
    }

    public EnergyStorageItemWrapper(ItemStack stack, int capacity, int maxTransfer) {
        this(stack, capacity, maxTransfer, maxTransfer);
    }

    public EnergyStorageItemWrapper(ItemStack stack, int capacity) {
        this(stack, capacity, capacity, capacity);
    }

    public static int getEnergyStored(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getInteger("energy") : 0;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(!this.canReceive())
            return 0;

        int energyStored = this.getEnergyStored();
        maxReceive = Math.min(this.getMaxEnergyStored() - energyStored, Math.min(this.maxReceive, maxReceive));
        if(!simulate) {
            this.setEnergyStored(energyStored + maxReceive);
        }
        return maxReceive;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if(!this.canExtract())
            return 0;

        int energyStored = this.getEnergyStored();
        maxExtract = Math.min(energyStored, Math.min(this.maxExtract, maxExtract));
        if(!simulate) {
            this.setEnergyStored(energyStored - maxExtract);
        }
        return maxExtract;
    }

    @Override
    public int getEnergyStored() {
        return getEnergyStored(this.stack);
    }

    protected void setEnergyStored(int energy) {
        this.stack.setTagInfo("energy", new NBTTagInt(energy));
    }

    @Override
    public int getMaxEnergyStored() {
        return this.capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0 && this.getEnergyStored() > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0 && this.getEnergyStored() < this.getMaxEnergyStored();
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(this) : null;
    }
}
