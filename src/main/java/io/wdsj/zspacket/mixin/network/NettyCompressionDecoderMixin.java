package io.wdsj.zspacket.mixin.network;

import com.github.luben.zstd.Zstd;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.wdsj.zspacket.ZSPacket;
import net.minecraft.network.NettyCompressionDecoder;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = NettyCompressionDecoder.class, priority = 999)
public abstract class NettyCompressionDecoderMixin {
    @Shadow
    private int threshold;

    /**
     * @author Creeam
     * @reason Zstd compression
     */
    @Inject(
            method = "decode",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_, CallbackInfo ci) throws Exception {
        if (threshold == ZSPacket.IDENTIFIER) {
            ci.cancel();
        } else {
            return;
        }
        if (p_decode_2_.readableBytes() != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(p_decode_2_);
            int compressedSize = packetBuffer.readVarInt();

            if (compressedSize == 0) {
                p_decode_3_.add(packetBuffer.readBytes(packetBuffer.readableBytes()));
            } else {

                if (compressedSize > 2097152) {
                    throw new DecoderException("Badly compressed packet - size of " + compressedSize + " is larger than protocol maximum of " + 2097152);
                }

                byte[] compressedData = new byte[packetBuffer.readableBytes()];
                packetBuffer.readBytes(compressedData);

                byte[] decompressedData = Zstd.decompress(compressedData, compressedSize);
                p_decode_3_.add(Unpooled.wrappedBuffer(decompressedData));
            }
        }
    }
}
