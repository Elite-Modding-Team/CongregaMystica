package congregamystica.integrations.theoneprobe.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.devices.BlockVisBattery;
import thaumcraft.common.tiles.crafting.TileVoidSiphon;

import java.util.Collections;
import java.util.function.Function;

public class TOPVoidSiphon implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe input) {
        input.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return BlocksTC.visBattery.getRegistryName().toString();
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
                if (mode == ProbeMode.EXTENDED) {
                    TileEntity tile = world.getTileEntity(data.getPos());
                    if(tile instanceof TileVoidSiphon) {
                        int progress = ((TileVoidSiphon) tile).progress;
                        int maxProgress = ((TileVoidSiphon) tile).PROGREQ;
                        int percent = (int) ((float) progress / maxProgress * 100);
                        info.progress(percent, 100, info.defaultProgressStyle()
                                .suffix("%"));
                    }
                }
            }
        });
        return null;
    }
}
