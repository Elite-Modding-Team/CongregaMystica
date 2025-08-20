package congregamystica.integrations.theoneprobe.providers;

import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.ProbeConfig;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import mcjty.theoneprobe.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.tiles.devices.TileVisGenerator;

import java.util.function.Function;

public class TOPVisGenerator implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe input) {
        input.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return BlocksTC.visGenerator.getRegistryName().toString();
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
                TileEntity tile = world.getTileEntity(data.getPos());
                if(tile instanceof TileVisGenerator) {
                    int energy = ((TileVisGenerator) tile).getEnergyStored();
                    int maxEnergy = ((TileVisGenerator) tile).getMaxEnergyStored();
                    ProbeConfig config = Config.getDefaultConfig();
                    if (config.getRFMode() == 1) {
                        info.progress(energy, maxEnergy, info.defaultProgressStyle().suffix("RF")
                                .filledColor(Config.rfbarFilledColor)
                                .alternateFilledColor(Config.rfbarAlternateFilledColor)
                                .borderColor(Config.rfbarBorderColor)
                                .numberFormat(Config.rfFormat));
                    } else {
                        info.text(TextStyleClass.PROGRESS + "RF: " + ElementProgress.format(energy, Config.rfFormat, "RF"));
                    }
                }
            }
        });
        return null;
    }
}
