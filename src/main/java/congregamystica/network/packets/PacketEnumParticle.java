package congregamystica.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEnumParticle implements IMessage {
    public EnumParticleTypes particle;
    public double xPos;
    public double yPos;
    public double zPos;
    public double xSpeed;
    public double ySpeed;
    public double zSpeed;

    public PacketEnumParticle(EnumParticleTypes particle, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
        this.particle = particle;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public PacketEnumParticle() {
        this(EnumParticleTypes.getParticleFromId(0),0,0,0,0,0,0);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.particle = EnumParticleTypes.getParticleFromId(buf.readInt());
        this.xPos = buf.readDouble();
        this.yPos = buf.readDouble();
        this.zPos = buf.readDouble();
        this.xSpeed = buf.readDouble();
        this.ySpeed = buf.readDouble();
        this.zSpeed = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.particle.getParticleID());
        buf.writeDouble(this.xPos);
        buf.writeDouble(this.yPos);
        buf.writeDouble(this.zPos);
        buf.writeDouble(this.xSpeed);
        buf.writeDouble(this.ySpeed);
        buf.writeDouble(this.zSpeed);
    }

    public static class Handler implements IMessageHandler<PacketEnumParticle, IMessage> {
        @Override
        public IMessage onMessage(PacketEnumParticle message, MessageContext ctx) {
            FMLClientHandler.instance().getClient().effectRenderer.spawnEffectParticle(
                    message.particle.getParticleID(),
                    message.xPos, message.yPos, message.zPos,
                    message.xSpeed, message.ySpeed, message.zSpeed);
            return null;
        }
    }
}
