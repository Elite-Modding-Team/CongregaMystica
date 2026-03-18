package congregamystica.integrations.congregamystica.blocks.inventories.containers;

import congregamystica.integrations.congregamystica.blocks.tiles.TileArcanePatternProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPatternProvider extends Container {
    public TileArcanePatternProvider tileEntity;

    public ContainerPatternProvider(EntityPlayer player, TileArcanePatternProvider tile) {
        this.tileEntity = tile;
        this.bindTileInventory();
        this.bindPlayerInventory(player);
    }

    protected void bindTileInventory() {

    }

    protected void bindPlayerInventory(EntityPlayer player) {
        InventoryPlayer inventory = player.inventory;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.mergeItemStack(stack, startIndex, endIndex, reverseDirection);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
