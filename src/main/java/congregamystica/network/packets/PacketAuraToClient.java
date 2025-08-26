package congregamystica.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.client.lib.events.HudHandler;
import thaumcraft.common.world.aura.AuraChunk;

public class PacketAuraToClient implements IMessage {
    short base;
    float vis;
    float flux;

    public PacketAuraToClient(AuraChunk auraChunk) {
        this.base = auraChunk.getBase();
        this.vis = auraChunk.getVis();
        this.flux = auraChunk.getFlux();
    }

    public PacketAuraToClient() {
        this.base = 0;
        this.vis = 0;
        this.flux = 0;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.base = buf.readShort();
        this.vis = buf.readFloat();
        this.flux = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(this.base);
        buf.writeFloat(this.vis);
        buf.writeFloat(this.flux);
    }

    public static class Handler implements IMessageHandler<PacketAuraToClient, IMessage> {
        @Override
        public IMessage onMessage(PacketAuraToClient message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                HudHandler.currentAura = new AuraChunk(null, message.base, message.vis, message.flux);
            });
            return null;
        }
    }
}
