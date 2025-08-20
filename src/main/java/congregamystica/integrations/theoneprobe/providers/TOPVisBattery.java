package congregamystica.integrations.theoneprobe.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.devices.BlockVisBattery;

import java.util.Collections;
import java.util.function.Function;

public class TOPVisBattery implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe input) {
        input.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return BlocksTC.visBattery.getRegistryName().toString();
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
                if(state.getBlock() instanceof BlockVisBattery) {
                    int charge = state.getValue(BlockVisBattery.CHARGE);
                    int maxCharge = Collections.max(BlockVisBattery.CHARGE.getAllowedValues());
                    info.progress(charge, maxCharge, info.defaultProgressStyle()
                            .showText(false)
                            .filledColor(0xff00b7ec)
                            .alternateFilledColor(0xff008fb9)
                            .borderColor(0xff555555)
                            .numberFormat(NumberFormat.FULL));
                }
            }
        });
        return null;
    }
}
