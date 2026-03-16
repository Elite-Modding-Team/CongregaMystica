package congregamystica.integrations.congregamystica.blocks.render;

import congregamystica.CongregaMystica;
import congregamystica.integrations.congregamystica.blocks.BlockArcaneCrafter;
import congregamystica.integrations.congregamystica.blocks.tiles.TileArcaneCrafter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import thaumcraft.Thaumcraft;
import thaumcraft.client.lib.UtilsFX;

@SideOnly(Side.CLIENT)
public class TileArcaneCrafterTESR extends TileEntitySpecialRenderer<TileArcaneCrafter> {
    private static final ResourceLocation CRAFTING_TEXTURE = new ResourceLocation(CongregaMystica.MOD_ID, "textures/blocks/arcane_crafter_fill.png");
    private static final ResourceLocation GEAR_ICON = new ResourceLocation(Thaumcraft.MODID, "textures/misc/gear_brass.png");

    @Override
    public void render(@NotNull TileArcaneCrafter tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(tile, x, y, z, partialTicks, destroyStage, alpha);

        Minecraft mc = FMLClientHandler.instance().getClient();
        IBlockState state = tile.getWorld().getBlockState(tile.getPos());
        EnumFacing facing = state.getValue(BlockArcaneCrafter.FACING);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 0.75, z + 0.5);
        switch (facing) {
            case NORTH:
                GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.rotate(-90, 0.0F, 1.0F, 0.0F);
                break;
            case EAST:
                GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
                break;
        }

        GlStateManager.pushMatrix();
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.5F);
        UtilsFX.renderQuadCentered(CRAFTING_TEXTURE, 10, 1, tile.getFilledCraftingSlots(), 0.5F, 1.0F, 1.0F, 1.0F,
                state.getPackedLightmapCoords(tile.getWorld(), tile.getPos()), 771, 1.0F);
        GlStateManager.popMatrix();
        mc.renderEngine.bindTexture(GEAR_ICON);
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.2F, -0.40625F, 0.05F);
        GlStateManager.rotate(-tile.rotation % 360.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.translate(-0.5F, -0.5F, 0.0F);
        UtilsFX.renderTextureIn3D(1.0F, 0.0F, 0.0F, 1.0F, 16, 16, 0.1F);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.2F, -0.40625F, 0.05F);
        GlStateManager.rotate(tile.rotation % 360.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.translate(-0.5F, -0.5F, 0.0F);
        UtilsFX.renderTextureIn3D(1.0F, 0.0F, 0.0F, 1.0F, 16, 16, 0.1F);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
