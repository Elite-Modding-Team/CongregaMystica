package congregamystica.registry;

import congregamystica.integrations.congregamystica.blocks.inventories.containers.ContainerPatternProvider;
import congregamystica.integrations.congregamystica.blocks.inventories.gui.GuiPatternProvider;
import congregamystica.integrations.congregamystica.blocks.tiles.TileArcanePatternProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.jetbrains.annotations.Nullable;

public class ModGuiHandlerCM implements IGuiHandler {
    public static final int ID_ARCANE_PATTERN_PROVIDER = 0;

    @Override
    public @Nullable Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ID_ARCANE_PATTERN_PROVIDER:
                return new ContainerPatternProvider(player, (TileArcanePatternProvider) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public @Nullable Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ID_ARCANE_PATTERN_PROVIDER:
                return new GuiPatternProvider(player, (TileArcanePatternProvider) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
