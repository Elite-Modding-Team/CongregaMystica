package congregamystica.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.FXDispatcher;

import java.awt.*;

public class PacketCrystalGrowth implements IMessage {
    public BlockPos pos;
    public int color;

    public PacketCrystalGrowth(Aspect aspect, BlockPos pos) {
        this.pos = pos;
        this.color = aspect.getColor();
    }

    public PacketCrystalGrowth() {
        this(Aspect.ORDER, new BlockPos(0, 0, 0));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.color = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeInt(this.color);
    }

    public static class Handler implements IMessageHandler<PacketCrystalGrowth, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketCrystalGrowth message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                Color color = new Color(message.color);
                for(int i = 0; i < 12; i++) {
                    FXDispatcher.INSTANCE.drawWispyMotes(
                            message.pos.getX() + world.rand.nextFloat(),
                            message.pos.getY() + world.rand.nextGaussian(),
                            message.pos.getZ() + world.rand.nextFloat(),
                            0, 0, 0,
                            30 + world.rand.nextInt(10),
                            color.getRed() / 255.0f,
                            color.getGreen() / 255.0f,
                            color.getBlue() / 255.0f,
                            -0.05f);
                }
            });
            return null;
        }
    }
}
