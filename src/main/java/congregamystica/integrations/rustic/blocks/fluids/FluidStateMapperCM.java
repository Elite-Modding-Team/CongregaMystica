package congregamystica.integrations.rustic.blocks.fluids;

import congregamystica.CongregaMystica;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class FluidStateMapperCM extends StateMapperBase implements ItemMeshDefinition {
    private final Fluid fluid;

    public FluidStateMapperCM(Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public @NotNull ModelResourceLocation getModelLocation(@NotNull ItemStack stack) {
        return new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, "block_fluid"), fluid.getName());
    }

    @Override
    protected @NotNull ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {
        return new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, "block_fluid"), fluid.getName());
    }
}
