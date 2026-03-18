package congregamystica.integrations.congregamystica.blocks.inventories.handlers;

import congregamystica.integrations.congregamystica.blocks.tiles.TileArcanePatternProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.resources.ItemCrystalEssence;

public class PatternProviderStackHandler extends ItemStackHandler {
    public static final int SLOT_AIR = 9;
    public static final int SLOT_FIRE = 10;
    public static final int SLOT_WATER = 11;
    public static final int SLOT_EARTH = 12;
    public static final int SLOT_ORDER = 13;
    public static final int SLOT_ENTROPY = 14;
    public static final int SLOT_GRID_MAX = 15;
    public static final int SLOT_MAX = 33;

    public TileArcanePatternProvider tile;

    public PatternProviderStackHandler(TileArcanePatternProvider tile) {
        super(SLOT_MAX);
        this.tile = tile;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return slot < SLOT_GRID_MAX ? ItemStack.EMPTY : super.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot < SLOT_AIR ? 1 :super.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        switch (slot) {
            case SLOT_AIR:
                return this.isPrimalAspectCrystal(stack, Aspect.AIR);
            case SLOT_FIRE:
                return this.isPrimalAspectCrystal(stack, Aspect.FIRE);
            case SLOT_WATER:
                return this.isPrimalAspectCrystal(stack, Aspect.WATER);
            case SLOT_EARTH:
                return this.isPrimalAspectCrystal(stack, Aspect.EARTH);
            case SLOT_ORDER:
                return this.isPrimalAspectCrystal(stack, Aspect.ORDER);
            case SLOT_ENTROPY:
                return this.isPrimalAspectCrystal(stack, Aspect.ENTROPY);
            default:
                return true;
        }
    }

    private boolean isPrimalAspectCrystal(ItemStack stack, Aspect slotAspect) {
        if(stack.isEmpty()) {
            return false;
        } else if(stack.getItem() instanceof ItemCrystalEssence) {
            AspectList aspectList = ((ItemCrystalEssence) stack.getItem()).getAspects(stack);
            if (aspectList != null && aspectList.aspects.size() == 1) {
                return aspectList.getAspects()[0] == slotAspect;
            }
        }
        return false;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.tile.markDirty();
    }
}
