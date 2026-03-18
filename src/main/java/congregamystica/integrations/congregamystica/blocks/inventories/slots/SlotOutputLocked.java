package congregamystica.integrations.congregamystica.blocks.inventories.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotOutputLocked extends SlotItemHandler {
    public SlotOutputLocked(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }


}
