package congregamystica.integrations.congregamystica.blocks.inventories.gui;

import congregamystica.CongregaMystica;
import congregamystica.integrations.congregamystica.blocks.inventories.containers.ContainerPatternProvider;
import congregamystica.integrations.congregamystica.blocks.tiles.TileArcanePatternProvider;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiPatternProvider extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CongregaMystica.MOD_ID, "textures/gui/gui_pattern_provider.png");

    public GuiPatternProvider(EntityPlayer player, TileArcanePatternProvider tile) {
        super(new ContainerPatternProvider(player, tile));
    }

    public ContainerPatternProvider getContainer() {
        return (ContainerPatternProvider) this.inventorySlots;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(TEXTURE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GlStateManager.enableBlend();
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GlStateManager.disableBlend();
    }

    @Override
    public void drawSlot(Slot slotIn) {
        super.drawSlot(slotIn);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void handlePatternSlots() {

    }
}
