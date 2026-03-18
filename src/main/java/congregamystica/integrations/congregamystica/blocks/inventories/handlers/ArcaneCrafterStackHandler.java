package congregamystica.integrations.congregamystica.blocks.inventories.handlers;

import congregamystica.integrations.congregamystica.blocks.tiles.TileArcaneCrafter;
import congregamystica.registry.ModItemsCM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.IArcaneWorkbench;
import thaumcraft.common.items.resources.ItemCrystalEssence;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.List;

public class ArcaneCrafterStackHandler extends ItemStackHandler {
    /** Designated slot for the filter item separating the Vis Crystals from the crafting grid. */
    public static final int SLOT_AIR = 9;
    public static final int SLOT_FIRE = 10;
    public static final int SLOT_WATER = 11;
    public static final int SLOT_EARTH = 12;
    public static final int SLOT_ORDER = 13;
    public static final int SLOT_ENTROPY = 14;
    public static final int SLOT_OUTPUT = 15;
    public static final int SLOT_FILTER = 16;

    private final TileArcaneCrafter tile;
    private IArcaneRecipe cachedRecipe;

    public ArcaneCrafterStackHandler(TileArcaneCrafter tile) {
        super(17);
        this.tile = tile;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return this.isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }

    public @NotNull ItemStack insertInternal(int slot, @NotNull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        validateSlotIndex(slot);
        if(slot < SLOT_AIR || slot == SLOT_FILTER) {
            //Separator slot and crafting slots
            return this.isItemValid(slot, stack) ? 1 : 0;
        } else {
            //Vis Crystal slots
            return this.isItemValid(slot, stack) ? super.getStackLimit(slot, stack) : 0;
        }
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        validateSlotIndex(slot);
        if(slot == SLOT_OUTPUT) {
            return false;
        } else if(this.shouldFillCrystalSlots()) {
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
                case SLOT_FILTER:
                    return this.isFilterItem(stack);
                default:
                    return false;
            }
        } else {
            return slot < SLOT_AIR;
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.tile.markDirty();
    }

    public boolean isInventoryFull() {
        for(int i = 0; i < SLOT_AIR; i++) {
            if(this.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for(int i = 0; i < this.getSlots(); i++) {
            if(!this.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getFilledCraftingSlots() {
        int filled = 0;
        for(int slot = 0; slot < SLOT_AIR; slot++) {
            if(!this.getStackInSlot(slot).isEmpty()) {
                filled++;
            }
        }
        return filled;
    }

    @SuppressWarnings("ConstantConditions")
    public InventoryCrafting getInventoryCrafting(boolean skipPlaceholders) {
        InventoryCrafting craft = new ArcaneCrafterInventoryCraft();
        for(int i = 0; i < getSlots(); i++) {
            if(i == SLOT_FILTER || i == SLOT_OUTPUT)
                continue;
            ItemStack stack = this.getStackInSlot(i);
            if (!stack.isEmpty() && (stack.getItem() != ModItemsCM.CRAFTER_PLACEHOLDER || !skipPlaceholders)) {
                craft.setInventorySlotContents(i, stack);
            }
        }
        return craft;
    }

    @Nullable
    public IArcaneRecipe getMatchingArcaneRecipe(EntityPlayer player) {
        return this.getMatchingArcaneRecipe(player, this.getInventoryCrafting(true));
    }

    public IArcaneRecipe getMatchingArcaneRecipe(EntityPlayer player, InventoryCrafting craft) {
        if(this.isInventoryFull()) {
            if(this.cachedRecipe == null || !this.cachedRecipe.matches(craft, player.world)) {
                this.cachedRecipe = ThaumcraftCraftingManager.findMatchingArcaneRecipe(craft, player);
            }
            return this.cachedRecipe;
        }
        return null;
    }

    public boolean canCraft(EntityPlayer player, World tileWorld, BlockPos tilePos) {
        if(this.isInventoryFull()) {
            InventoryCrafting craft = this.getInventoryCrafting(true);
            IArcaneRecipe recipe = this.cachedRecipe;
            if (recipe == null) {
                recipe = this.getMatchingArcaneRecipe(player, craft);
                return recipe != null;
            } else {
                if (!recipe.matches(craft, tileWorld)) {
                    this.getMatchingArcaneRecipe(player, craft);
                    return false;
                } else if(ThaumcraftCapabilities.knowsResearch(player, recipe.getResearch())) {
                    return AuraHelper.getVis(tileWorld, tilePos) >= (float) (recipe.getVis() + 1);
                }
            }
        }
        return false;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean attemptCraft(EntityPlayer player, World tileWorld, BlockPos tilePos) {
        if(!this.isInventoryFull())
            return false;

        InventoryCrafting craft = this.getInventoryCrafting(true);
        IArcaneRecipe recipe = this.getMatchingArcaneRecipe(player, craft);
        if(recipe != null && ThaumcraftCapabilities.knowsResearch(player, recipe.getResearch())) {
            int vis = recipe.getVis() + 1;
            if(AuraHelper.getVis(tileWorld, tilePos) >= (float) vis) {
                AuraHelper.drainVis(tileWorld, tilePos, vis, false);
                this.setStackInSlot(SLOT_OUTPUT, recipe.getRecipeOutput());
                List<ItemStack> remainders = recipe.getRemainingItems(craft);
                for(int i = 0; i < craft.getSizeInventory(); i++) {
                    ItemStack slotStack = this.getStackInSlot(i);
                    if(i < SLOT_AIR) {
                        if(slotStack.getItem() != ModItemsCM.CRAFTER_PLACEHOLDER) {
                            this.setStackInSlot(i, remainders.get(i));
                        }
                    } else {
                        if(!slotStack.isEmpty() && slotStack.getItem() instanceof ItemCrystalEssence) {
                            ItemCrystalEssence crystal = (ItemCrystalEssence) slotStack.getItem();
                            AspectList itemAspects = crystal.getAspects(slotStack);
                            if(itemAspects.size() == 1) {
                                Aspect itemAspect = itemAspects.getAspects()[0];
                                AspectList recipeAspects = recipe.getCrystals();
                                int recipeAmount = recipeAspects.getAmount(itemAspect);
                                if(recipeAmount > 0) {
                                    slotStack.shrink(recipeAmount);
                                    this.setStackInSlot(i, !slotStack.isEmpty() ? slotStack : ItemStack.EMPTY);
                                }
                                continue;
                            }
                        }
                        this.setStackInSlot(i, remainders.get(i));
                    }
                }
                return true;
            }
        }
        return false;
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

    private boolean shouldFillCrystalSlots() {
        return this.getStackInSlot(SLOT_FILTER).isEmpty();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isFilterItem(ItemStack stack) {
        return stack.getItem() == ModItemsCM.CRAFTER_PLACEHOLDER;
    }

    private static class ArcaneCrafterInventoryCraft extends InventoryCrafting implements IArcaneWorkbench {
        public ArcaneCrafterInventoryCraft() {
            super(new Container() {
                @Override
                public boolean canInteractWith(@NotNull EntityPlayer playerIn) {
                    return false;
                }
            }, 5, 3);
        }
    }
}
