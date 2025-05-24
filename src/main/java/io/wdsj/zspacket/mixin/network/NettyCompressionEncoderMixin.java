package io.wdsj.zspacket.mixin.network;

import com.github.luben.zstd.Zstd;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.wdsj.zspacket.config.Settings;
import net.minecraft.network.NettyCompressionEncoder;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NettyCompressionEncoder.class, priority = 999)
public abstract class NettyCompressionEncoderMixin {
    @Shadow
    private int threshold;

    /**
     * @author Creeam
     * @reason Zstd compression
     */
    @Overwrite
    protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws Exception {
        int inputSize = p_encode_2_.readableBytes();
        PacketBuffer packetBuffer = new PacketBuffer(p_encode_3_);

        if (inputSize < this.threshold) {
            packetBuffer.writeVarInt(0);
            packetBuffer.writeBytes(p_encode_2_);
        } else {
            byte[] input = new byte[inputSize];
            p_encode_2_.readBytes(input);

            byte[] compressed = Zstd.compress(input, Settings.compressionLevel);
            packetBuffer.writeVarInt(input.length);
            packetBuffer.writeBytes(compressed);
        }
    }
}
