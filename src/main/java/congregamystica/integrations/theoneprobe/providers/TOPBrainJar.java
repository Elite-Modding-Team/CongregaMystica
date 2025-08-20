package congregamystica.integrations.theoneprobe.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.tiles.devices.TileJarBrain;

import java.util.function.Function;

public class TOPBrainJar implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe input) {
        input.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return BlocksTC.brainBox.getRegistryName().toString();
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
                if (mode == ProbeMode.EXTENDED) {
                    TileEntity tile = world.getTileEntity(data.getPos());
                    if(tile instanceof TileJarBrain) {
                        int xp = ((TileJarBrain) tile).xp;
                        int xpMax = ((TileJarBrain) tile).xpMax;
                        info.progress(xp, xpMax, info.defaultProgressStyle()
                                .suffix("xp")
                                //TODO: .filledColor()
                                //TODO: .alternateFilledColor()
                                .borderColor(0xff555555)
                                .numberFormat(NumberFormat.FULL));

                    }
                }
            }
        });
        return null;
    }
}