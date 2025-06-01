package io.wdsj.zspacket.mixin.network;

import io.netty.channel.Channel;
import io.wdsj.zspacket.ZSPacket;
import net.minecraft.network.NettyCompressionDecoder;
import net.minecraft.network.NettyCompressionEncoder;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetworkManager.class, priority = 100)
public abstract class NetworkManagerMixin {
    @Shadow
    private Channel channel;

    /**
     * @author Creeam
     * @reason ZStandard compression
     */
    @Overwrite
    public void setCompressionThreshold(int threshold) {
        if (threshold >= 0 || threshold == ZSPacket.IDENTIFIER) {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder) this.channel.pipeline().get("decompress")).setCompressionThreshold(threshold);
            } else {
                this.channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(threshold));
            }

            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder) this.channel.pipeline().get("compress")).setCompressionThreshold(threshold);
            } else {
                this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(threshold));
            }
        } else {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                this.channel.pipeline().remove("decompress");
            }

            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                this.channel.pipeline().remove("compress");
            }
        }
    }
}
