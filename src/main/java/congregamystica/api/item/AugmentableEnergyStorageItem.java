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
import thecodex6824.thaumicaugmentation.api.augment.AugmentableItem;
import thecodex6824.thaumicaugmentation.api.augment.CapabilityAugmentableItem;

public class AugmentableEnergyStorageItem extends AugmentableItem implements IEnergyStorage, ICapabilityProvider {
    protected ItemStack stack;
    protected int capacity;

    public AugmentableEnergyStorageItem(ItemStack stack, int capacity, int slots) {
        super(slots);
        this.stack = stack;
        this.capacity = capacity;
        this.setEnergyStored(this.getEnergyStored());
    }

    //##########################################################
    // IEnergyStorage

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(!this.canReceive())
            return 0;

        int energyStored = this.getEnergyStored();
        maxReceive = Math.min(this.getMaxEnergyStored() - energyStored, maxReceive);
        if(!simulate) {
            this.setEnergyStored(energyStored + maxReceive);
        }
        return maxReceive;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return EnergyStorageItemWrapper.getEnergyStored(this.stack);
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
        return false;
    }

    @Override
    public boolean canReceive() {
        return this.getEnergyStored() < this.getMaxEnergyStored();
    }

    //##########################################################
    // ICapabilityProvider

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || capability == CapabilityAugmentableItem.AUGMENTABLE_ITEM;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this);
        } else if(capability == CapabilityAugmentableItem.AUGMENTABLE_ITEM) {
            return CapabilityAugmentableItem.AUGMENTABLE_ITEM.cast(this);
        }
        return null;
    }
}
